package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.config.DockerStructure;
import demo.spring.boot.demospringboot.config.StartConfig;
import demo.spring.boot.demospringboot.controller.resource.service.ResourceService;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.service.asp.ASPService;
import demo.spring.boot.demospringboot.service.asp.PanService;
import demo.spring.boot.demospringboot.service.download.DownloadAndParse;
import demo.spring.boot.demospringboot.service.zip.UnzipToDocker;
import demo.spring.boot.demospringboot.thread.ThreadPoolExecutorService;
import demo.spring.boot.demospringboot.util.URLUtils;
import demomaster.service.ProjectPlusService;
import demomaster.service.ProjectService;
import demomaster.vo.ProjectVo;
import demomaster.vo.ProjectVoBase;
import demomaster.vo.plugin.ProjectNoPriVo;
import demomaster.vo.plugin.ProjectPriVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RestController
@RequestMapping(value = "/ASPToDockerController")
public class ASPToDockerController {

    @Autowired
    @Qualifier("DefaultDownloadAndParse")
    private DownloadAndParse downloadAndParse;

    @Autowired
    private UnzipToDocker unzipToDocker;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectPlusService projectPlusService;

    @Autowired
    private ASPService aspService;

    @Autowired
    private PanService panService;

    @Autowired
    private ThreadPoolExecutorService threadPoolExecutorService;

    @Autowired
    private StartConfig startConfig;


    @ApiOperation(value = "asp生成docker")
    @GetMapping("/aspToDocker")
    public Response aspToDocker(
            @ApiParam(defaultValue = "http://www.asp300.net/SoftView/27/SoftView_69985.html")
            @RequestParam(value = "url") String url,
            @RequestParam(value = "cookie") String cookie) {
        Response response = new Response<>();
        try {
            ProjectVoBase projectVoBase = downloadAndParse.doWork(url, "GB2312", cookie);
            ProjectVo projectVo = new ProjectVo();
            BeanUtils.copyProperties(projectVoBase, projectVo);
            projectService.insert(projectVo);
            response.setCode(Code.System.OK);
            log.info("下载完成");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }


    @ApiOperation(value = "batchDeal")
    @GetMapping("/batchDeal")
    public Response batchDeal(@ApiParam(defaultValue = "0") @RequestParam(value = "from") int from,
                              @ApiParam(defaultValue = "10") @RequestParam(value = "to") int to,
                              @RequestParam(value = "cookie") String cookie) {
        Response response = new Response<>();
        try {
            Collection<String> listUrl = aspService.getListUrl(ASPService.PHP_LIST_PRE, from, to);
            ProjectVo query = new ProjectVo();
            List<ProjectVo> projectVos = projectService.queryBase(query);
            for (String url : listUrl) {
                boolean flag = false;
                for (ProjectVo vo : projectVos) {
                    if (vo.getSourceUrl().equalsIgnoreCase(url)) {
                        //数据库已经存在 -> 跳入下一次循环
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    //如果跳过
                    try {
                        threadPoolExecutorService.addWork(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ProjectVoBase projectVoBase = null;
                                    projectVoBase = downloadAndParse.doWork(url, "GB2312", cookie);
                                    ProjectVo projectVo = new ProjectVo();
                                    BeanUtils.copyProperties(projectVoBase, projectVo);
                                    projectService.insert(projectVo);
                                } catch (Exception e) {
                                    log.error("下载异常:{}", e.toString(), e);
                                }

                            }
                        });
                    } catch (Exception e) {
                        log.error("e:{}", e.toString(), e);
                    }
                }
            }
            threadPoolExecutorService.waitComplete();
            log.info("提取完成,return");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;
    }

    @ApiOperation(value = "批量处理端口号")
    @GetMapping("/batchDealPort")
    public Response batchDealPort() {
        Response response = new Response<>();
        try {
            ProjectVo query = new ProjectVo();
            List<ProjectVo> projectVos = projectService.queryBase(query);
            //筛选出最大值
            Integer max = 10000;
            for (ProjectVo projectVo : projectVos) {
                if (StringUtils.isNotBlank(projectVo.getDockerPort())) {
                    if ((max.compareTo(Integer.valueOf(projectVo.getDockerPort())) < 0)) {
                        max = Integer.valueOf(projectVo.getDockerPort());
                    }
                }
            }
            for (ProjectVo vo : projectVos) {
                if (StringUtils.isBlank(vo.getDockerPort())) {
                    max++;
                    ProjectPriVo target = new ProjectPriVo();
                    target.setId(vo.getId());
                    ProjectNoPriVo source = new ProjectNoPriVo();
                    source.setDockerPort(max.toString());
                    projectService.updateByPrimaryKey(source, target);//更新
                }
            }


            log.info("提取完成,return");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;
    }


    @ApiOperation(value = "batchDealIndb")
    @GetMapping("/batchDealIndb")
    public Response batchDealIndb(@RequestParam(value = "cookie") String cookie) {
        Response response = new Response<>();
        try {

            ProjectVo query = new ProjectVo();
            query.setProjectZipStatus("text");
            List<ProjectVo> projectVos = projectService.queryBase(query);
            projectVos.forEach(vo -> {
                String url = vo.getSourceUrl();
                try {
                    ProjectVoBase projectVo = downloadAndParse.doWork(url, "GB2312", cookie);
                } catch (Exception e) {
                    log.error("e:{}", e.toString(), e);
                }
            });
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }


    @ApiOperation(value = "批量保存网盘并且保存真实的文件名称")
    @PostMapping("/batchSavePanAndSaveRealName")
    public Response batchSavePanAndSaveRealName() {
        Response response = new Response<>();
        try {
            String regex = "百度网盘链接：(.*?),提取码：(.*)";
            ProjectVo query = new ProjectVo();
            query.setProjectZipStatus("text");
            List<ProjectVo> projectVos = projectService.queryBase(query);
            projectVos.forEach(vo -> {
                String projectPanAddress = vo.getProjectPanAddress();
                if (StringUtils.isNotBlank(projectPanAddress)) {
                    try {
                        if (projectPanAddress.matches(regex)) {
                            String url = projectPanAddress.replaceAll(regex, "$1");
                            String passwd = projectPanAddress.replaceAll(regex, "$2");
                            AtomicReference<String> fileRealName = new AtomicReference<>();
                            boolean result = panService.savePan(url, passwd, fileRealName);//保存
                            if (StringUtils.isNotBlank(fileRealName.get())) {
                                ProjectNoPriVo source = new ProjectNoPriVo();
                                source.setFileRealName(fileRealName.get());
                                ProjectPriVo target = new ProjectPriVo();
                                target.setId(vo.getId());
                                projectService.updateByPrimaryKey(source, target);//更新
                            }

                            log.info("{}保存：{}", projectPanAddress, result);
                        } else {
                            boolean result = panService.savePan(projectPanAddress);//保存
                            log.info("{}保存：{}", projectPanAddress, result);
                        }
                    } catch (Exception e) {
                        log.info("{}保存失败：{}", projectPanAddress, e);
                    }
                }
            });

            response.setCode(Code.System.OK);
            log.info("获取完成");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @ApiOperation(value = "批量保存网盘")
    @PostMapping("/batchSavePan")
    public Response batchSavePan(
            @ApiParam(value = "这里上传特定格式的数据" +
                    "<br>1.百度网盘链接：https://pan.baidu.com/s/1a1qbM4kgl8YeDBH-9Pmmtw,提取码：c6um" +
                    "<br>2.https://pan.baidu.com/s/1xnBt2zpqKbPFvVoDaw09dA")
            @RequestParam(name = "listFile")
                    MultipartFile listFile) {
        Response response = new Response<>();
        try {
            String regex = "百度网盘链接：(.*?),提取码：(.*)";
            List<String> list = IOUtils.readLines(listFile.getInputStream(), "UTF-8");
            for (String line : list) {
                try {
                    if (line.matches(regex)) {
                        String url = line.replaceAll(regex, "$1");
                        String passwd = line.replaceAll(regex, "$2");
                        boolean result = panService.savePan(url, passwd, null);//保存
                        log.info("{}保存：{}", line, result);
                    } else {
                        boolean result = panService.savePan(line);//保存
                        log.info("{}保存：{}", line, result);
                    }
                } catch (Exception e) {
                    log.info("{}保存失败：{}", line, e);
                }

            }
//            response.setContent(result);
            response.setCode(Code.System.OK);
            log.info("获取完成");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }
//    @ApiOperation(value = "构建Docker镜像")
//    @GetMapping("/buildToDocker")
//    public Response buildToDocker(
//            @RequestParam(value = "criteriaid") String criteriaid) {
//        Response response = new Response<>();
//        try {
//            ProjectVo query = new ProjectVo();
//            query.setCriteriaid(criteriaid);
//            List<ProjectVo> projectVos = projectService.queryBase(query);
//            String localFsPathOriginZip = startConfig.getLocalFsPathOriginZip();
//            String workDirAbsolutePath = startConfig.getLocalFsPathTmp();
//            for (ProjectVo vo : projectVos) {
//                String fileRealName = vo.getFileRealName();
//                String absolutePathFilePath = localFsPathOriginZip + "/" + fileRealName;
//                String contentImgs = vo.getContentImgs();
//                Map<String, byte[]> descMap = new HashMap<>();
//                descMap.put("readme.txt", vo.getIntroduction().getBytes());//加入介绍
//                if (StringUtils.isNotBlank(contentImgs)) {
//                    String[] split = contentImgs.substring(1, contentImgs.length() - 1).split(",");
//                    for (String imgName : split) {
//                        String s = startConfig.getLocalFsPathImg() + "/" + imgName;
//                        File file = new File(s);
//                        if (file.exists()) {
//                            //如果存在
//                            descMap.put(imgName, IOUtils.toByteArray(FileUtils.openInputStream(file)));
//                        }
//                    }
//                }
//                try {
//                    String dockerModelDirPath = DockerStructure.DOCKER_MODEL_Dir_Path;
//                    ProjectVo tmp = new ProjectVo();
//                    unzipToDocker.doWork(
//                            workDirAbsolutePath,
//                            new File(absolutePathFilePath),
//                            criteriaid.toLowerCase(), dockerModelDirPath, Integer.valueOf(vo.getDockerPort()), descMap, tmp);
//                    ProjectPriVo target = new ProjectPriVo();
//                    target.setId(vo.getId());
//                    ProjectNoPriVo source = new ProjectNoPriVo();
//                    BeanUtils.copyProperties(tmp, source);
//                    projectService.updateByPrimaryKey(source, target);//更新
//
//                } catch (Exception e) {
//                    log.error("e:{}", e.toString(), e);
//                }
//            }
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.getMessage());
//            response.addException(e);
//            log.error("异常 ：{} ", e.getMessage(), e);
//        }
//        return response;
//
//    }
//
//    @ApiOperation(value = "构建Docker镜像批量")
//    @GetMapping("/buildToDockerBatch")
//    public Response buildToDockerBatch() throws Exception {
//        Response response = new Response<>();
//        ProjectVo query = new ProjectVo();
//        query.setProjectZipStatus(URLUtils.Type.stream.getType());
//        List<ProjectVo> projectVos = projectService.queryBase(query);
//        for (ProjectVo vo : projectVos) {
//            if (StringUtils.isBlank(vo.getDockerImageName())) {
//                buildToDocker(vo.getCriteriaid());
////                threadPoolExecutorService.addWork(new Runnable() {
////                    @Override
////                    public void run() {
////                        try {
////                            buildToDocker(vo.getCriteriaid());
////                        } catch (Exception e) {
////                            log.error("构建异常:{}", e.toString(), e);
////                        }
////
////                    }
////                });
//
//            }
//        }
////        threadPoolExecutorService.waitComplete();
//        return response;
//    }


}
