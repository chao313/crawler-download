package demomaster.vo;




/**
 * 表名称      :project_plus
 * 表类型      :BASE TABLE
 * 表引擎      :InnoDB
 * 表版本      :10
 * 行格式      :Dynamic
 * 表创建      :2020-12-14
 * 字符集      :utf8_bin
 * 表注释      :
 */
public class ProjectPlusVo {

    private Integer id; 
    private String criteriaid;  // 唯一id 
    private String projectName;  // 项目-名称 
    private String projectUpdateTime;  // 项目-更新时间 
    private String projectType;  // 项目-类型(VIP/COMMON) 
    private String projectPrice;  // 项目-价格 
    private String projectPackageType;  // 项目-下载包类型(流/text) 
    private String projectPanAddress;  // 项目-网盘地址 
    private String projectRealFileName;  // 项目-真实文件名称 
    private String projectHtmlBody;  // 项目-网页Body 
    private String projectSourceUrl;  // 项目-源ASPURL 
    private String projectLanguage;  // 项目-开发语言 
    private String projectSize2;  // 项目-大小 
    private String projectSizeNum;  // 项目-大小数字 
    private String projectSizeType;  // 项目-大小type(k,M,G) 
    private String projectImgs;  // 项目-介绍(图片) 
    private String projectImgsDefault;  // 项目-默认的图片 
    private String projectRuntime;  // 项目-运行环境 
    private String projectOfficialWebsite;  // 项目-官网地址 
    private String projectShowWebsite;  // 项目-展示网址 
    private String projectDownloadUrls;  // 项目-下载URL 
    private String projectDownloadSum;  // 项目-下载次数 
    private String projectIntroduction;  // 项目-介绍(文字) 
    private String projectStatus;  // 项目-状态,可用/废弃/暂定 
    private String projectMark;  // 项目-备注 
    private String projectCanRunning;  // 项目-是否正常运行 
    private String dockerImageName;  // docker-镜像-名称 
    private String dockerImageShellRemove;  // docker-镜像-移除命令 
    private String dockerStatus;  // docker-状态,创建.运行中,停止,容器移除,镜像移除 
    private String dockerContainerId;  // docker-容器-完整id 
    private String dockerContainerName;  // docker-容器-名称 
    private String dockerContainerPort;  // docker-端口号 
    private String dockerContainerShellCreate;  // docker-容器-创建命令 
    private String dockerContainerShellRun;  // docker-容器-启动命令 
    private String dockerContainerShellStop;  // docker-容器-停止命令 
    private String dockerContainerShellRemove;  // docker-容器-移除命令 
    private String addressContainerOuter;  // 地址-容器外网 
    private String addressContainerInner;  // 地址-容器内网 
    private String addressProjectFront;  // 地址-项目前端 
    private String addressProjectBackground;  // 地址-项目后台 
    private String addressProjectBackgroundAccountPasswd;  // 地址-项目后台-账号密码 
    private String updateTime;  // 更新时间 


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

    public String getProjectPrice() {

        return projectPrice;

    }

    public void setProjectPrice(String projectPrice) {

        this.projectPrice = projectPrice;

    }

    public String getProjectPackageType() {

        return projectPackageType;

    }

    public void setProjectPackageType(String projectPackageType) {

        this.projectPackageType = projectPackageType;

    }

    public String getProjectPanAddress() {

        return projectPanAddress;

    }

    public void setProjectPanAddress(String projectPanAddress) {

        this.projectPanAddress = projectPanAddress;

    }

    public String getProjectRealFileName() {

        return projectRealFileName;

    }

    public void setProjectRealFileName(String projectRealFileName) {

        this.projectRealFileName = projectRealFileName;

    }

    public String getProjectHtmlBody() {

        return projectHtmlBody;

    }

    public void setProjectHtmlBody(String projectHtmlBody) {

        this.projectHtmlBody = projectHtmlBody;

    }

    public String getProjectSourceUrl() {

        return projectSourceUrl;

    }

    public void setProjectSourceUrl(String projectSourceUrl) {

        this.projectSourceUrl = projectSourceUrl;

    }

    public String getProjectLanguage() {

        return projectLanguage;

    }

    public void setProjectLanguage(String projectLanguage) {

        this.projectLanguage = projectLanguage;

    }

    public String getProjectSize2() {

        return projectSize2;

    }

    public void setProjectSize2(String projectSize2) {

        this.projectSize2 = projectSize2;

    }

    public String getProjectSizeNum() {

        return projectSizeNum;

    }

    public void setProjectSizeNum(String projectSizeNum) {

        this.projectSizeNum = projectSizeNum;

    }

    public String getProjectSizeType() {

        return projectSizeType;

    }

    public void setProjectSizeType(String projectSizeType) {

        this.projectSizeType = projectSizeType;

    }

    public String getProjectImgs() {

        return projectImgs;

    }

    public void setProjectImgs(String projectImgs) {

        this.projectImgs = projectImgs;

    }

    public String getProjectImgsDefault() {

        return projectImgsDefault;

    }

    public void setProjectImgsDefault(String projectImgsDefault) {

        this.projectImgsDefault = projectImgsDefault;

    }

    public String getProjectRuntime() {

        return projectRuntime;

    }

    public void setProjectRuntime(String projectRuntime) {

        this.projectRuntime = projectRuntime;

    }

    public String getProjectOfficialWebsite() {

        return projectOfficialWebsite;

    }

    public void setProjectOfficialWebsite(String projectOfficialWebsite) {

        this.projectOfficialWebsite = projectOfficialWebsite;

    }

    public String getProjectShowWebsite() {

        return projectShowWebsite;

    }

    public void setProjectShowWebsite(String projectShowWebsite) {

        this.projectShowWebsite = projectShowWebsite;

    }

    public String getProjectDownloadUrls() {

        return projectDownloadUrls;

    }

    public void setProjectDownloadUrls(String projectDownloadUrls) {

        this.projectDownloadUrls = projectDownloadUrls;

    }

    public String getProjectDownloadSum() {

        return projectDownloadSum;

    }

    public void setProjectDownloadSum(String projectDownloadSum) {

        this.projectDownloadSum = projectDownloadSum;

    }

    public String getProjectIntroduction() {

        return projectIntroduction;

    }

    public void setProjectIntroduction(String projectIntroduction) {

        this.projectIntroduction = projectIntroduction;

    }

    public String getProjectStatus() {

        return projectStatus;

    }

    public void setProjectStatus(String projectStatus) {

        this.projectStatus = projectStatus;

    }

    public String getProjectMark() {

        return projectMark;

    }

    public void setProjectMark(String projectMark) {

        this.projectMark = projectMark;

    }

    public String getProjectCanRunning() {

        return projectCanRunning;

    }

    public void setProjectCanRunning(String projectCanRunning) {

        this.projectCanRunning = projectCanRunning;

    }

    public String getDockerImageName() {

        return dockerImageName;

    }

    public void setDockerImageName(String dockerImageName) {

        this.dockerImageName = dockerImageName;

    }

    public String getDockerImageShellRemove() {

        return dockerImageShellRemove;

    }

    public void setDockerImageShellRemove(String dockerImageShellRemove) {

        this.dockerImageShellRemove = dockerImageShellRemove;

    }

    public String getDockerStatus() {

        return dockerStatus;

    }

    public void setDockerStatus(String dockerStatus) {

        this.dockerStatus = dockerStatus;

    }

    public String getDockerContainerId() {

        return dockerContainerId;

    }

    public void setDockerContainerId(String dockerContainerId) {

        this.dockerContainerId = dockerContainerId;

    }

    public String getDockerContainerName() {

        return dockerContainerName;

    }

    public void setDockerContainerName(String dockerContainerName) {

        this.dockerContainerName = dockerContainerName;

    }

    public String getDockerContainerPort() {

        return dockerContainerPort;

    }

    public void setDockerContainerPort(String dockerContainerPort) {

        this.dockerContainerPort = dockerContainerPort;

    }

    public String getDockerContainerShellCreate() {

        return dockerContainerShellCreate;

    }

    public void setDockerContainerShellCreate(String dockerContainerShellCreate) {

        this.dockerContainerShellCreate = dockerContainerShellCreate;

    }

    public String getDockerContainerShellRun() {

        return dockerContainerShellRun;

    }

    public void setDockerContainerShellRun(String dockerContainerShellRun) {

        this.dockerContainerShellRun = dockerContainerShellRun;

    }

    public String getDockerContainerShellStop() {

        return dockerContainerShellStop;

    }

    public void setDockerContainerShellStop(String dockerContainerShellStop) {

        this.dockerContainerShellStop = dockerContainerShellStop;

    }

    public String getDockerContainerShellRemove() {

        return dockerContainerShellRemove;

    }

    public void setDockerContainerShellRemove(String dockerContainerShellRemove) {

        this.dockerContainerShellRemove = dockerContainerShellRemove;

    }

    public String getAddressContainerOuter() {

        return addressContainerOuter;

    }

    public void setAddressContainerOuter(String addressContainerOuter) {

        this.addressContainerOuter = addressContainerOuter;

    }

    public String getAddressContainerInner() {

        return addressContainerInner;

    }

    public void setAddressContainerInner(String addressContainerInner) {

        this.addressContainerInner = addressContainerInner;

    }

    public String getAddressProjectFront() {

        return addressProjectFront;

    }

    public void setAddressProjectFront(String addressProjectFront) {

        this.addressProjectFront = addressProjectFront;

    }

    public String getAddressProjectBackground() {

        return addressProjectBackground;

    }

    public void setAddressProjectBackground(String addressProjectBackground) {

        this.addressProjectBackground = addressProjectBackground;

    }

    public String getAddressProjectBackgroundAccountPasswd() {

        return addressProjectBackgroundAccountPasswd;

    }

    public void setAddressProjectBackgroundAccountPasswd(String addressProjectBackgroundAccountPasswd) {

        this.addressProjectBackgroundAccountPasswd = addressProjectBackgroundAccountPasswd;

    }

    public String getUpdateTime() {

        return updateTime;

    }

    public void setUpdateTime(String updateTime) {

        this.updateTime = updateTime;

    }


    @Override
    public String toString() {
        return "ProjectPlusVo{" +
                ", id '" + id +
                ", criteriaid '" + criteriaid + '\'' +
                ", projectName '" + projectName + '\'' +
                ", projectUpdateTime '" + projectUpdateTime + '\'' +
                ", projectType '" + projectType + '\'' +
                ", projectPrice '" + projectPrice + '\'' +
                ", projectPackageType '" + projectPackageType + '\'' +
                ", projectPanAddress '" + projectPanAddress + '\'' +
                ", projectRealFileName '" + projectRealFileName + '\'' +
                ", projectHtmlBody '" + projectHtmlBody + '\'' +
                ", projectSourceUrl '" + projectSourceUrl + '\'' +
                ", projectLanguage '" + projectLanguage + '\'' +
                ", projectSize2 '" + projectSize2 + '\'' +
                ", projectSizeNum '" + projectSizeNum + '\'' +
                ", projectSizeType '" + projectSizeType + '\'' +
                ", projectImgs '" + projectImgs + '\'' +
                ", projectImgsDefault '" + projectImgsDefault + '\'' +
                ", projectRuntime '" + projectRuntime + '\'' +
                ", projectOfficialWebsite '" + projectOfficialWebsite + '\'' +
                ", projectShowWebsite '" + projectShowWebsite + '\'' +
                ", projectDownloadUrls '" + projectDownloadUrls + '\'' +
                ", projectDownloadSum '" + projectDownloadSum + '\'' +
                ", projectIntroduction '" + projectIntroduction + '\'' +
                ", projectStatus '" + projectStatus + '\'' +
                ", projectMark '" + projectMark + '\'' +
                ", projectCanRunning '" + projectCanRunning + '\'' +
                ", dockerImageName '" + dockerImageName + '\'' +
                ", dockerImageShellRemove '" + dockerImageShellRemove + '\'' +
                ", dockerStatus '" + dockerStatus + '\'' +
                ", dockerContainerId '" + dockerContainerId + '\'' +
                ", dockerContainerName '" + dockerContainerName + '\'' +
                ", dockerContainerPort '" + dockerContainerPort + '\'' +
                ", dockerContainerShellCreate '" + dockerContainerShellCreate + '\'' +
                ", dockerContainerShellRun '" + dockerContainerShellRun + '\'' +
                ", dockerContainerShellStop '" + dockerContainerShellStop + '\'' +
                ", dockerContainerShellRemove '" + dockerContainerShellRemove + '\'' +
                ", addressContainerOuter '" + addressContainerOuter + '\'' +
                ", addressContainerInner '" + addressContainerInner + '\'' +
                ", addressProjectFront '" + addressProjectFront + '\'' +
                ", addressProjectBackground '" + addressProjectBackground + '\'' +
                ", addressProjectBackgroundAccountPasswd '" + addressProjectBackgroundAccountPasswd + '\'' +
                ", updateTime '" + updateTime + '\'' +
                '}';
    }

}
