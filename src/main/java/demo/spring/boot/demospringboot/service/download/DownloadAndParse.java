package demo.spring.boot.demospringboot.service.download;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * 模板方法 -> 指定步骤
 */
@Slf4j
@Component
public abstract class DownloadAndParse {

    /**
     * 根据指定的url来下载详情页
     */
    protected abstract String getHtmlDetail(String url, String encoding) throws IOException;

    /**
     * 根据详情页获取描述
     */
    protected abstract String getDescByHtmlDetail(String htmlDetail);

    /**
     * 根据详情页下载图片
     */
    protected abstract Map<String, byte[]> getImgByHtmlDetail(String htmlDetail, String host) throws IOException;

    /**
     * 下载下载列表
     */
    protected abstract List<String> getDownloadListByHtmlDetail(String htmlDetail, String host, String encoding) throws Exception;


    /**
     * 根据下载列表 -> 下载真实的下载包
     * 注意:这里需要注意是是百度云如何处理
     * 返回的包的真实路径
     */
    protected abstract String downloadZipByList(List<String> downloadList, String host, String criteriaId, String workDirAbsolutePath) throws IOException;


    /**
     * 统一把rar转换成zip
     * 返回的包的真实路径
     */
    protected abstract String transformToZip(String filePath, String workDirAbsolutePath) throws IOException;

    /**
     * @param url                 需要处理的url
     * @param workDirAbsolutePath 工作文件夹
     */
    public boolean doWork(String url,
                          String encoding,
                          String workDirAbsolutePath) throws Exception {

        String regex = "(.*?)/SoftView/(.*?).html";
        String host = url.replaceAll(regex, "$1");
        String criteriaId = url.replaceAll(regex, "$2");
        criteriaId = criteriaId.replace("/", "_");//斜杠转换成_
        String htmlDetail = this.getHtmlDetail(url, encoding);
        log.info("获取htmlDetail");
        String descByHtmlDetail = this.getDescByHtmlDetail(htmlDetail);
        log.info("获取描述:{}", descByHtmlDetail);
        Map<String, byte[]> imgByHtmlDetail = this.getImgByHtmlDetail(htmlDetail, host);
        log.info("下载图片:{}", imgByHtmlDetail.keySet());
        List<String> downloadListByHtmlDetail = this.getDownloadListByHtmlDetail(htmlDetail, host, encoding);
        log.info("下载下载列表:{}", downloadListByHtmlDetail);
        String filePath = this.downloadZipByList(downloadListByHtmlDetail, host, criteriaId, workDirAbsolutePath);
        log.info("zip包下载绝对地址:{}", filePath);
        String filePathAfterTransformed = this.transformToZip(filePath, workDirAbsolutePath);
        log.info("zip转换后的绝对地址:{}", filePathAfterTransformed);
        return true;
    }

}
