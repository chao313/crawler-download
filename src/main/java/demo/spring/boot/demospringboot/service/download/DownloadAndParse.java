package demo.spring.boot.demospringboot.service.download;

import demo.spring.boot.demospringboot.config.StartConfig;
import demo.spring.boot.demospringboot.service.zip.UnzipToDocker;
import demo.spring.boot.demospringboot.util.URLUtils;
import demomaster.vo.ProjectVoBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 内聚当前的功能:只做数据收集工作
 * 只包含:1.页面数据,图片数据,介绍,下载包地址,pan地址
 * 其他的一概不设计要高内聚
 */
@Slf4j
@Component
public abstract class DownloadAndParse {


    @Autowired
    private StartConfig startConfig;

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
     *
     * @param filePath 真实文件地址
     */
    protected abstract String transformToZip(String filePath, String workDirAbsolutePath, String localFsPathTmp, String zipName) throws IOException;


    /**
     * @param url                 需要处理的url
     */
    public ProjectVoBase doWork(String url,
                                String encoding,
                                String cookie) throws Exception {
        String localFsPathOriginZip = startConfig.getLocalFsPathOriginZip();
        String localFsPathTmp = startConfig.getLocalFsPathTmp();
        String regex = "(.*?)/SoftView/(.*?).html";
        String host = url.replaceAll(regex, "$1");
        String criteriaId = url.replaceAll(regex, "$2");
        criteriaId = criteriaId.replace("/", "_");//斜杠转换成_
        String htmlDetail = this.getHtmlDetail(url, encoding);
        log.info("获取htmlDetail");
        String descByHtmlDetail = this.getDescByHtmlDetail(htmlDetail);
        log.info("获取描述:{}", descByHtmlDetail);
        Map<String, byte[]> imgByHtmlDetail = this.getImgByHtmlDetail(htmlDetail, host, criteriaId);
        //图片存储到指定的路径
        this.saveImg(imgByHtmlDetail);
        log.info("下载图片:{}", imgByHtmlDetail.keySet());
        List<String> downloadListByHtmlDetail = this.getDownloadListByHtmlDetail(htmlDetail, host, encoding);
        log.info("下载下载列表:{}", downloadListByHtmlDetail);
        AtomicReference<URLUtils.Type> type = new AtomicReference<>();
        String fileAbsolutePath = this.downloadZipByList(downloadListByHtmlDetail, host, criteriaId, localFsPathTmp, cookie, type);
        log.info("zip包下载绝对地址:{}", fileAbsolutePath);
        ProjectVoBase projectVoBase = new ProjectVoBase();
        if (type.get().equals(URLUtils.Type.stream)) {
            //输出为流 -> 转换为zip -> 存入真实地址
            String zipName = criteriaId + ".zip";
            String filePathAfterTransformed = this.transformToZip(fileAbsolutePath, localFsPathOriginZip, localFsPathTmp, zipName);
            log.info("zip转换后的绝对地址:{}", filePathAfterTransformed);
            projectVoBase.setFileRealName(zipName);//这里存放的是文件名称(路径会发生变化)
        } else {
            //输出为非流 -> 基本就是pan -> 存入盘地址
            projectVoBase.setProjectPanAddress(type.get().getPanAddress());
        }
        //入库

//        //整合结果
//        unzipToDocker.doWork(workDirAbsolutePath, workDirAbsolutePath, criteriaId + ".zip", DockerStructure.DOCKER_MODEL_Dir_Path, port, descMap, ProjectVoBase);
        //补全项目
        this.makeUpProjectVoBase(projectVoBase, htmlDetail, url, criteriaId, descByHtmlDetail, imgByHtmlDetail.keySet(), type);
        return projectVoBase;
    }

    private void makeUpProjectVoBase(ProjectVoBase vo, String body, String url, String criteriaId, String desc, Set<String> images, AtomicReference<URLUtils.Type> type) {
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

    /**
     * 保存至文件系统
     */
    private void saveImg(Map<String, byte[]> imgByHtmlDetail) throws IOException {
        String localFsPathImg = startConfig.getLocalFsPathImg();
        for (Map.Entry<String, byte[]> entry : imgByHtmlDetail.entrySet()) {
            String name = entry.getKey();
            byte[] bts = entry.getValue();
            String s = localFsPathImg + "/" + name;
            FileOutputStream outputStream = new FileOutputStream(s);
            IOUtils.write(bts, outputStream);
            outputStream.close();
        }
    }

}
