package demo.spring.boot.demospringboot.service.download.impl;

import demo.spring.boot.demospringboot.controller.resource.service.ResourceService;
import demo.spring.boot.demospringboot.service.asp.Asp300FeignService;
import demo.spring.boot.demospringboot.service.download.DownloadAndParse;
import demo.spring.boot.demospringboot.util.*;
import demomaster.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 默认的下载和解析类
 */
@Slf4j
@Component("DefaultDownloadAndParse")
public class DefaultDownloadAndParse extends DownloadAndParse {

    private static final String tag = "blockquote";


    @Resource
    private Asp300FeignService asp300FeignService;


    @Autowired
    private DefaultDownloadService defaultDownloadService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ProjectService projectService;

    private static final String PHP_PATH = "/Users/chao/Downloads/PHP";

    /**
     * 根据指定的url来下载详情页
     */
    @Override
    protected String getHtmlDetail(String url, String encoding) throws IOException {
        return defaultDownloadService.downloadPageSource(url, encoding);
    }

    /**
     * 获取描述
     */
    @Override
    protected String getDescByHtmlDetail(String htmlDetail) {
        String descByHtmlDetail = Jsoup.parse(htmlDetail).getElementsByTag(tag).toString();

        String baseContent = Jsoup.clean(descByHtmlDetail, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false));
        String newText = baseContent.replaceAll("\\s{2,}", "\n");
        String trueContent = newText.replaceFirst("\n", "").trim();
        return trueContent;
    }

    @Override
    protected Map<String, byte[]> getImgByHtmlDetail(String htmlDetail, String host, String criteriaId) throws IOException {
        Map<String, byte[]> result = new HashMap<>();
        Elements imgs = new Elements();
        Jsoup.parse(htmlDetail).getElementsByTag(tag).forEach(element -> {
            Elements img = element.getElementsByTag("img");
            imgs.addAll(img);
        });
        List<String> imgUrls = new ArrayList<>();
        imgs.forEach(imgElement -> {
            String src = imgElement.attr("src");
            imgUrls.add(src);
        });
        String regex = ".*(\\..*)";//用于提取后缀名称
        int i = 0;
        for (String url : imgUrls) {
            if (!url.startsWith("http")) {
                //如果不是https开头,补全
                url = host + url;
            }
            result.put(criteriaId + "_" + String.valueOf(i++) + url.replaceAll(regex, "$1"), DownLoadUtil.downloadFileByUrl(url));
        }
        return result;
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
    @Override
    protected List<String> getDownloadListByHtmlDetail(String htmlDetail, String host, String encoding) throws IOException {
        List<String> result = new ArrayList<>();
        int i = 0;
        Elements downlistElements = Jsoup.parse(htmlDetail).getElementsByClass("downlist");//预期class只有一个
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
                String downloadUrlsSource = IOUtils.toString(new URL(realUrl), encoding);
                Elements as = Jsoup.parse(downloadUrlsSource).getElementsByTag("a");
                as.forEach(aElement -> {
                    String href = aElement.attr("href");
                    if (href.contains("www.chenxunyun.com") || href.contains("www.8dwww.com")) {
                        return;
                    }
                    result.add(href);
                });
            }
        }
        return result;
    }

    @Override
    protected String downloadZipByList(List<String> downloadList, String host, String criteriaId, String workDirAbsolutePath, String cookie, AtomicReference<URLUtils.Type> type) throws IOException {
        String filePath = workDirAbsolutePath + "/" + criteriaId;//下载包的真实路径
        String urlToDownload = downloadList.get(0);
        if (!urlToDownload.startsWith("http")) {
            urlToDownload = host + urlToDownload;
        }
        InputStream inputStream = URLUtils.getDataByType(urlToDownload, cookie, type);
        //处理流
        if (URLUtils.Type.stream.equals(type.get())) {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, outputStream);
            outputStream.close();
        } else if (URLUtils.Type.text.equals(type.get())) {
            String html = IOUtils.toString(inputStream, "GB2312");
            String urlAndPass = this.getUrlAndPass(html);
            type.get().setPanAddress(urlAndPass);//存放盘的地址
            log.info("url+密码:{}", urlAndPass);
            /**
             * 这里兼容本地获取文件
             */
//            ProjectVo query = new ProjectVo();
//            query.setCriteriaid(criteriaId);
//            List<ProjectVo> projectVos = projectService.queryBase(query);
//            if (projectVos.size() > 0) {
//                String projectPanRealName = projectVos.get(0).getFileRealName();
//                File file_php = new File(PHP_PATH + "/" + projectPanRealName);
//                if (file_php.exists()) {
//                    IOUtils.copy(new FileInputStream(file_php), outputStream);
//                }
//
//            }
        }
        return filePath;
    }

    @Override
    protected String transformToZip(String filePath, String workDirAbsolutePath, String localFsPathTmp, String zipName) throws IOException {
        String dirPath = localFsPathTmp + "/" + UUIDUtils.generateUUID();
        String toZipFilePath = workDirAbsolutePath + "/" + zipName;
        SevenZipUtils.unzip(filePath, dirPath, null);
        ZipUtils.toZip(dirPath, new FileOutputStream(toZipFilePath), true);
        try {
            //后续删除操作
            FileUtils.deleteFile(filePath);//删除原始文件
            FileUtils.deleteDirectory(dirPath);//删除临时文件
        } catch (Exception e) {
            log.error("删除文件夹失败 e:{}", e.toString(), e);
        }

        return toZipFilePath;
    }


    /**
     * 获取pan的下载地址和url
     *
     * @param pageSourceResult
     * @return
     */
    public String getUrlAndPass(String pageSourceResult) {
        Document document = Jsoup.parse(pageSourceResult);

        Elements ps = document.getElementsByTag("p");

        String href = "";
        String passwd = "";
        for (Element p : ps) {
            if (p.text().contains("百度网盘链接")) {
                href = p.text();
            } else if (p.text().contains("提取码")) {
                passwd = p.text();
            }
        }
        return href + "," + passwd;
    }

}
