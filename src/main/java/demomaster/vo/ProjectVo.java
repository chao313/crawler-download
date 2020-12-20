package demomaster.vo;


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
public class ProjectVo {


    /**
     * 状态 zip状态 项目的下载包类型(流/盘)
     */
    public enum ZipStatus {
        PAN("pan"), STREAM("stream");
        private String status;

        ZipStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }


    private Integer id;
    private String criteriaid;  // ASP唯一id 
    private String projectName;  // 项目名称 
    private String projectUpdateTime;  // 项目更新时间 
    private String projectType;  // 类型(VIP/COMMON) 
    private String projectZipStatus;  // 项目的下载包类型(流/盘) 
    private String projectPanAddress;  // 项目的网盘地址 
    private String fileRealName;  // 真实文件名称 
    private String dockerZipName;  // docker压缩包的真实名称 
    private String htmlBody;  // 网页Body 
    private String sourceUrl;  // ASPURL 
    private String language;  // 项目语言 
    private String size2;  // 项目大小 
    private String sizeNum;  // 项目大小数字 
    private String sizeType;  // 项目大小type(k,M,G) 
    private String officialWebsite;  // 官网地址 
    private String showWebsite;  // 展示网址 
    private String downloadSum;  // 项目下载次数 
    private String introduction;  // 项目介绍(文字) 
    private String contentImgs;  // 项目介绍(图片) 
    private String runtime;  // 运行环境 
    private String downloadUrls;  // 项目下载URL 
    private String dockerPort;  // docker的端口号 
    private String dockerImageName;  // docker的镜像名称 
    private String dockerContainerName;  // docker的容器名称 
    private String dockerShellCreate;  // docker的创建命令 
    private String dockerShellRun;  // docker的启动命令 
    private String dockerShellStop;  // docker的停止命令 
    private String dockerShellContainerRemove;  // docker容器移除命令 
    private String dockerShellImageRemove;  // docker镜像移除命令 
    private String dockerStatus;  // docker状态,创建.运行中,停止,容器移除,镜像移除 
    private String projectStatus;  // project状态,没有状态,可以使用,暂不可使用,完全不能使用 
    private String httpInnerAddress;  // 内网的访问地址 
    private String httpOutAddress;  // 外网的访问地址 


    public Integer getId() {

        return id;

    }

    public void setId(Integer id) {

        this.id = id;

    }

    public String getCriteriaid() {

        return criteriaid;

    }

    public void setCriteriaid(String criteriaid) {

        this.criteriaid = criteriaid;

    }

    public String getProjectName() {

        return projectName;

    }

    public void setProjectName(String projectName) {

        this.projectName = projectName;

    }

    public String getProjectUpdateTime() {

        return projectUpdateTime;

    }

    public void setProjectUpdateTime(String projectUpdateTime) {

        this.projectUpdateTime = projectUpdateTime;

    }

    public String getProjectType() {

        return projectType;

    }

    public void setProjectType(String projectType) {

        this.projectType = projectType;

    }

    public String getProjectZipStatus() {

        return projectZipStatus;

    }

    public void setProjectZipStatus(String projectZipStatus) {

        this.projectZipStatus = projectZipStatus;

    }

    public String getProjectPanAddress() {

        return projectPanAddress;

    }

    public void setProjectPanAddress(String projectPanAddress) {

        this.projectPanAddress = projectPanAddress;

    }

    public String getFileRealName() {

        return fileRealName;

    }

    public void setFileRealName(String fileRealName) {

        this.fileRealName = fileRealName;

    }

    public String getDockerZipName() {

        return dockerZipName;

    }

    public void setDockerZipName(String dockerZipName) {

        this.dockerZipName = dockerZipName;

    }

    public String getHtmlBody() {

        return htmlBody;

    }

    public void setHtmlBody(String htmlBody) {

        this.htmlBody = htmlBody;

    }

    public String getSourceUrl() {

        return sourceUrl;

    }

    public void setSourceUrl(String sourceUrl) {

        this.sourceUrl = sourceUrl;

    }

    public String getLanguage() {

        return language;

    }

    public void setLanguage(String language) {

        this.language = language;

    }

    public String getSize2() {

        return size2;

    }

    public void setSize2(String size2) {

        this.size2 = size2;

    }

    public String getSizeNum() {

        return sizeNum;

    }

    public void setSizeNum(String sizeNum) {

        this.sizeNum = sizeNum;

    }

    public String getSizeType() {

        return sizeType;

    }

    public void setSizeType(String sizeType) {

        this.sizeType = sizeType;

    }

    public String getOfficialWebsite() {

        return officialWebsite;

    }

    public void setOfficialWebsite(String officialWebsite) {

        this.officialWebsite = officialWebsite;

    }

    public String getShowWebsite() {

        return showWebsite;

    }

    public void setShowWebsite(String showWebsite) {

        this.showWebsite = showWebsite;

    }

    public String getDownloadSum() {

        return downloadSum;

    }

    public void setDownloadSum(String downloadSum) {

        this.downloadSum = downloadSum;

    }

    public String getIntroduction() {

        return introduction;

    }

    public void setIntroduction(String introduction) {

        this.introduction = introduction;

    }

    public String getContentImgs() {

        return contentImgs;

    }

    public void setContentImgs(String contentImgs) {

        this.contentImgs = contentImgs;

    }

    public String getRuntime() {

        return runtime;

    }

    public void setRuntime(String runtime) {

        this.runtime = runtime;

    }

    public String getDownloadUrls() {

        return downloadUrls;

    }

    public void setDownloadUrls(String downloadUrls) {

        this.downloadUrls = downloadUrls;

    }

    public String getDockerPort() {

        return dockerPort;

    }

    public void setDockerPort(String dockerPort) {

        this.dockerPort = dockerPort;

    }

    public String getDockerImageName() {

        return dockerImageName;

    }

    public void setDockerImageName(String dockerImageName) {

        this.dockerImageName = dockerImageName;

    }

    public String getDockerContainerName() {

        return dockerContainerName;

    }

    public void setDockerContainerName(String dockerContainerName) {

        this.dockerContainerName = dockerContainerName;

    }

    public String getDockerShellCreate() {

        return dockerShellCreate;

    }

    public void setDockerShellCreate(String dockerShellCreate) {

        this.dockerShellCreate = dockerShellCreate;

    }

    public String getDockerShellRun() {

        return dockerShellRun;

    }

    public void setDockerShellRun(String dockerShellRun) {

        this.dockerShellRun = dockerShellRun;

    }

    public String getDockerShellStop() {

        return dockerShellStop;

    }

    public void setDockerShellStop(String dockerShellStop) {

        this.dockerShellStop = dockerShellStop;

    }

    public String getDockerShellContainerRemove() {

        return dockerShellContainerRemove;

    }

    public void setDockerShellContainerRemove(String dockerShellContainerRemove) {

        this.dockerShellContainerRemove = dockerShellContainerRemove;

    }

    public String getDockerShellImageRemove() {

        return dockerShellImageRemove;

    }

    public void setDockerShellImageRemove(String dockerShellImageRemove) {

        this.dockerShellImageRemove = dockerShellImageRemove;

    }

    public String getDockerStatus() {

        return dockerStatus;

    }

    public void setDockerStatus(String dockerStatus) {

        this.dockerStatus = dockerStatus;

    }

    public String getProjectStatus() {

        return projectStatus;

    }

    public void setProjectStatus(String projectStatus) {

        this.projectStatus = projectStatus;

    }

    public String getHttpInnerAddress() {

        return httpInnerAddress;

    }

    public void setHttpInnerAddress(String httpInnerAddress) {

        this.httpInnerAddress = httpInnerAddress;

    }

    public String getHttpOutAddress() {

        return httpOutAddress;

    }

    public void setHttpOutAddress(String httpOutAddress) {

        this.httpOutAddress = httpOutAddress;

    }


    @Override
    public String toString() {
        return "ProjectVo{" +
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
