package demo.spring.boot.demospringboot.service.asp;

import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.spring.boot.demospringboot.resource.service.ResourceService;
import demo.spring.boot.demospringboot.util.DownLoadUtil;
import demo.spring.boot.demospringboot.util.RegexUtil;
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
import java.net.MalformedURLException;
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
