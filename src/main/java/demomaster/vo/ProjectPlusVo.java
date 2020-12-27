package demomaster.vo;




/**
 * 表名称      :project_plus
 * 表类型      :BASE TABLE
 * 表引擎      :InnoDB
 * 表版本      :10
 * 行格式      :Dynamic
 * 表创建      :2020-12-25
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
    private String projectSql;  // 项目-sql 
    private String projectPort;  // 项目-端口号 
    private String addressContainerOuter;  // 地址-容器外网 
    private String addressContainerInner;  // 地址-容器内网 
    private String addressProjectFront;  // 地址-项目前端 
    private String addressProjectBackground;  // 地址-项目后台 
    private String addressProjectBackgroundAccountPasswd;  // 地址-项目后台-账号密码 
    private String devProjectRealFileName;  // 项目-真实文件名称 
    private String devDockerImageName;  // docker-镜像-名称 
    private String devDockerImageShellRemove;  // docker-镜像-移除命令 
    private String devDockerContainerId;  // docker-容器-完整id 
    private String devDockerContainerName;  // docker-容器-名称 
    private String devDockerContainerShellCreate;  // docker-容器-创建命令 
    private String devDockerContainerShellRun;  // docker-容器-启动命令 
    private String devDockerContainerShellStop;  // docker-容器-停止命令 
    private String devDockerContainerShellRemove;  // docker-容器-移除命令 
    private String devDockerStatusImagesIsExist;  // docker-状态-镜像是否存在 
    private String devDockerStatusContainerIsExist;  // docker-状态-容器是否存在 
    private String devDockerStatusContainerIsRunning;  // docker-状态-容器是否运行 
    private String proProjectRealFileName;  // pro-项目-真实文件名称 
    private String proDockerImageName;  // pro-docker-镜像-名称 
    private String proDockerImageShellRemove;  // pro-docker-镜像-移除命令 
    private String proDockerContainerId;  // pro-docker-容器-完整id 
    private String proDockerContainerName;  // pro-docker-容器-名称 
    private String proDockerContainerShellCreate;  // pro-docker-容器-创建命令 
    private String proDockerContainerShellRun;  // pro-docker-容器-启动命令 
    private String proDockerContainerShellStop;  // pro-docker-容器-停止命令 
    private String proDockerContainerShellRemove;  // pro-docker-容器-移除命令 
    private String proDockerStatusImagesIsExist;  // pro-docker-状态-镜像是否存在 
    private String proDockerStatusContainerIsExist;  // pro-docker-状态-容器是否存在 
    private String proDockerStatusContainerIsRunning;  // pro-docker-状态-容器是否运行 
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

    public String getProjectSql() {

        return projectSql;

    }

    public void setProjectSql(String projectSql) {

        this.projectSql = projectSql;

    }

    public String getProjectPort() {

        return projectPort;

    }

    public void setProjectPort(String projectPort) {

        this.projectPort = projectPort;

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

    public String getDevProjectRealFileName() {

        return devProjectRealFileName;

    }

    public void setDevProjectRealFileName(String devProjectRealFileName) {

        this.devProjectRealFileName = devProjectRealFileName;

    }

    public String getDevDockerImageName() {

        return devDockerImageName;

    }

    public void setDevDockerImageName(String devDockerImageName) {

        this.devDockerImageName = devDockerImageName;

    }

    public String getDevDockerImageShellRemove() {

        return devDockerImageShellRemove;

    }

    public void setDevDockerImageShellRemove(String devDockerImageShellRemove) {

        this.devDockerImageShellRemove = devDockerImageShellRemove;

    }

    public String getDevDockerContainerId() {

        return devDockerContainerId;

    }

    public void setDevDockerContainerId(String devDockerContainerId) {

        this.devDockerContainerId = devDockerContainerId;

    }

    public String getDevDockerContainerName() {

        return devDockerContainerName;

    }

    public void setDevDockerContainerName(String devDockerContainerName) {

        this.devDockerContainerName = devDockerContainerName;

    }

    public String getDevDockerContainerShellCreate() {

        return devDockerContainerShellCreate;

    }

    public void setDevDockerContainerShellCreate(String devDockerContainerShellCreate) {

        this.devDockerContainerShellCreate = devDockerContainerShellCreate;

    }

    public String getDevDockerContainerShellRun() {

        return devDockerContainerShellRun;

    }

    public void setDevDockerContainerShellRun(String devDockerContainerShellRun) {

        this.devDockerContainerShellRun = devDockerContainerShellRun;

    }

    public String getDevDockerContainerShellStop() {

        return devDockerContainerShellStop;

    }

    public void setDevDockerContainerShellStop(String devDockerContainerShellStop) {

        this.devDockerContainerShellStop = devDockerContainerShellStop;

    }

    public String getDevDockerContainerShellRemove() {

        return devDockerContainerShellRemove;

    }

    public void setDevDockerContainerShellRemove(String devDockerContainerShellRemove) {

        this.devDockerContainerShellRemove = devDockerContainerShellRemove;

    }

    public String getDevDockerStatusImagesIsExist() {

        return devDockerStatusImagesIsExist;

    }

    public void setDevDockerStatusImagesIsExist(String devDockerStatusImagesIsExist) {

        this.devDockerStatusImagesIsExist = devDockerStatusImagesIsExist;

    }

    public String getDevDockerStatusContainerIsExist() {

        return devDockerStatusContainerIsExist;

    }

    public void setDevDockerStatusContainerIsExist(String devDockerStatusContainerIsExist) {

        this.devDockerStatusContainerIsExist = devDockerStatusContainerIsExist;

    }

    public String getDevDockerStatusContainerIsRunning() {

        return devDockerStatusContainerIsRunning;

    }

    public void setDevDockerStatusContainerIsRunning(String devDockerStatusContainerIsRunning) {

        this.devDockerStatusContainerIsRunning = devDockerStatusContainerIsRunning;

    }

    public String getProProjectRealFileName() {

        return proProjectRealFileName;

    }

    public void setProProjectRealFileName(String proProjectRealFileName) {

        this.proProjectRealFileName = proProjectRealFileName;

    }

    public String getProDockerImageName() {

        return proDockerImageName;

    }

    public void setProDockerImageName(String proDockerImageName) {

        this.proDockerImageName = proDockerImageName;

    }

    public String getProDockerImageShellRemove() {

        return proDockerImageShellRemove;

    }

    public void setProDockerImageShellRemove(String proDockerImageShellRemove) {

        this.proDockerImageShellRemove = proDockerImageShellRemove;

    }

    public String getProDockerContainerId() {

        return proDockerContainerId;

    }

    public void setProDockerContainerId(String proDockerContainerId) {

        this.proDockerContainerId = proDockerContainerId;

    }

    public String getProDockerContainerName() {

        return proDockerContainerName;

    }

    public void setProDockerContainerName(String proDockerContainerName) {

        this.proDockerContainerName = proDockerContainerName;

    }

    public String getProDockerContainerShellCreate() {

        return proDockerContainerShellCreate;

    }

    public void setProDockerContainerShellCreate(String proDockerContainerShellCreate) {

        this.proDockerContainerShellCreate = proDockerContainerShellCreate;

    }

    public String getProDockerContainerShellRun() {

        return proDockerContainerShellRun;

    }

    public void setProDockerContainerShellRun(String proDockerContainerShellRun) {

        this.proDockerContainerShellRun = proDockerContainerShellRun;

    }

    public String getProDockerContainerShellStop() {

        return proDockerContainerShellStop;

    }

    public void setProDockerContainerShellStop(String proDockerContainerShellStop) {

        this.proDockerContainerShellStop = proDockerContainerShellStop;

    }

    public String getProDockerContainerShellRemove() {

        return proDockerContainerShellRemove;

    }

    public void setProDockerContainerShellRemove(String proDockerContainerShellRemove) {

        this.proDockerContainerShellRemove = proDockerContainerShellRemove;

    }

    public String getProDockerStatusImagesIsExist() {

        return proDockerStatusImagesIsExist;

    }

    public void setProDockerStatusImagesIsExist(String proDockerStatusImagesIsExist) {

        this.proDockerStatusImagesIsExist = proDockerStatusImagesIsExist;

    }

    public String getProDockerStatusContainerIsExist() {

        return proDockerStatusContainerIsExist;

    }

    public void setProDockerStatusContainerIsExist(String proDockerStatusContainerIsExist) {

        this.proDockerStatusContainerIsExist = proDockerStatusContainerIsExist;

    }

    public String getProDockerStatusContainerIsRunning() {

        return proDockerStatusContainerIsRunning;

    }

    public void setProDockerStatusContainerIsRunning(String proDockerStatusContainerIsRunning) {

        this.proDockerStatusContainerIsRunning = proDockerStatusContainerIsRunning;

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
                ", projectSql '" + projectSql + '\'' +
                ", projectPort '" + projectPort + '\'' +
                ", addressContainerOuter '" + addressContainerOuter + '\'' +
                ", addressContainerInner '" + addressContainerInner + '\'' +
                ", addressProjectFront '" + addressProjectFront + '\'' +
                ", addressProjectBackground '" + addressProjectBackground + '\'' +
                ", addressProjectBackgroundAccountPasswd '" + addressProjectBackgroundAccountPasswd + '\'' +
                ", devProjectRealFileName '" + devProjectRealFileName + '\'' +
                ", devDockerImageName '" + devDockerImageName + '\'' +
                ", devDockerImageShellRemove '" + devDockerImageShellRemove + '\'' +
                ", devDockerContainerId '" + devDockerContainerId + '\'' +
                ", devDockerContainerName '" + devDockerContainerName + '\'' +
                ", devDockerContainerShellCreate '" + devDockerContainerShellCreate + '\'' +
                ", devDockerContainerShellRun '" + devDockerContainerShellRun + '\'' +
                ", devDockerContainerShellStop '" + devDockerContainerShellStop + '\'' +
                ", devDockerContainerShellRemove '" + devDockerContainerShellRemove + '\'' +
                ", devDockerStatusImagesIsExist '" + devDockerStatusImagesIsExist + '\'' +
                ", devDockerStatusContainerIsExist '" + devDockerStatusContainerIsExist + '\'' +
                ", devDockerStatusContainerIsRunning '" + devDockerStatusContainerIsRunning + '\'' +
                ", proProjectRealFileName '" + proProjectRealFileName + '\'' +
                ", proDockerImageName '" + proDockerImageName + '\'' +
                ", proDockerImageShellRemove '" + proDockerImageShellRemove + '\'' +
                ", proDockerContainerId '" + proDockerContainerId + '\'' +
                ", proDockerContainerName '" + proDockerContainerName + '\'' +
                ", proDockerContainerShellCreate '" + proDockerContainerShellCreate + '\'' +
                ", proDockerContainerShellRun '" + proDockerContainerShellRun + '\'' +
                ", proDockerContainerShellStop '" + proDockerContainerShellStop + '\'' +
                ", proDockerContainerShellRemove '" + proDockerContainerShellRemove + '\'' +
                ", proDockerStatusImagesIsExist '" + proDockerStatusImagesIsExist + '\'' +
                ", proDockerStatusContainerIsExist '" + proDockerStatusContainerIsExist + '\'' +
                ", proDockerStatusContainerIsRunning '" + proDockerStatusContainerIsRunning + '\'' +
                ", updateTime '" + updateTime + '\'' +
                '}';
    }

}
