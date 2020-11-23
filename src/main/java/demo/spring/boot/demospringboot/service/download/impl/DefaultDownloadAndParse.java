package demo.spring.boot.demospringboot.service.download.impl;

import com.jcraft.jsch.IO;
import demo.spring.boot.demospringboot.service.asp.Asp300FeignService;
import demo.spring.boot.demospringboot.service.download.DownloadAndParse;
import demo.spring.boot.demospringboot.service.download.DownloadService;
import demo.spring.boot.demospringboot.util.DownLoadUtil;
import demo.spring.boot.demospringboot.util.URLUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

/**
 * 默认的下载和解析类
 */
@Slf4j
@Component("DefaultDownloadAndParse")
public class DefaultDownloadAndParse extends DownloadAndParse {

    private static final String tag = "blockquote";


    @Autowired
    private Asp300FeignService asp300FeignService;


    @Autowired
    private DefaultDownloadService defaultDownloadService;

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
        String descByHtmlDetail = Jsoup.parse(htmlDetail).getElementsByTag(tag).text();
        return descByHtmlDetail;
    }

    @Override
    protected Map<String, byte[]> getImgByHtmlDetail(String htmlDetail, String host) throws IOException {
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
        for (String url : imgUrls) {
            if (!url.startsWith("http")) {
                //如果不是https开头,补全
                url = host + url;
            }
            result.put(url, DownLoadUtil.downloadFileByUrl(url));
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
    protected String downloadZipByList(List<String> downloadList, String host, String criteriaId, String workDirAbsolutePath) throws IOException {
        String urlToDownload = downloadList.get(0);
        if (!urlToDownload.startsWith("http")) {
            urlToDownload = host + urlToDownload;
        }

//        String loginSource = asp300FeignService.login("hcwang-docker", "Ys20140913!", "+++%B5%C7+%C2%BC+++");
        String cookieByLogin = URLUtils.getCookieByLogin("http://www.asp300.net/2012user/login.asp?action=chk&url=http://www.asp300.net/", "hcwang-docker", "Ys20140913!");

        String cookieValue = cookieByLogin + ";UserType=5;";
        String pageSourceResult = URLUtils.toString(urlToDownload, cookieValue, "gb2312");

        byte[] bytes = DownloadService.downloadToBytesByUrl(urlToDownload);
        String filePath = workDirAbsolutePath + "/" + criteriaId;
        IOUtils.write(bytes, new FileOutputStream(filePath));
        return filePath;
    }

    @Override
    protected String transformToZip(String filePath, String workDirAbsolutePath) throws IOException {
        return null;
    }
}
