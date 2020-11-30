package demo.spring.boot.demospringboot.service.download;

import demo.spring.boot.demospringboot.config.DockerStructure;
import demo.spring.boot.demospringboot.service.zip.UnzipToDocker;
import demo.spring.boot.demospringboot.util.URLUtils;
import demomaster.vo.ProjectVo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 模板方法 -> 指定步骤
 */
@Slf4j
@Component
public abstract class DownloadAndParse {

    @Autowired
    private UnzipToDocker unzipToDocker;

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
    protected abstract Map<String, byte[]> getImgByHtmlDetail(String htmlDetail, String host, String criteriaId) throws IOException;

    /**
     * 下载下载列表
     */
    protected abstract List<String> getDownloadListByHtmlDetail(String htmlDetail, String host, String encoding) throws Exception;


    /**
     * 根据下载列表 -> 下载真实的下载包
     * 注意:这里需要注意是是百度云如何处理
     * 返回的包的真实路径
     */
    protected abstract String downloadZipByList(List<String> downloadList, String host, String criteriaId, String workDirAbsolutePath, String cookie, AtomicReference<URLUtils.Type> type) throws IOException;


    /**
     * 统一把rar转换成zip
     * 返回的包的真实路径
     */
    protected abstract String transformToZip(String filePath, String workDirAbsolutePath, String zipName) throws IOException;


    /**
     * @param url                 需要处理的url
     * @param workDirAbsolutePath 工作文件夹
     */
    public ProjectVo doWork(String url,
                            String encoding,
                            String workDirAbsolutePath,
                            String cookie,
                            Integer port) throws Exception {
        Map<String, byte[]> descMap = new HashMap<>();
        String regex = "(.*?)/SoftView/(.*?).html";
        String host = url.replaceAll(regex, "$1");
        String criteriaId = url.replaceAll(regex, "$2");
        criteriaId = criteriaId.replace("/", "_");//斜杠转换成_
        String htmlDetail = this.getHtmlDetail(url, encoding);
        log.info("获取htmlDetail");
        String descByHtmlDetail = this.getDescByHtmlDetail(htmlDetail);
        descMap.put(DockerStructure.DSC_TXT_NAME, descByHtmlDetail.getBytes());//存放描述的map
        log.info("获取描述:{}", descByHtmlDetail);
        Map<String, byte[]> imgByHtmlDetail = this.getImgByHtmlDetail(htmlDetail, host, criteriaId);
        descMap.putAll(imgByHtmlDetail);
        log.info("下载图片:{}", imgByHtmlDetail.keySet());
        List<String> downloadListByHtmlDetail = this.getDownloadListByHtmlDetail(htmlDetail, host, encoding);
        log.info("下载下载列表:{}", downloadListByHtmlDetail);
        AtomicReference<URLUtils.Type> type = new AtomicReference<>();
        String filePath = this.downloadZipByList(downloadListByHtmlDetail, host, criteriaId, workDirAbsolutePath, cookie, type);
        log.info("zip包下载绝对地址:{}", filePath);
        String filePathAfterTransformed = this.transformToZip(filePath, workDirAbsolutePath, criteriaId);
        log.info("zip转换后的绝对地址:{}", filePathAfterTransformed);
        //入库
        ProjectVo projectVo = new ProjectVo();
        //整合结果
        unzipToDocker.doWork(workDirAbsolutePath, workDirAbsolutePath, criteriaId + ".zip", DockerStructure.DOCKER_MODEL_Dir_Path, port, descMap, projectVo);
        //补全项目
        this.makeUpProjectVo(projectVo, htmlDetail, url, criteriaId, descByHtmlDetail, imgByHtmlDetail.keySet(), type);
        return projectVo;
    }

    private void makeUpProjectVo(ProjectVo vo, String body, String url, String criteriaId, String desc, Set<String> images, AtomicReference<URLUtils.Type> type) {
        Document parse = Jsoup.parse(body);
        Element goodsInfo = parse.getElementById("goodsInfo");
        String projectName = goodsInfo.getElementsByTag("h2").text();
        String projectUpdateTime = goodsInfo
                .getElementsMatchingOwnText("更新日期.*")
                .text().replace(" ", "")
                .replace("：", ":")
                .replaceAll("更新日期:(.*)", "$1");

        String language = goodsInfo
                .getElementsMatchingOwnText("语言编码.*")
                .text().replace(" ", "")
                .replace("：", ":")
                .replaceAll("语言编码:(.*)", "$1");


        String size2 = goodsInfo
                .getElementsMatchingOwnText("软件大小.*")
                .text().replace(" ", "")
                .replace("：", ":")
                .replaceAll("软件大小:(.*)", "$1");

        vo.setHtmlBody(body);
        vo.setProjectName(projectName);
        vo.setProjectUpdateTime(projectUpdateTime);
        vo.setLanguage(language);
        vo.setSize2(size2);
        vo.setSizeNum(size2.replaceAll("([0-9.]+)(.*)", "$1"));
        vo.setSizeType(size2.replaceAll("([0-9.]+)(.*)", "$2"));
        vo.setContentImgs(images.toString());
        vo.setIntroduction(desc);
        vo.setCriteriaid(criteriaId);
        vo.setSourceUrl(url);
        vo.setProjectZipStatus(type.get().getType());
        vo.setProjectPanAddress(type.get().getPanAddress());//存放pan的地址
    }

}
