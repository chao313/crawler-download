package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.config.DockerStructure;
import demo.spring.boot.demospringboot.config.StartConfig;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.service.zip.UnzipToDocker;
import demo.spring.boot.demospringboot.thread.ThreadPoolExecutorService;
import demo.spring.boot.demospringboot.util.URLUtils;
import demo.spring.boot.demospringboot.util.UUIDUtils;
import demo.spring.boot.demospringboot.util.ZipUtils;
import demomaster.service.ProjectPlusService;
import demomaster.vo.ProjectPlusVo;
import demomaster.vo.plugin.ProjectPlusNoPriVo;
import demomaster.vo.plugin.ProjectPlusPriVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
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

    @Autowired
    private ThreadPoolExecutorService threadPoolExecutorService;


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
                    source.setUpdateTime(FastDateFormat.getInstance("yyyyMMddHHmmss").format(new Date()));//设置更新时间
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
        System.out.println("构建Docker镜像批量");
        Response response = new Response<>();
        ProjectPlusVo query = new ProjectPlusVo();
        query.setProjectPackageType(URLUtils.Type.stream.getType());
        List<ProjectPlusVo> projectVos = projectService.queryBase(query);
        for (ProjectPlusVo vo : projectVos) {
            if (StringUtils.isBlank(vo.getDockerImageName())) {
                threadPoolExecutorService.addWork(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            buildToDocker(vo.getCriteriaid());
                        } catch (Exception e) {
                            log.error("构建异常:{}", e.toString(), e);
                        }

                    }
                });

            }
        }
        threadPoolExecutorService.waitComplete();
        return response;
    }


    @ApiOperation(value = "下载zip")
    @GetMapping("/downloadZip")
    public ResponseEntity<byte[]> downloadZip(
            @RequestParam(value = "criteriaid") String criteriaid) throws IOException {
        ProjectPlusVo query = new ProjectPlusVo();
        query.setCriteriaid(criteriaid);
        List<ProjectPlusVo> projectVos = projectService.queryBase(query);
        if (projectVos.size() > 1) {
            throw new RuntimeException("同一个criteriaid可用检索出两条:" + criteriaid);
        }
        String fileName = projectVos.get(0).getProjectRealFileName();
        String s = startConfig.getLocalFsPathOriginZip() + "/" + fileName;
        File file = new File(s);
        HttpHeaders headers = new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename=" + fileName);//下载的文件名称
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<>(IOUtils.toByteArray(FileUtils.openInputStream(file)), headers, statusCode);
        return response;

    }

    @ApiOperation(value = "下载introduce")
    @GetMapping("/downIntroduce")
    public ResponseEntity<byte[]> downIntroduce(
            @RequestParam(value = "criteriaid") String criteriaid) throws IOException {
        ProjectPlusVo query = new ProjectPlusVo();
        query.setCriteriaid(criteriaid);
        List<ProjectPlusVo> projectVos = projectService.queryBase(query);
        if (projectVos.size() > 1) {
            throw new RuntimeException("同一个criteriaid可用检索出两条:" + criteriaid);
        }
        ProjectPlusVo vo = projectVos.get(0);
        Map<String, byte[]> descMap = new HashMap<>();
        descMap.put("readme.txt", vo.getProjectIntroduction().getBytes());//加入介绍
        String projectImgs = projectVos.get(0).getProjectImgs();
        if (StringUtils.isNotBlank(projectImgs)) {
            String[] split = projectImgs.split(",");
            for (String imgName : split) {
                String s = startConfig.getLocalFsPathImg() + "/" + imgName;
                File file = new File(s);
                if (file.exists()) {
                    //如果存在
                    descMap.put(imgName, IOUtils.toByteArray(FileUtils.openInputStream(file)));
                }
            }
        }
        String tmpDir = UUIDUtils.generateUUID();
        String s = startConfig.getLocalFsPathTmp() + "/" + tmpDir;
        File fileTmpDir = new File(s);
        fileTmpDir.mkdirs();
        for (Map.Entry<String, byte[]> entry : descMap.entrySet()) {
            String fileName = entry.getKey();
            byte[] bytes = entry.getValue();
            FileUtils.writeByteArrayToFile(new File(s + "/" + fileName), bytes);
        }
        OutputStream outputStream = new ByteArrayOutputStream();
        ZipUtils.toZip(s, outputStream, true);
        HttpHeaders headers = new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename=" + criteriaid + "_Introduce.zip");//下载的文件名称
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<>(((ByteArrayOutputStream) outputStream).toByteArray(), headers, statusCode);
        demo.spring.boot.demospringboot.util.FileUtils.deleteDirectory(s);//删除文件
        return response;

    }

}
