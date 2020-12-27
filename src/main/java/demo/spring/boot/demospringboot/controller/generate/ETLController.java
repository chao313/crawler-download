package demo.spring.boot.demospringboot.controller.generate;

import demomaster.service.ProjectPlusService;
import demomaster.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/ETLController")
public class ETLController {


    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectPlusService projectPlusService;


//    /**
//     * 清洗数据到plus2
//     *
//     * @return
//     */
//    @ApiOperation(value = "ETLProjectToPlus2")
//    @GetMapping("/ETLProjectToPlus2")
//    public Response ETLProjectToPlus2() {
//        Response response = new Response<>();
//        try {
//            ProjectPlusVo query = new ProjectPlusVo();
//            List<ProjectPlusVo> vos = projectPlusService.queryBase(query);
//            vos.forEach(vo -> {
//                ProjectPlus2Vo projectPlus2Vo = new ProjectPlus2Vo();
//                projectPlus2Vo.setId(vo.getId());
//                projectPlus2Vo.setCriteriaid(vo.getCriteriaid());
//                projectPlus2Vo.setProjectName(vo.getProjectName());
//                projectPlus2Vo.setProjectUpdateTime(vo.getProjectUpdateTime());
//                projectPlus2Vo.setProjectImgs(vo.getProjectImgs());
//                projectPlus2Vo.setProjectImgsDefault(vo.getProjectImgsDefault());
//                projectPlus2Vo.setProjectType(vo.getProjectType());
//                projectPlus2Vo.setProjectPrice("");
//                projectPlus2Vo.setProjectPackageType(vo.getProjectPackageType());
//                projectPlus2Vo.setProjectPanAddress(vo.getProjectPanAddress());
//                projectPlus2Vo.setDevProjectRealFileName(vo.getProjectRealFileName());
//                projectPlus2Vo.setProjectHtmlBody(vo.getProjectHtmlBody());
//                projectPlus2Vo.setProjectSourceUrl(vo.getProjectSourceUrl());
//                projectPlus2Vo.setProjectLanguage(vo.getProjectLanguage());
//                projectPlus2Vo.setProjectSize2(vo.getProjectSize2());
//                projectPlus2Vo.setProjectSizeNum(vo.getProjectSizeNum());
//                projectPlus2Vo.setProjectSizeType(vo.getProjectSizeType());
//                projectPlus2Vo.setProjectRuntime(vo.getProjectRuntime());
//                projectPlus2Vo.setProjectOfficialWebsite(vo.getProjectOfficialWebsite());
//                projectPlus2Vo.setProjectShowWebsite(vo.getProjectShowWebsite());
//                projectPlus2Vo.setProjectDownloadUrls(vo.getProjectDownloadUrls());
//                projectPlus2Vo.setProjectDownloadSum(vo.getProjectDownloadSum());
//                projectPlus2Vo.setProjectIntroduction(vo.getProjectIntroduction());
//                projectPlus2Vo.setProjectStatus(vo.getProjectStatus());
//                projectPlus2Vo.setProjectMark(vo.getProjectMark());
//                projectPlus2Vo.setProjectCanRunning(vo.getProjectCanRunning());
//                projectPlus2Vo.setProjectPort(vo.getDockerContainerPort());
//
//                projectPlus2Vo.setDevDockerImageName(vo.getDockerImageName());
//                projectPlus2Vo.setDevDockerImageShellRemove(vo.getDockerImageShellRemove());
//                projectPlus2Vo.setDevDockerContainerId(null);
//                projectPlus2Vo.setDevDockerContainerName(vo.getDockerContainerName());
//                projectPlus2Vo.setDevDockerContainerShellCreate(vo.getDockerContainerShellCreate());
//                projectPlus2Vo.setDevDockerContainerShellRun(vo.getDockerContainerShellRun());
//                projectPlus2Vo.setDevDockerContainerShellStop(vo.getDockerContainerShellStop());
//                projectPlus2Vo.setDevDockerContainerShellRemove(vo.getDockerContainerShellRemove());
//                projectPlus2Vo.setAddressContainerOuter(vo.getAddressContainerOuter());
//                projectPlus2Vo.setAddressContainerInner(vo.getAddressContainerInner());
//                projectPlus2Vo.setAddressProjectFront(vo.getAddressProjectFront());
//                projectPlus2Vo.setAddressProjectBackground(vo.getAddressProjectBackground());
//                projectPlus2Vo.setAddressProjectBackgroundAccountPasswd(vo.getAddressProjectBackgroundAccountPasswd());
//                projectPlus2Vo.setUpdateTime(FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()));
//                projectPlus2Service.insert(projectPlus2Vo);
//
//            });
//            response.setCode(Code.System.OK);
//            response.setContent(true);
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.getMessage());
//            response.addException(e);
//            log.error("异常 ：{} ", e.getMessage(), e);
//        }
//        return response;
//
//    }

    /**
     * 清洗plus -> create命令加上仓库
     * +移除镜像时加上仓库
     *
     * @return
     */
//    @ApiOperation(value = "ETLProjectPlusCreateContainer")
//    @GetMapping("/ETLProjectPlusCreateContainer")
//    public Response ETLProjectPlusCreateContainer() {
//        Response response = new Response<>();
//        try {
//            ProjectPlusVo query = new ProjectPlusVo();
//            List<ProjectPlusVo> vos = projectPlusService.queryBase(query);
//            vos.forEach(vo -> {
//                if (StringUtils.isNotBlank(vo.getDevDockerContainerShellCreate())) {
//                    ProjectPlusNoPriVo source = new ProjectPlusNoPriVo();
//                    ProjectPlusPriVo target = new ProjectPlusPriVo();
//                    target.setId(vo.getId());
//                    source.setUpdateTime(FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()));
//                    source.setDevDockerContainerShellCreate(CmdDockerUtils.create(vo.get(), Integer.valueOf(vo.getDockerContainerPort()), 80, vo.getDockerImageName()));
//                    source.setDevDockerContainerShellRemove(CmdDockerUtils.removeImage(vo.getDockerContainerName()));
//                    source.setDevDockerContainerShellRun(CmdDockerUtils.removeImage(vo.getDockerContainerName()));
//                    source.setDevDockerContainerShellStop(CmdDockerUtils.removeImage(vo.getDockerContainerName()));
//                    projectPlusService.updateByPrimaryKey(source, target);
//                }
//            });
//            response.setCode(Code.System.OK);
//            response.setContent(true);
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.getMessage());
//            response.addException(e);
//            log.error("异常 ：{} ", e.getMessage(), e);
//        }
//        return response;
//
//    }


//    /**
//     * 清洗数据到plus
//     *
//     * @return
//     */
//    @ApiOperation(value = "ETLProjectToPlus")
//    @GetMapping("/ETLProjectToPlus")
//    public Response ETLProjectToPlus() {
//        Response response = new Response<>();
//        try {
//            ProjectVo query = new ProjectVo();
//            List<ProjectVo> projectVos = projectService.queryBase(query);
//            projectVos.forEach(vo -> {
//                ProjectPlusVo projectPlusVo = new ProjectPlusVo();
//                projectPlusVo.setId(vo.getId());
//                projectPlusVo.setCriteriaid(vo.getCriteriaid());
//                projectPlusVo.setProjectName(vo.getProjectName());
//                projectPlusVo.setProjectUpdateTime(vo.getProjectUpdateTime());
//                projectPlusVo.setProjectType(vo.getProjectType());
//                projectPlusVo.setProjectPrice("");
//                projectPlusVo.setProjectPackageType(vo.getProjectZipStatus());
//                projectPlusVo.setProjectPanAddress(vo.getProjectPanAddress());
//                projectPlusVo.setProjectRealFileName(vo.getFileRealName());
//                projectPlusVo.setProjectHtmlBody(vo.getHtmlBody());
//                projectPlusVo.setProjectSourceUrl(vo.getSourceUrl());
//                projectPlusVo.setProjectLanguage(vo.getLanguage());
//                projectPlusVo.setProjectSize2(vo.getSize2());
//                projectPlusVo.setProjectSizeNum(vo.getSizeNum());
//                projectPlusVo.setProjectSizeType(vo.getSizeType());
//                String contentImgs = vo.getContentImgs();
//                if (StringUtils.isNotBlank(contentImgs)) {
//                    String substring = contentImgs.substring(1, contentImgs.length() - 1).replace(" ", "");
//                    projectPlusVo.setProjectImgs(substring);
//                    projectPlusVo.setProjectImgsDefault(substring.split(",")[0]);
//                }
//                projectPlusVo.setProjectRuntime(vo.getRuntime());
//                projectPlusVo.setProjectOfficialWebsite(vo.getOfficialWebsite());
//                projectPlusVo.setProjectShowWebsite(vo.getShowWebsite());
////                projectPlusVo.setProjectDownloadUrls(vo.getDownloadUrls());
//                projectPlusVo.setProjectDownloadSum(vo.getDownloadSum());
//                projectPlusVo.setProjectIntroduction(vo.getIntroduction());
//                projectPlusVo.setProjectStatus(vo.getProjectStatus());
//                projectPlusVo.setProjectMark("");
//                projectPlusVo.setProjectCanRunning("");
//                projectPlusVo.setDockerImageName(vo.getDockerImageName());
//                projectPlusVo.setDockerImageShellRemove(vo.getDockerShellImageRemove());
//                projectPlusVo.setDockerStatus(vo.getDockerStatus());
//                projectPlusVo.setDockerContainerId("");
//                projectPlusVo.setDockerContainerName(vo.getDockerContainerName());
//                projectPlusVo.setDockerContainerPort(vo.getDockerPort());
//                projectPlusVo.setDockerContainerShellCreate(vo.getDockerShellCreate());
//                projectPlusVo.setDockerContainerShellRun(vo.getDockerShellRun());
//                projectPlusVo.setDockerContainerShellStop(vo.getDockerShellStop());
//                projectPlusVo.setDockerContainerShellRemove(vo.getDockerShellContainerRemove());
//                projectPlusVo.setAddressContainerOuter(vo.getHttpOutAddress());
//                projectPlusVo.setAddressContainerInner(vo.getHttpInnerAddress());
//                projectPlusVo.setAddressProjectFront("");
//                projectPlusVo.setAddressProjectBackground("");
//                projectPlusVo.setAddressProjectBackgroundAccountPasswd("");
//                projectPlusVo.setUpdateTime(FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()));
//                projectPlusService.insert(projectPlusVo);
//
//            });
//            response.setCode(Code.System.OK);
//            response.setContent(true);
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.getMessage());
//            response.addException(e);
//            log.error("异常 ：{} ", e.getMessage(), e);
//        }
//        return response;
//
//    }

}
