package demomaster.vo;

import demo.spring.boot.demospringboot.framework.MultiTerm;

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
public class ProjectPlusMultiTermVo {

    private MultiTerm<Integer> id; 
    private MultiTerm<String> criteriaid;  // 唯一id
    private MultiTerm<String> projectName;  // 项目-名称
    private MultiTerm<String> projectUpdateTime;  // 项目-更新时间
    private MultiTerm<String> projectType;  // 项目-类型(VIP/COMMON)
    private MultiTerm<String> projectPrice;  // 项目-价格
    private MultiTerm<String> projectPackageType;  // 项目-下载包类型(流/text)
    private MultiTerm<String> projectPanAddress;  // 项目-网盘地址
    private MultiTerm<String> projectRealFileName;  // 项目-真实文件名称
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
    private MultiTerm<String> dockerImageName;  // docker-镜像-名称
    private MultiTerm<String> dockerImageShellRemove;  // docker-镜像-移除命令
    private MultiTerm<String> dockerStatus;  // docker-状态,创建.运行中,停止,容器移除,镜像移除
    private MultiTerm<String> dockerContainerId;  // docker-容器-完整id
    private MultiTerm<String> dockerContainerName;  // docker-容器-名称
    private MultiTerm<String> dockerContainerPort;  // docker-端口号
    private MultiTerm<String> dockerContainerShellCreate;  // docker-容器-创建命令
    private MultiTerm<String> dockerContainerShellRun;  // docker-容器-启动命令
    private MultiTerm<String> dockerContainerShellStop;  // docker-容器-停止命令
    private MultiTerm<String> dockerContainerShellRemove;  // docker-容器-移除命令
    private MultiTerm<String> addressContainerOuter;  // 地址-容器外网
    private MultiTerm<String> addressContainerInner;  // 地址-容器内网
    private MultiTerm<String> addressProjectFront;  // 地址-项目前端
    private MultiTerm<String> addressProjectBackground;  // 地址-项目后台
    private MultiTerm<String> addressProjectBackgroundAccountPasswd;  // 地址-项目后台-账号密码
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
    public MultiTerm<String> getProjectRealFileName() {

        return projectRealFileName;

    }

    public void setProjectRealFileName(MultiTerm<String> projectRealFileName) {

        this.projectRealFileName = projectRealFileName;

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
    public MultiTerm<String> getDockerImageName() {

        return dockerImageName;

    }

    public void setDockerImageName(MultiTerm<String> dockerImageName) {

        this.dockerImageName = dockerImageName;

    }
    public MultiTerm<String> getDockerImageShellRemove() {

        return dockerImageShellRemove;

    }

    public void setDockerImageShellRemove(MultiTerm<String> dockerImageShellRemove) {

        this.dockerImageShellRemove = dockerImageShellRemove;

    }
    public MultiTerm<String> getDockerStatus() {

        return dockerStatus;

    }

    public void setDockerStatus(MultiTerm<String> dockerStatus) {

        this.dockerStatus = dockerStatus;

    }
    public MultiTerm<String> getDockerContainerId() {

        return dockerContainerId;

    }

    public void setDockerContainerId(MultiTerm<String> dockerContainerId) {

        this.dockerContainerId = dockerContainerId;

    }
    public MultiTerm<String> getDockerContainerName() {

        return dockerContainerName;

    }

    public void setDockerContainerName(MultiTerm<String> dockerContainerName) {

        this.dockerContainerName = dockerContainerName;

    }
    public MultiTerm<String> getDockerContainerPort() {

        return dockerContainerPort;

    }

    public void setDockerContainerPort(MultiTerm<String> dockerContainerPort) {

        this.dockerContainerPort = dockerContainerPort;

    }
    public MultiTerm<String> getDockerContainerShellCreate() {

        return dockerContainerShellCreate;

    }

    public void setDockerContainerShellCreate(MultiTerm<String> dockerContainerShellCreate) {

        this.dockerContainerShellCreate = dockerContainerShellCreate;

    }
    public MultiTerm<String> getDockerContainerShellRun() {

        return dockerContainerShellRun;

    }

    public void setDockerContainerShellRun(MultiTerm<String> dockerContainerShellRun) {

        this.dockerContainerShellRun = dockerContainerShellRun;

    }
    public MultiTerm<String> getDockerContainerShellStop() {

        return dockerContainerShellStop;

    }

    public void setDockerContainerShellStop(MultiTerm<String> dockerContainerShellStop) {

        this.dockerContainerShellStop = dockerContainerShellStop;

    }
    public MultiTerm<String> getDockerContainerShellRemove() {

        return dockerContainerShellRemove;

    }

    public void setDockerContainerShellRemove(MultiTerm<String> dockerContainerShellRemove) {

        this.dockerContainerShellRemove = dockerContainerShellRemove;

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
