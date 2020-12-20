package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.util.DockerCmdUtils;
import demomaster.service.ProjectPlusService;
import demomaster.service.ProjectService;
import demomaster.vo.ProjectPlusVo;
import demomaster.vo.ProjectVo;
import demomaster.vo.plugin.ProjectPlusNoPriVo;
import demomaster.vo.plugin.ProjectPlusPriVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/ETLController")
public class ETLController {


    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectPlusService projectPlusService;


    /**
     * 清洗数据到plus
     *
     * @return
     */
    @ApiOperation(value = "ETLProjectToPlus")
    @GetMapping("/ETLProjectToPlus")
    public Response ETLProjectToPlus() {
        Response response = new Response<>();
        try {
            ProjectVo query = new ProjectVo();
            List<ProjectVo> projectVos = projectService.queryBase(query);
            projectVos.forEach(vo -> {
                ProjectPlusVo projectPlusVo = new ProjectPlusVo();
                projectPlusVo.setId(vo.getId());
                projectPlusVo.setCriteriaid(vo.getCriteriaid());
                projectPlusVo.setProjectName(vo.getProjectName());
                projectPlusVo.setProjectUpdateTime(vo.getProjectUpdateTime());
                projectPlusVo.setProjectType(vo.getProjectType());
                projectPlusVo.setProjectPrice("");
                projectPlusVo.setProjectPackageType(vo.getProjectZipStatus());
                projectPlusVo.setProjectPanAddress(vo.getProjectPanAddress());
                projectPlusVo.setProjectRealFileName(vo.getFileRealName());
                projectPlusVo.setProjectHtmlBody(vo.getHtmlBody());
                projectPlusVo.setProjectSourceUrl(vo.getSourceUrl());
                projectPlusVo.setProjectLanguage(vo.getLanguage());
                projectPlusVo.setProjectSize2(vo.getSize2());
                projectPlusVo.setProjectSizeNum(vo.getSizeNum());
                projectPlusVo.setProjectSizeType(vo.getSizeType());
                String contentImgs = vo.getContentImgs();
                if (StringUtils.isNotBlank(contentImgs)) {
                    String substring = contentImgs.substring(1, contentImgs.length() - 1).replace(" ", "");
                    projectPlusVo.setProjectImgs(substring);
                    projectPlusVo.setProjectImgsDefault(substring.split(",")[0]);
                }
                projectPlusVo.setProjectRuntime(vo.getRuntime());
                projectPlusVo.setProjectOfficialWebsite(vo.getOfficialWebsite());
                projectPlusVo.setProjectShowWebsite(vo.getShowWebsite());
//                projectPlusVo.setProjectDownloadUrls(vo.getDownloadUrls());
                projectPlusVo.setProjectDownloadSum(vo.getDownloadSum());
                projectPlusVo.setProjectIntroduction(vo.getIntroduction());
                projectPlusVo.setProjectStatus(vo.getProjectStatus());
                projectPlusVo.setProjectMark("");
                projectPlusVo.setProjectCanRunning("");
                projectPlusVo.setDockerImageName(vo.getDockerImageName());
                projectPlusVo.setDockerImageShellRemove(vo.getDockerShellImageRemove());
                projectPlusVo.setDockerStatus(vo.getDockerStatus());
                projectPlusVo.setDockerContainerId("");
                projectPlusVo.setDockerContainerName(vo.getDockerContainerName());
                projectPlusVo.setDockerContainerPort(vo.getDockerPort());
                projectPlusVo.setDockerContainerShellCreate(vo.getDockerShellCreate());
                projectPlusVo.setDockerContainerShellRun(vo.getDockerShellRun());
                projectPlusVo.setDockerContainerShellStop(vo.getDockerShellStop());
                projectPlusVo.setDockerContainerShellRemove(vo.getDockerShellContainerRemove());
                projectPlusVo.setAddressContainerOuter(vo.getHttpOutAddress());
                projectPlusVo.setAddressContainerInner(vo.getHttpInnerAddress());
                projectPlusVo.setAddressProjectFront("");
                projectPlusVo.setAddressProjectBackground("");
                projectPlusVo.setAddressProjectBackgroundAccountPasswd("");
                projectPlusVo.setUpdateTime(FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()));
                projectPlusService.insert(projectPlusVo);

            });
            response.setCode(Code.System.OK);
            response.setContent(true);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    /**
     * 清洗plus -> create命令加上仓库
     * +移除镜像时加上仓库
     *
     * @return
     */
    @ApiOperation(value = "ETLProjectPlusCreateContainer")
    @GetMapping("/ETLProjectPlusCreateContainer")
    public Response ETLProjectPlusCreateContainer() {
        Response response = new Response<>();
        try {
            ProjectPlusVo query = new ProjectPlusVo();
            List<ProjectPlusVo> vos = projectPlusService.queryBase(query);
            vos.forEach(vo -> {
                if (StringUtils.isNotBlank(vo.getDockerContainerShellCreate())) {
                    ProjectPlusNoPriVo source = new ProjectPlusNoPriVo();
                    ProjectPlusPriVo target = new ProjectPlusPriVo();
                    target.setId(vo.getId());
                    source.setUpdateTime(FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()));
                    source.setDockerContainerShellCreate(DockerCmdUtils.create(vo.getDockerContainerName(), Integer.valueOf(vo.getDockerContainerPort()), 80, vo.getDockerImageName()));
                    source.setDockerImageShellRemove(DockerCmdUtils.removeImage(vo.getDockerContainerName()));
                    projectPlusService.updateByPrimaryKey(source, target);
                }
            });
            response.setCode(Code.System.OK);
            response.setContent(true);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

}
