package demo.spring.boot.demospringboot.service.asp;

import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.spring.boot.demospringboot.resource.service.ResourceService;
import demo.spring.boot.demospringboot.util.DownLoadUtil;
import demo.spring.boot.demospringboot.util.RegexUtil;
import demo.spring.boot.demospringboot.util.URLUtils;
import demo.spring.boot.demospringboot.util.ZipUtils;
import demo.spring.boot.demospringboot.vo.Info;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class ASPService {

    /**
     * http://www.asp300.net/dllDown/?CodeID=8872&id=1
     */
    public final int max = 540;
    public String host = "http://www.asp300.net";
    public String listPre = "http://www.asp300.net/SoftList/27/27_";
    public String listSuffix = ".html";

    @Autowired
    private ResourceService resourceService;

    /**
     * 获取List任务
     */
    public Collection<String> getListUrl(int max) throws IOException {
        Set<String> result = new LinkedHashSet<>();
        for (int j = 1; j <= max; j++) {
            String url = listPre + j + listSuffix;
            String pageSource = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
            this.getListHref(pageSource).forEach(detailUrl -> {
                log.info("获取listUrl:{}", detailUrl);
                result.add(host + detailUrl);
            });
        }
        return result;
    }

    /**
     * 下载list任务
     */
    public Collection<String> downloadListUrl(String bisName, int max, boolean headless, boolean useDriver) throws IOException {
        Collection<String> result = Collections.synchronizedSet(new LinkedHashSet<>());
        ;
        Collection<String> listUrl = this.getListUrl(max);
        listUrl.parallelStream().forEach(url -> {
            String fileNames = null;
            try {
                fileNames = this.downloadDetailToFile(url, bisName, headless, useDriver);
            } catch (IOException e) {
                log.error("下载异常:{}", e.toString(), e);
            }
            result.add(fileNames);
        });
        return result;
    }


    public String downloadDetailToFile(String url, String bisName, boolean headless, boolean useDriver) throws IOException {
        ByteArrayOutputStream packageStream = this.downloadDetailToOutputStream(url, bisName, headless, useDriver);
        String regex = "(.*?)/SoftView/(.*?).html";
        String criteriaId = url.replaceAll(regex, "$2");
        String fileName = criteriaId.replace("/", "_") + ".zip";
        File file = resourceService.addNewFile(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        IOUtils.write(packageStream.toByteArray(), fileOutputStream);//写入zip
        fileOutputStream.close();
        packageStream.close();
        return fileName;
    }

    /**
     * 下载的数据入byte
     */
    public byte[] downloadDetailToBytes(String url, String bisName, boolean headless, boolean useDriver) throws IOException {
        ByteArrayOutputStream packageStream = this.downloadDetailToOutputStream(url, bisName, headless, useDriver);
        return packageStream.toByteArray();
    }


    /**
     * http://www.asp300.net/SoftView/27/SoftView_69985.html
     * detail下载的入口
     */
    public ByteArrayOutputStream downloadDetailToOutputStream(String url, String bisName, boolean headless, boolean useDriver) throws IOException {
        JsonMapper jsonMapper = new JsonMapper();
        String pageSource = this.downloadDetailPageSource(url, headless, useDriver);
        String regex = "(.*?)/SoftView/(.*?).html";
        String host = url.replaceAll(regex, "$1");
        String criteriaId = url.replaceAll(regex, "$2");
        Map<String, byte[]> mapPic = this.downloadDetailPic(host, pageSource);//下载图片
        Map<String, byte[]> mapDownloadUrl = this.downloadDownloadList(host, pageSource);//下载下载的url
        String vipPanPageSource = this.downloadDownloadVipPan(host, mapDownloadUrl);//下载下载的url
        Info info = this.generateInfo(url, bisName, criteriaId, host, mapPic.keySet());//生成info -> 下载的其他放入info
        ByteArrayOutputStream infoOut = new ByteArrayOutputStream();
        infoOut.write(jsonMapper.writeValueAsBytes(info));
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(result);
        ZipUtils.compress(zipOutputStream, "info.txt", infoOut.toByteArray());
        ZipUtils.compress(zipOutputStream, "detail.html", pageSource.getBytes(StandardCharsets.UTF_8));
        //压缩pic文件流
        for (Map.Entry<String, byte[]> entry : mapPic.entrySet()) {
            String urlTmp = entry.getKey();
            byte[] bytes = entry.getValue();
            ZipUtils.compress(zipOutputStream, urlTmp.replace("/", "_"), bytes);
        }

        //压缩下载js文件流
        for (Map.Entry<String, byte[]> entry : mapDownloadUrl.entrySet()) {
            String urlTmp = entry.getKey();
            byte[] bytes = entry.getValue();
            ZipUtils.compress(zipOutputStream, urlTmp.replace("/", "_"), bytes);
        }
        zipOutputStream.closeEntry();
        zipOutputStream.close();
        return result;
    }


    /**
     * 生成info
     *
     * @param url
     * @param bisName
     * @param criteriaId
     * @param hostName
     * @param otherDownloadUrls 其他下载的url
     * @return
     */
    public Info generateInfo(String url,
                             String bisName,
                             String criteriaId,
                             String hostName,
                             Collection<String> otherDownloadUrls

    ) {

        String downLoadTime = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date());
        return new Info(url, bisName, downLoadTime, criteriaId, hostName, otherDownloadUrls);
    }

    /**
     * 下载详情页HTML
     *
     * @param headless 是否无头
     * @param url      目标url
     * @return
     */
    public String downloadDetailPageSource(String url, boolean headless, boolean useDriver) throws IOException {
        String pageSource;
        if (true == useDriver) {
            pageSource = this.downloadDetailPageSourceWithDriver(url, headless);
        } else {
            pageSource = this.downloadDetailPageSourceWithOutDriver(url);
        }
        return pageSource;
    }

    /**
     * 下载详情页HTML -> 不使用驱动下载
     *
     * @param url 目标url
     * @return
     */
    public String downloadDetailPageSourceWithOutDriver(String url) throws IOException {
        return IOUtils.toString(new URL(url), "GB2312");
    }

    /**
     * 下载详情页HTML -> 不使用驱动下载
     *
     * @param url 目标url
     * @return
     */
    public String downloadDetailPageSourceWithDriver(String url, boolean headless) throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("-lang=zh-cn");
        if (true == headless) {
            options.addArguments("--headless");
        }
        // 实例化ChromeDriver
        WebDriver driver = new ChromeDriver(options);
        // 启动浏览器 打开utl
        driver.navigate().to(url);
        ChromeDriver chromeDriver = ((ChromeDriver) driver);
        return chromeDriver.getPageSource();
    }


    /**
     * 下载详情页的图片
     */
    public Map<String, byte[]> downloadDetailPic(String host, String pageSource) throws IOException {
        Map<String, byte[]> map = new HashMap<>();
        Document document = Jsoup.parse(pageSource);
        Elements imgs = document.getElementsByTag("img");
        Set<String> urls = new HashSet<>();
        imgs.forEach(img -> {
            String url = img.attr("src");
            urls.add(url);
        });
        //开始下载
        urls.parallelStream().forEach(url -> {
            String realUrl = null;
            if (url.startsWith("/")) {
                realUrl = host + url;
            } else {
                realUrl = url;
            }
            /**
             * 过滤广告图片
             */
            if (url.matches("/\\d+adimg/.*")) {
                //如果是广告，就不下载
                return;
            }
            try {
                map.put(url, DownLoadUtil.downloadFileByUrl(realUrl));
            } catch (Exception e) {
                log.error("下载发生异常:{}", e.toString(), e);
                log.error("host:{},url:{},目标url:{}", host, url, realUrl);
            }
            log.info("host:{},url:{},目标url:{}", host, url, realUrl);
        });
        return map;
    }

    /**
     * 下载详情页的下载url
     * ->预期的
     * <p>
     * <p>
     * document.writeln("<a href=https://www.chenxunyun.com/cloud.html target=_blank><font color=red>该源码可完美运行于辰迅云服务器</font></a>");
     * document.writeln("<ul class=software-download> ");
     * document.writeln("<li><a href='/dllDown/?CodeID=69891&id=1' target=_blank><em>[讯飞云高速下载]</em></a> <a href='/dllDown/?CodeID=69891&id=2' target=_blank><em>[招远网通下载点]</em></a></li> ");
     * document.writeln("<li><a href='/dllDown/?CodeID=69891&id=3' target=_blank><em>[招远网通下载点]</em></a> <a href='/dllDown/?CodeID=69891&id=4' target=_blank><em>[双云上海下载点]</em></a></li> ");
     * document.writeln("<li><a href='/dllDown/?CodeID=69891&id=5' target=_blank><em>[北京世博下载点]</em></a> <a href='/dllDown/?CodeID=69891&id=6' target=_blank><em>[腾佑联通下载点]</em></a></li> ");
     * document.writeln("<li><a href='/dllDown/?CodeID=69891&id=7' target=_blank><em>[双云上海下载点]</em></a> <a href='/dllDown/?CodeID=69891&id=8' target=_blank><em>[景安快云下载点]</em></a></li> ");
     * document.writeln("<li><a href='/dllDown/?CodeID=69891&id=9' target=_blank><em>[亿速电信下载点]</em></a> <a href='/dllDown/?CodeID=69891&id=1' target=_blank><em>[千喜电信下载点]</em></a></li> ");
     * document.writeln("</ul> ");
     * document.writeln("<a href=https://www.8dwww.com/server/ target=_blank><img src='/2012adimg/8dwww.gif'></a>");
     */
    public Map<String, byte[]> downloadDownloadList(String host, String pageSource) throws IOException {
        Map<String, byte[]> map = new HashMap<>();
        Document document = Jsoup.parse(pageSource);
        int i = 0;
        Elements downlistElements = document.getElementsByClass("downlist");//预期class只有一个
        for (Element downlistElement : downlistElements) {
            Elements scriptElements = downlistElement.getElementsByTag("script");
            for (Element element : scriptElements) {
                String jsUrl = element.attr("src");
                String realUrl = null;
                if (jsUrl.startsWith("/")) {
                    realUrl = host + jsUrl;
                } else {
                    realUrl = jsUrl;
                }
                String downloadUrlsSource = IOUtils.toString(new URL(realUrl), StandardCharsets.UTF_8);
                String key = "down";
                String suffix = ".js";
                if (i == 0) {
                    key += suffix;
                } else {
                    key = key + i + suffix;
                }
                map.put(key, downloadUrlsSource.getBytes());
                i++;
            }
        }
        return map;
    }

    /**
     * 获取网盘地址
     */
    public String downloadDownloadVipPan(String host, Map<String, byte[]> keyToPageSourceBytes) throws IOException {
        String pageSourceResult = "";
        for (Map.Entry<String, byte[]> entry : keyToPageSourceBytes.entrySet()) {
            String key = entry.getKey();
            byte[] pageSourceBytes = entry.getValue();
            String pageSource = IOUtils.toString(pageSourceBytes, "UTF-8");//下载的url
            Document document = Jsoup.parse(pageSource);
            Elements as = document.getElementsByTag("a");
            if (as.size() > 3) {
                String href = as.get(2).attr("href");
                String tmp = host + href;
//                String cookieValue = "Hm_lvt_d7682ab43891c68a00de46e9ce5b76aa=1576302267,1576385911; UM_distinctid=1742e5229f23f2-0fb7b68938e4ca-393e5809-1fa400-1742e5229f38f0; LiuLanJiLu%5Fstr=70082%24%242716%24%24%A1%BE%CD%EA%C3%C0%D4%CB%D3%AA%B0%E6%A1%BF%C7%D7%B2%E2%CE%DE%CE%CA%CC%E2%B0%AE%B5%E3%D4%DE%B6%B6%D2%F4%B5%E3%D4%DE%C8%CE%CE%F1%C6%BD%CC%A8%CD%EA%C3%C0%D0%DE%B8%B4%B0%E6%B6%CC%D0%C5%B8%C4%C8%A5%B0%A2%C0%EF%D4%C6%C8%AB%CC%D7%D4%B4%C2%EB%24%24%2F2012uploadpic%2F2020%2D9%2D9%2F700822020991033361%5F110%2Egif%7C%7C64621%24%242708%24%24%D7%EE%D0%C2JAVA+EE+MVC%BC%DC%B9%B9%B2%CD%D2%FB%B9%DC%C0%ED%CF%B5%CD%B3%D4%B4%C2%EB+MYSQL%CA%FD%BE%DD%BF%E2MVC%BC%DC%B9%B9%B2%CD%D2%FB%B9%DC%C0%ED%CF%B5%CD%B3%D4%B4%C2%EB%24%24%2F2012uploadpic%2F2018%2D11%2D1%2F6462120181111840061%5F110%2Egif%7C%7C69309%24%242703%24%24PHP%B7%C2%D6%ED%B0%CB%BD%E4%CD%FE%BF%CD%CD%F8%D5%FB%D5%BE%D4%B4%C2%EB%24%24%2F2012uploadpic%2F2020%2D6%2D30%2F6930920206301519251%5F110%2Egif%7C%7C69658%24%241111%24%24%D3%A9%BB%F0ThinkPHP%CE%A2%D0%C5%D0%A1%B3%CC%D0%F2%BF%AA%D4%B4%C9%CC%B3%C7+v1%2E1%2E05%24%24%2F2012uploadpic%2F2020%2D7%2D27%2F6965820207271030151%5F110%2Egif%7C%7C69116%24%242708%24%24java%2Bmysql%D0%A3%D4%B0%D1%A7%D0%A3%CB%DE%C9%E1%B9%DC%C0%ED%CF%B5%CD%B3%D4%B4%C2%EB%24%24%2F2012uploadpic%2F2020%2D6%2D7%2F691162020671738591%5F110%2Egif%7C%7C69882%24%242708%24%24java%2Bssh%2Bmysql%BE%C6%B5%EA%CD%F8%D5%BE%B9%DC%C0%ED%CF%B5%CD%B3%D4%B4%C2%EB%24%24%2F2012uploadpic%2F2020%2D8%2D18%2F698822020818848171%5F110%2Egif%7C%7C; ASPSESSIONIDAAATCASR=KHLLNADAHIPBHEEPFCIDKPMI; Hm_lvt_b56620492a36e2654a40162e6dcf4864=1598946186,1599450794,1599457456,1599641196; Hm_lvt_e74ddd953605e0810eb230f1b13ce662=1598946187,1599450796,1599457457,1599641197; UserType=5; Enddate=2040%2F9%2F8; loginok=True; username=hcwang%2Ddocker; CNZZDATA1254065253=429477519-1598501450-http%253A%252F%252Fwww.asp300.net%252F%7C1599650291; bj%5Fip%5Fid=1; bj%5Fcode%5Fname=java%2Bssh%2Bmysql%BE%C6%B5%EA%CD%F8%D5%BE%B9%DC%C0%ED%CF%B5%CD%B3%D4%B4%C2%EB; bj%5Fcode%5Fid=69882; Hm_lpvt_e74ddd953605e0810eb230f1b13ce662=1599650770; lastLoginDate=2020%2F9%2F9+16%3A46%3A00; Hm_lpvt_b56620492a36e2654a40162e6dcf4864=1599650800";
                String cookieValue = "lastLoginDate=2020%2F9%2F9+19%3A54%3A00; UserType=5; loginok=True; username=hcwang%2Ddocker; Enddate=2040%2F9%2F8;";
                pageSourceResult = URLUtils.toString(tmp, cookieValue, "gb2312");
                log.info("");
            }
        }
        return pageSourceResult;
    }


    /**
     * 根据页面是source获取list任务(相对路径)
     *
     * @param pageSource
     * @return
     */
    private Set<String> getListHref(String pageSource) {
        Set<String> downUrls = new HashSet<>();
        Document document = Jsoup.parse(pageSource);
        Elements cat_boxs = document.getElementsByClass("cat_box");
        if (cat_boxs.size() > 0) {
            cat_boxs.get(0).getElementsByClass("pages").remove();//移除混淆的分页
            Elements as = cat_boxs.get(0).getElementsByTag("a");
            if (as.size() > 0) {
                as.forEach(a -> {
                    String href = a.attr("href");
                    if (StringUtils.isNotBlank(href)) {
                        /**
                         * /SoftView/27/SoftView_69998.html
                         */
                        downUrls.add(href);

                    }
                });
            }
        }
        return downUrls;
    }


    /**
     * ------------------------------------------
     */

    /**
     * http://www.asp300.net/dllDown/?CodeID=8872&id=1
     */
    public static final String prefix = "http://www.asp300.net/dllDown/?CodeID=";
    public static final String suffix = "\\&id=1";

    /**
     * 类似这个网站 -> 获取href
     * http://www.asp300.net/SoftList/11/11_1.html
     *
     * @param documentStr
     * @return
     */
    public static Set<String> getHrefFromListPage(String documentStr) {

        Set<String> downUrls = new HashSet<>();
        Document document = Jsoup.parse(documentStr);
        Elements cat_boxs = document.getElementsByClass("cat_box");
        if (cat_boxs.size() > 0) {
            Elements as = cat_boxs.get(0).getElementsByTag("a");
            if (as.size() > 0) {
                as.forEach(a -> {
                    String href = a.attr("href");
                    if (StringUtils.isNotBlank(href)) {
                        /**
                         * SoftView_8872.html
                         */
                        String id = RegexUtil.RegexValueListFirst(href, "SoftView_(\\d*?).html", 1);
                        if (StringUtils.isNotBlank(id)) {
                            downUrls.add(prefix + id + suffix);
                        }

                    }
                });
            }
        }
        return downUrls;
    }


}
