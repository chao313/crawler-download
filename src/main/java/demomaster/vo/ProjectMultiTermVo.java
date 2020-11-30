package demomaster.vo;

import demo.spring.boot.demospringboot.framework.MultiTerm;

/**
 * 表名称      :project
 * 表类型      :BASE TABLE
 * 表引擎      :InnoDB
 * 表版本      :10
 * 行格式      :Dynamic
 * 表创建      :2020-11-30
 * 字符集      :utf8_bin
 * 表注释      :
 */
public class ProjectMultiTermVo {

    private MultiTerm<Integer> id; 
    private MultiTerm<String> criteriaid;  // ASP唯一id
    private MultiTerm<String> projectName;  // 项目名称
    private MultiTerm<String> projectUpdateTime;  // 项目更新时间
    private MultiTerm<String> projectType;  // 类型(VIP/COMMON)
    private MultiTerm<String> projectZipStatus;  // 项目的下载包类型(流/盘)
    private MultiTerm<String> projectPanAddress;  // 项目的网盘地址
    private MultiTerm<String> fileRealName;  // 真实文件名称
    private MultiTerm<String> dockerZipName;  // docker压缩包的真实名称
    private MultiTerm<String> htmlBody;  // 网页Body
    private MultiTerm<String> sourceUrl;  // ASPURL
    private MultiTerm<String> language;  // 项目语言
    private MultiTerm<String> size2;  // 项目大小
    private MultiTerm<String> sizeNum;  // 项目大小数字
    private MultiTerm<String> sizeType;  // 项目大小type(k,M,G)
    private MultiTerm<String> officialWebsite;  // 官网地址
    private MultiTerm<String> showWebsite;  // 展示网址
    private MultiTerm<String> downloadSum;  // 项目下载次数
    private MultiTerm<String> introduction;  // 项目介绍(文字)
    private MultiTerm<String> contentImgs;  // 项目介绍(图片)
    private MultiTerm<String> runtime;  // 运行环境
    private MultiTerm<String> downloadUrls;  // 项目下载URL
    private MultiTerm<String> dockerPort;  // docker的端口号
    private MultiTerm<String> dockerImageName;  // docker的镜像名称
    private MultiTerm<String> dockerContainerName;  // docker的容器名称
    private MultiTerm<String> dockerShellCreate;  // docker的创建命令
    private MultiTerm<String> dockerShellRun;  // docker的启动命令
    private MultiTerm<String> dockerShellStop;  // docker的停止命令
    private MultiTerm<String> dockerShellContainerRemove;  // docker容器移除命令
    private MultiTerm<String> dockerShellImageRemove;  // docker镜像移除命令
    private MultiTerm<String> dockerStatus;  // docker状态,创建.运行中,停止,容器移除,镜像移除
    private MultiTerm<String> projectStatus;  // project状态,没有状态,可以使用,暂不可使用,完全不能使用
    private MultiTerm<String> httpInnerAddress;  // 内网的访问地址
    private MultiTerm<String> httpOutAddress;  // 外网的访问地址


    public MultiTerm<Integer> getId() {

        return id;

    }

    public void setId(MultiTerm<Integer> id) {

        this.id = id;

    }
    public MultiTerm<String> getCriteriaid() {

        return criteriaid;

    }

    public void setCriteriaid(MultiTerm<String> criteriaid) {

        this.criteriaid = criteriaid;

    }
    public MultiTerm<String> getProjectName() {

        return projectName;

    }

    public void setProjectName(MultiTerm<String> projectName) {

        this.projectName = projectName;

    }
    public MultiTerm<String> getProjectUpdateTime() {

        return projectUpdateTime;

    }

    public void setProjectUpdateTime(MultiTerm<String> projectUpdateTime) {

        this.projectUpdateTime = projectUpdateTime;

    }
    public MultiTerm<String> getProjectType() {

        return projectType;

    }

    public void setProjectType(MultiTerm<String> projectType) {

        this.projectType = projectType;

    }
    public MultiTerm<String> getProjectZipStatus() {

        return projectZipStatus;

    }

    public void setProjectZipStatus(MultiTerm<String> projectZipStatus) {

        this.projectZipStatus = projectZipStatus;

    }
    public MultiTerm<String> getProjectPanAddress() {

        return projectPanAddress;

    }

    public void setProjectPanAddress(MultiTerm<String> projectPanAddress) {

        this.projectPanAddress = projectPanAddress;

    }
    public MultiTerm<String> getFileRealName() {

        return fileRealName;

    }

    public void setFileRealName(MultiTerm<String> fileRealName) {

        this.fileRealName = fileRealName;

    }
    public MultiTerm<String> getDockerZipName() {

        return dockerZipName;

    }

    public void setDockerZipName(MultiTerm<String> dockerZipName) {

        this.dockerZipName = dockerZipName;

    }
    public MultiTerm<String> getHtmlBody() {

        return htmlBody;

    }

    public void setHtmlBody(MultiTerm<String> htmlBody) {

        this.htmlBody = htmlBody;

    }
    public MultiTerm<String> getSourceUrl() {

        return sourceUrl;

    }

    public void setSourceUrl(MultiTerm<String> sourceUrl) {

        this.sourceUrl = sourceUrl;

    }
    public MultiTerm<String> getLanguage() {

        return language;

    }

    public void setLanguage(MultiTerm<String> language) {

        this.language = language;

    }
    public MultiTerm<String> getSize2() {

        return size2;

    }

    public void setSize2(MultiTerm<String> size2) {

        this.size2 = size2;

    }
    public MultiTerm<String> getSizeNum() {

        return sizeNum;

    }

    public void setSizeNum(MultiTerm<String> sizeNum) {

        this.sizeNum = sizeNum;

    }
    public MultiTerm<String> getSizeType() {

        return sizeType;

    }

    public void setSizeType(MultiTerm<String> sizeType) {

        this.sizeType = sizeType;

    }
    public MultiTerm<String> getOfficialWebsite() {

        return officialWebsite;

    }

    public void setOfficialWebsite(MultiTerm<String> officialWebsite) {

        this.officialWebsite = officialWebsite;

    }
    public MultiTerm<String> getShowWebsite() {

        return showWebsite;

    }

    public void setShowWebsite(MultiTerm<String> showWebsite) {

        this.showWebsite = showWebsite;

    }
    public MultiTerm<String> getDownloadSum() {

        return downloadSum;

    }

    public void setDownloadSum(MultiTerm<String> downloadSum) {

        this.downloadSum = downloadSum;

    }
    public MultiTerm<String> getIntroduction() {

        return introduction;

    }

    public void setIntroduction(MultiTerm<String> introduction) {

        this.introduction = introduction;

    }
    public MultiTerm<String> getContentImgs() {

        return contentImgs;

    }

    public void setContentImgs(MultiTerm<String> contentImgs) {

        this.contentImgs = contentImgs;

    }
    public MultiTerm<String> getRuntime() {

        return runtime;

    }

    public void setRuntime(MultiTerm<String> runtime) {

        this.runtime = runtime;

    }
    public MultiTerm<String> getDownloadUrls() {

        return downloadUrls;

    }

    public void setDownloadUrls(MultiTerm<String> downloadUrls) {

        this.downloadUrls = downloadUrls;

    }
    public MultiTerm<String> getDockerPort() {

        return dockerPort;

    }

    public void setDockerPort(MultiTerm<String> dockerPort) {

        this.dockerPort = dockerPort;

    }
    public MultiTerm<String> getDockerImageName() {

        return dockerImageName;

    }

    public void setDockerImageName(MultiTerm<String> dockerImageName) {

        this.dockerImageName = dockerImageName;

    }
    public MultiTerm<String> getDockerContainerName() {

        return dockerContainerName;

    }

    public void setDockerContainerName(MultiTerm<String> dockerContainerName) {

        this.dockerContainerName = dockerContainerName;

    }
    public MultiTerm<String> getDockerShellCreate() {

        return dockerShellCreate;

    }

    public void setDockerShellCreate(MultiTerm<String> dockerShellCreate) {

        this.dockerShellCreate = dockerShellCreate;

    }
    public MultiTerm<String> getDockerShellRun() {

        return dockerShellRun;

    }

    public void setDockerShellRun(MultiTerm<String> dockerShellRun) {

        this.dockerShellRun = dockerShellRun;

    }
    public MultiTerm<String> getDockerShellStop() {

        return dockerShellStop;

    }

    public void setDockerShellStop(MultiTerm<String> dockerShellStop) {

        this.dockerShellStop = dockerShellStop;

    }
    public MultiTerm<String> getDockerShellContainerRemove() {

        return dockerShellContainerRemove;

    }

    public void setDockerShellContainerRemove(MultiTerm<String> dockerShellContainerRemove) {

        this.dockerShellContainerRemove = dockerShellContainerRemove;

    }
    public MultiTerm<String> getDockerShellImageRemove() {

        return dockerShellImageRemove;

    }

    public void setDockerShellImageRemove(MultiTerm<String> dockerShellImageRemove) {

        this.dockerShellImageRemove = dockerShellImageRemove;

    }
    public MultiTerm<String> getDockerStatus() {

        return dockerStatus;

    }

    public void setDockerStatus(MultiTerm<String> dockerStatus) {

        this.dockerStatus = dockerStatus;

    }
    public MultiTerm<String> getProjectStatus() {

        return projectStatus;

    }

    public void setProjectStatus(MultiTerm<String> projectStatus) {

        this.projectStatus = projectStatus;

    }
    public MultiTerm<String> getHttpInnerAddress() {

        return httpInnerAddress;

    }

    public void setHttpInnerAddress(MultiTerm<String> httpInnerAddress) {

        this.httpInnerAddress = httpInnerAddress;

    }
    public MultiTerm<String> getHttpOutAddress() {

        return httpOutAddress;

    }

    public void setHttpOutAddress(MultiTerm<String> httpOutAddress) {

        this.httpOutAddress = httpOutAddress;

    }

    @Override
    public String toString() {
        return "ProjectMultiTermVo{" +
                ", id '" + id +
                ", criteriaid '" + criteriaid + '\'' +
                ", projectName '" + projectName + '\'' +
                ", projectUpdateTime '" + projectUpdateTime + '\'' +
                ", projectType '" + projectType + '\'' +
                ", projectZipStatus '" + projectZipStatus + '\'' +
                ", projectPanAddress '" + projectPanAddress + '\'' +
                ", fileRealName '" + fileRealName + '\'' +
                ", dockerZipName '" + dockerZipName + '\'' +
                ", htmlBody '" + htmlBody + '\'' +
                ", sourceUrl '" + sourceUrl + '\'' +
                ", language '" + language + '\'' +
                ", size2 '" + size2 + '\'' +
                ", sizeNum '" + sizeNum + '\'' +
                ", sizeType '" + sizeType + '\'' +
                ", officialWebsite '" + officialWebsite + '\'' +
                ", showWebsite '" + showWebsite + '\'' +
                ", downloadSum '" + downloadSum + '\'' +
                ", introduction '" + introduction + '\'' +
                ", contentImgs '" + contentImgs + '\'' +
                ", runtime '" + runtime + '\'' +
                ", downloadUrls '" + downloadUrls + '\'' +
                ", dockerPort '" + dockerPort + '\'' +
                ", dockerImageName '" + dockerImageName + '\'' +
                ", dockerContainerName '" + dockerContainerName + '\'' +
                ", dockerShellCreate '" + dockerShellCreate + '\'' +
                ", dockerShellRun '" + dockerShellRun + '\'' +
                ", dockerShellStop '" + dockerShellStop + '\'' +
                ", dockerShellContainerRemove '" + dockerShellContainerRemove + '\'' +
                ", dockerShellImageRemove '" + dockerShellImageRemove + '\'' +
                ", dockerStatus '" + dockerStatus + '\'' +
                ", projectStatus '" + projectStatus + '\'' +
                ", httpInnerAddress '" + httpInnerAddress + '\'' +
                ", httpOutAddress '" + httpOutAddress + '\'' +
                '}';
    }

}
