package demomaster.vo;

import demo.spring.boot.demospringboot.framework.MultiTerm;

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
public class ProjectPlusMultiTermVo {

    private MultiTerm<Integer> id; 
    private MultiTerm<String> criteriaid;  // 唯一id
    private MultiTerm<String> projectName;  // 项目-名称
    private MultiTerm<String> projectUpdateTime;  // 项目-更新时间
    private MultiTerm<String> projectType;  // 项目-类型(VIP/COMMON)
    private MultiTerm<String> projectPrice;  // 项目-价格
    private MultiTerm<String> projectPackageType;  // 项目-下载包类型(流/text)
    private MultiTerm<String> projectPanAddress;  // 项目-网盘地址
    private MultiTerm<String> projectHtmlBody;  // 项目-网页Body
    private MultiTerm<String> projectSourceUrl;  // 项目-源ASPURL
    private MultiTerm<String> projectLanguage;  // 项目-开发语言
    private MultiTerm<String> projectSize2;  // 项目-大小
    private MultiTerm<String> projectSizeNum;  // 项目-大小数字
    private MultiTerm<String> projectSizeType;  // 项目-大小type(k,M,G)
    private MultiTerm<String> projectImgs;  // 项目-介绍(图片)
    private MultiTerm<String> projectImgsDefault;  // 项目-默认的图片
    private MultiTerm<String> projectRuntime;  // 项目-运行环境
    private MultiTerm<String> projectOfficialWebsite;  // 项目-官网地址
    private MultiTerm<String> projectShowWebsite;  // 项目-展示网址
    private MultiTerm<String> projectDownloadUrls;  // 项目-下载URL
    private MultiTerm<String> projectDownloadSum;  // 项目-下载次数
    private MultiTerm<String> projectIntroduction;  // 项目-介绍(文字)
    private MultiTerm<String> projectStatus;  // 项目-状态,可用/废弃/暂定
    private MultiTerm<String> projectMark;  // 项目-备注
    private MultiTerm<String> projectCanRunning;  // 项目-是否正常运行
    private MultiTerm<String> projectSql;  // 项目-sql
    private MultiTerm<String> projectPort;  // 项目-端口号
    private MultiTerm<String> addressContainerOuter;  // 地址-容器外网
    private MultiTerm<String> addressContainerInner;  // 地址-容器内网
    private MultiTerm<String> addressProjectFront;  // 地址-项目前端
    private MultiTerm<String> addressProjectBackground;  // 地址-项目后台
    private MultiTerm<String> addressProjectBackgroundAccountPasswd;  // 地址-项目后台-账号密码
    private MultiTerm<String> devProjectRealFileName;  // 项目-真实文件名称
    private MultiTerm<String> devDockerImageName;  // docker-镜像-名称
    private MultiTerm<String> devDockerImageShellRemove;  // docker-镜像-移除命令
    private MultiTerm<String> devDockerContainerId;  // docker-容器-完整id
    private MultiTerm<String> devDockerContainerName;  // docker-容器-名称
    private MultiTerm<String> devDockerContainerShellCreate;  // docker-容器-创建命令
    private MultiTerm<String> devDockerContainerShellRun;  // docker-容器-启动命令
    private MultiTerm<String> devDockerContainerShellStop;  // docker-容器-停止命令
    private MultiTerm<String> devDockerContainerShellRemove;  // docker-容器-移除命令
    private MultiTerm<String> devDockerStatusImagesIsExist;  // docker-状态-镜像是否存在
    private MultiTerm<String> devDockerStatusContainerIsExist;  // docker-状态-容器是否存在
    private MultiTerm<String> devDockerStatusContainerIsRunning;  // docker-状态-容器是否运行
    private MultiTerm<String> proProjectRealFileName;  // pro-项目-真实文件名称
    private MultiTerm<String> proDockerImageName;  // pro-docker-镜像-名称
    private MultiTerm<String> proDockerImageShellRemove;  // pro-docker-镜像-移除命令
    private MultiTerm<String> proDockerContainerId;  // pro-docker-容器-完整id
    private MultiTerm<String> proDockerContainerName;  // pro-docker-容器-名称
    private MultiTerm<String> proDockerContainerShellCreate;  // pro-docker-容器-创建命令
    private MultiTerm<String> proDockerContainerShellRun;  // pro-docker-容器-启动命令
    private MultiTerm<String> proDockerContainerShellStop;  // pro-docker-容器-停止命令
    private MultiTerm<String> proDockerContainerShellRemove;  // pro-docker-容器-移除命令
    private MultiTerm<String> proDockerStatusImagesIsExist;  // pro-docker-状态-镜像是否存在
    private MultiTerm<String> proDockerStatusContainerIsExist;  // pro-docker-状态-容器是否存在
    private MultiTerm<String> proDockerStatusContainerIsRunning;  // pro-docker-状态-容器是否运行
    private MultiTerm<String> updateTime;  // 更新时间


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
    public MultiTerm<String> getProjectPrice() {

        return projectPrice;

    }

    public void setProjectPrice(MultiTerm<String> projectPrice) {

        this.projectPrice = projectPrice;

    }
    public MultiTerm<String> getProjectPackageType() {

        return projectPackageType;

    }

    public void setProjectPackageType(MultiTerm<String> projectPackageType) {

        this.projectPackageType = projectPackageType;

    }
    public MultiTerm<String> getProjectPanAddress() {

        return projectPanAddress;

    }

    public void setProjectPanAddress(MultiTerm<String> projectPanAddress) {

        this.projectPanAddress = projectPanAddress;

    }
    public MultiTerm<String> getProjectHtmlBody() {

        return projectHtmlBody;

    }

    public void setProjectHtmlBody(MultiTerm<String> projectHtmlBody) {

        this.projectHtmlBody = projectHtmlBody;

    }
    public MultiTerm<String> getProjectSourceUrl() {

        return projectSourceUrl;

    }

    public void setProjectSourceUrl(MultiTerm<String> projectSourceUrl) {

        this.projectSourceUrl = projectSourceUrl;

    }
    public MultiTerm<String> getProjectLanguage() {

        return projectLanguage;

    }

    public void setProjectLanguage(MultiTerm<String> projectLanguage) {

        this.projectLanguage = projectLanguage;

    }
    public MultiTerm<String> getProjectSize2() {

        return projectSize2;

    }

    public void setProjectSize2(MultiTerm<String> projectSize2) {

        this.projectSize2 = projectSize2;

    }
    public MultiTerm<String> getProjectSizeNum() {

        return projectSizeNum;

    }

    public void setProjectSizeNum(MultiTerm<String> projectSizeNum) {

        this.projectSizeNum = projectSizeNum;

    }
    public MultiTerm<String> getProjectSizeType() {

        return projectSizeType;

    }

    public void setProjectSizeType(MultiTerm<String> projectSizeType) {

        this.projectSizeType = projectSizeType;

    }
    public MultiTerm<String> getProjectImgs() {

        return projectImgs;

    }

    public void setProjectImgs(MultiTerm<String> projectImgs) {

        this.projectImgs = projectImgs;

    }
    public MultiTerm<String> getProjectImgsDefault() {

        return projectImgsDefault;

    }

    public void setProjectImgsDefault(MultiTerm<String> projectImgsDefault) {

        this.projectImgsDefault = projectImgsDefault;

    }
    public MultiTerm<String> getProjectRuntime() {

        return projectRuntime;

    }

    public void setProjectRuntime(MultiTerm<String> projectRuntime) {

        this.projectRuntime = projectRuntime;

    }
    public MultiTerm<String> getProjectOfficialWebsite() {

        return projectOfficialWebsite;

    }

    public void setProjectOfficialWebsite(MultiTerm<String> projectOfficialWebsite) {

        this.projectOfficialWebsite = projectOfficialWebsite;

    }
    public MultiTerm<String> getProjectShowWebsite() {

        return projectShowWebsite;

    }

    public void setProjectShowWebsite(MultiTerm<String> projectShowWebsite) {

        this.projectShowWebsite = projectShowWebsite;

    }
    public MultiTerm<String> getProjectDownloadUrls() {

        return projectDownloadUrls;

    }

    public void setProjectDownloadUrls(MultiTerm<String> projectDownloadUrls) {

        this.projectDownloadUrls = projectDownloadUrls;

    }
    public MultiTerm<String> getProjectDownloadSum() {

        return projectDownloadSum;

    }

    public void setProjectDownloadSum(MultiTerm<String> projectDownloadSum) {

        this.projectDownloadSum = projectDownloadSum;

    }
    public MultiTerm<String> getProjectIntroduction() {

        return projectIntroduction;

    }

    public void setProjectIntroduction(MultiTerm<String> projectIntroduction) {

        this.projectIntroduction = projectIntroduction;

    }
    public MultiTerm<String> getProjectStatus() {

        return projectStatus;

    }

    public void setProjectStatus(MultiTerm<String> projectStatus) {

        this.projectStatus = projectStatus;

    }
    public MultiTerm<String> getProjectMark() {

        return projectMark;

    }

    public void setProjectMark(MultiTerm<String> projectMark) {

        this.projectMark = projectMark;

    }
    public MultiTerm<String> getProjectCanRunning() {

        return projectCanRunning;

    }

    public void setProjectCanRunning(MultiTerm<String> projectCanRunning) {

        this.projectCanRunning = projectCanRunning;

    }
    public MultiTerm<String> getProjectSql() {

        return projectSql;

    }

    public void setProjectSql(MultiTerm<String> projectSql) {

        this.projectSql = projectSql;

    }
    public MultiTerm<String> getProjectPort() {

        return projectPort;

    }

    public void setProjectPort(MultiTerm<String> projectPort) {

        this.projectPort = projectPort;

    }
    public MultiTerm<String> getAddressContainerOuter() {

        return addressContainerOuter;

    }

    public void setAddressContainerOuter(MultiTerm<String> addressContainerOuter) {

        this.addressContainerOuter = addressContainerOuter;

    }
    public MultiTerm<String> getAddressContainerInner() {

        return addressContainerInner;

    }

    public void setAddressContainerInner(MultiTerm<String> addressContainerInner) {

        this.addressContainerInner = addressContainerInner;

    }
    public MultiTerm<String> getAddressProjectFront() {

        return addressProjectFront;

    }

    public void setAddressProjectFront(MultiTerm<String> addressProjectFront) {

        this.addressProjectFront = addressProjectFront;

    }
    public MultiTerm<String> getAddressProjectBackground() {

        return addressProjectBackground;

    }

    public void setAddressProjectBackground(MultiTerm<String> addressProjectBackground) {

        this.addressProjectBackground = addressProjectBackground;

    }
    public MultiTerm<String> getAddressProjectBackgroundAccountPasswd() {

        return addressProjectBackgroundAccountPasswd;

    }

    public void setAddressProjectBackgroundAccountPasswd(MultiTerm<String> addressProjectBackgroundAccountPasswd) {

        this.addressProjectBackgroundAccountPasswd = addressProjectBackgroundAccountPasswd;

    }
    public MultiTerm<String> getDevProjectRealFileName() {

        return devProjectRealFileName;

    }

    public void setDevProjectRealFileName(MultiTerm<String> devProjectRealFileName) {

        this.devProjectRealFileName = devProjectRealFileName;

    }
    public MultiTerm<String> getDevDockerImageName() {

        return devDockerImageName;

    }

    public void setDevDockerImageName(MultiTerm<String> devDockerImageName) {

        this.devDockerImageName = devDockerImageName;

    }
    public MultiTerm<String> getDevDockerImageShellRemove() {

        return devDockerImageShellRemove;

    }

    public void setDevDockerImageShellRemove(MultiTerm<String> devDockerImageShellRemove) {

        this.devDockerImageShellRemove = devDockerImageShellRemove;

    }
    public MultiTerm<String> getDevDockerContainerId() {

        return devDockerContainerId;

    }

    public void setDevDockerContainerId(MultiTerm<String> devDockerContainerId) {

        this.devDockerContainerId = devDockerContainerId;

    }
    public MultiTerm<String> getDevDockerContainerName() {

        return devDockerContainerName;

    }

    public void setDevDockerContainerName(MultiTerm<String> devDockerContainerName) {

        this.devDockerContainerName = devDockerContainerName;

    }
    public MultiTerm<String> getDevDockerContainerShellCreate() {

        return devDockerContainerShellCreate;

    }

    public void setDevDockerContainerShellCreate(MultiTerm<String> devDockerContainerShellCreate) {

        this.devDockerContainerShellCreate = devDockerContainerShellCreate;

    }
    public MultiTerm<String> getDevDockerContainerShellRun() {

        return devDockerContainerShellRun;

    }

    public void setDevDockerContainerShellRun(MultiTerm<String> devDockerContainerShellRun) {

        this.devDockerContainerShellRun = devDockerContainerShellRun;

    }
    public MultiTerm<String> getDevDockerContainerShellStop() {

        return devDockerContainerShellStop;

    }

    public void setDevDockerContainerShellStop(MultiTerm<String> devDockerContainerShellStop) {

        this.devDockerContainerShellStop = devDockerContainerShellStop;

    }
    public MultiTerm<String> getDevDockerContainerShellRemove() {

        return devDockerContainerShellRemove;

    }

    public void setDevDockerContainerShellRemove(MultiTerm<String> devDockerContainerShellRemove) {

        this.devDockerContainerShellRemove = devDockerContainerShellRemove;

    }
    public MultiTerm<String> getDevDockerStatusImagesIsExist() {

        return devDockerStatusImagesIsExist;

    }

    public void setDevDockerStatusImagesIsExist(MultiTerm<String> devDockerStatusImagesIsExist) {

        this.devDockerStatusImagesIsExist = devDockerStatusImagesIsExist;

    }
    public MultiTerm<String> getDevDockerStatusContainerIsExist() {

        return devDockerStatusContainerIsExist;

    }

    public void setDevDockerStatusContainerIsExist(MultiTerm<String> devDockerStatusContainerIsExist) {

        this.devDockerStatusContainerIsExist = devDockerStatusContainerIsExist;

    }
    public MultiTerm<String> getDevDockerStatusContainerIsRunning() {

        return devDockerStatusContainerIsRunning;

    }

    public void setDevDockerStatusContainerIsRunning(MultiTerm<String> devDockerStatusContainerIsRunning) {

        this.devDockerStatusContainerIsRunning = devDockerStatusContainerIsRunning;

    }
    public MultiTerm<String> getProProjectRealFileName() {

        return proProjectRealFileName;

    }

    public void setProProjectRealFileName(MultiTerm<String> proProjectRealFileName) {

        this.proProjectRealFileName = proProjectRealFileName;

    }
    public MultiTerm<String> getProDockerImageName() {

        return proDockerImageName;

    }

    public void setProDockerImageName(MultiTerm<String> proDockerImageName) {

        this.proDockerImageName = proDockerImageName;

    }
    public MultiTerm<String> getProDockerImageShellRemove() {

        return proDockerImageShellRemove;

    }

    public void setProDockerImageShellRemove(MultiTerm<String> proDockerImageShellRemove) {

        this.proDockerImageShellRemove = proDockerImageShellRemove;

    }
    public MultiTerm<String> getProDockerContainerId() {

        return proDockerContainerId;

    }

    public void setProDockerContainerId(MultiTerm<String> proDockerContainerId) {

        this.proDockerContainerId = proDockerContainerId;

    }
    public MultiTerm<String> getProDockerContainerName() {

        return proDockerContainerName;

    }

    public void setProDockerContainerName(MultiTerm<String> proDockerContainerName) {

        this.proDockerContainerName = proDockerContainerName;

    }
    public MultiTerm<String> getProDockerContainerShellCreate() {

        return proDockerContainerShellCreate;

    }

    public void setProDockerContainerShellCreate(MultiTerm<String> proDockerContainerShellCreate) {

        this.proDockerContainerShellCreate = proDockerContainerShellCreate;

    }
    public MultiTerm<String> getProDockerContainerShellRun() {

        return proDockerContainerShellRun;

    }

    public void setProDockerContainerShellRun(MultiTerm<String> proDockerContainerShellRun) {

        this.proDockerContainerShellRun = proDockerContainerShellRun;

    }
    public MultiTerm<String> getProDockerContainerShellStop() {

        return proDockerContainerShellStop;

    }

    public void setProDockerContainerShellStop(MultiTerm<String> proDockerContainerShellStop) {

        this.proDockerContainerShellStop = proDockerContainerShellStop;

    }
    public MultiTerm<String> getProDockerContainerShellRemove() {

        return proDockerContainerShellRemove;

    }

    public void setProDockerContainerShellRemove(MultiTerm<String> proDockerContainerShellRemove) {

        this.proDockerContainerShellRemove = proDockerContainerShellRemove;

    }
    public MultiTerm<String> getProDockerStatusImagesIsExist() {

        return proDockerStatusImagesIsExist;

    }

    public void setProDockerStatusImagesIsExist(MultiTerm<String> proDockerStatusImagesIsExist) {

        this.proDockerStatusImagesIsExist = proDockerStatusImagesIsExist;

    }
    public MultiTerm<String> getProDockerStatusContainerIsExist() {

        return proDockerStatusContainerIsExist;

    }

    public void setProDockerStatusContainerIsExist(MultiTerm<String> proDockerStatusContainerIsExist) {

        this.proDockerStatusContainerIsExist = proDockerStatusContainerIsExist;

    }
    public MultiTerm<String> getProDockerStatusContainerIsRunning() {

        return proDockerStatusContainerIsRunning;

    }

    public void setProDockerStatusContainerIsRunning(MultiTerm<String> proDockerStatusContainerIsRunning) {

        this.proDockerStatusContainerIsRunning = proDockerStatusContainerIsRunning;

    }
    public MultiTerm<String> getUpdateTime() {

        return updateTime;

    }

    public void setUpdateTime(MultiTerm<String> updateTime) {

        this.updateTime = updateTime;

    }

    @Override
    public String toString() {
        return "ProjectPlusMultiTermVo{" +
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
