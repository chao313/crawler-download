package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.config.DockerStructure;
import demo.spring.boot.demospringboot.config.StartConfig;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.service.zip.UnzipToDocker;
import demo.spring.boot.demospringboot.util.URLUtils;
import demomaster.service.ProjectPlusService;
import demomaster.vo.ProjectPlusVo;
import demomaster.vo.plugin.ProjectPlusNoPriVo;
import demomaster.vo.plugin.ProjectPlusPriVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/ASPToDockerController")
public class ASPToDockerPlusController {

    @Autowired
    private UnzipToDocker unzipToDocker;

    @Autowired
    private ProjectPlusService projectService;

    @Autowired
    private StartConfig startConfig;


    @ApiOperation(value = "构建Docker镜像")
    @GetMapping("/buildToDocker")
    public Response buildToDocker(
            @RequestParam(value = "criteriaid") String criteriaid) {
        Response response = new Response<>();
        try {
            ProjectPlusVo query = new ProjectPlusVo();
            query.setCriteriaid(criteriaid);
            List<ProjectPlusVo> projectVos = projectService.queryBase(query);
            String localFsPathOriginZip = startConfig.getLocalFsPathOriginZip();
            String workDirAbsolutePath = startConfig.getLocalFsPathTmp();
            for (ProjectPlusVo vo : projectVos) {
                String projectRealFileName = vo.getProjectRealFileName();
                String absolutePathFilePath = localFsPathOriginZip + "/" + projectRealFileName;
                String contentImgs = vo.getProjectImgs();
                Map<String, byte[]> descMap = new HashMap<>();
                descMap.put("readme.txt", vo.getProjectIntroduction().getBytes());//加入介绍
                if (StringUtils.isNotBlank(contentImgs)) {
                    String[] split = contentImgs.substring(1, contentImgs.length() - 1).split(",");
                    for (String imgName : split) {
                        String s = startConfig.getLocalFsPathImg() + "/" + imgName;
                        File file = new File(s);
                        if (file.exists()) {
                            //如果存在
                            descMap.put(imgName, IOUtils.toByteArray(FileUtils.openInputStream(file)));
                        }
                    }
                }
                try {
                    String dockerModelDirPath = DockerStructure.DOCKER_MODEL_Dir_Path;
                    ProjectPlusVo tmp = new ProjectPlusVo();
                    unzipToDocker.doWork(
                            workDirAbsolutePath,
                            new File(absolutePathFilePath),
                            criteriaid.toLowerCase(), dockerModelDirPath, Integer.valueOf(vo.getDockerContainerPort()), descMap, tmp);
                    ProjectPlusPriVo target = new ProjectPlusPriVo();
                    target.setId(vo.getId());
                    ProjectPlusNoPriVo source = new ProjectPlusNoPriVo();
                    BeanUtils.copyProperties(tmp, source);
                    projectService.updateByPrimaryKey(source, target);//更新

                } catch (Exception e) {
                    log.error("e:{}", e.toString(), e);
                }
            }
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @ApiOperation(value = "构建Docker镜像批量")
    @GetMapping("/buildToDockerBatch")
    public Response buildToDockerBatch() throws Exception {
        Response response = new Response<>();
        ProjectPlusVo query = new ProjectPlusVo();
        query.setProjectPackageType(URLUtils.Type.stream.getType());
        List<ProjectPlusVo> projectVos = projectService.queryBase(query);
        for (ProjectPlusVo vo : projectVos) {
            if (StringUtils.isBlank(vo.getDockerImageName())) {
                buildToDocker(vo.getCriteriaid());
//                threadPoolExecutorService.addWork(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            buildToDocker(vo.getCriteriaid());
//                        } catch (Exception e) {
//                            log.error("构建异常:{}", e.toString(), e);
//                        }
//
//                    }
//                });

            }
        }
//        threadPoolExecutorService.waitComplete();
        return response;
    }


}
