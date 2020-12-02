package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.controller.resource.service.ResourceService;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.service.asp.ASPService;
import demo.spring.boot.demospringboot.service.asp.PanService;
import demo.spring.boot.demospringboot.service.download.DownloadAndParse;
import demo.spring.boot.demospringboot.util.ShellUtil;
import demo.spring.boot.demospringboot.util.URLUtils;
import demomaster.service.ProjectService;
import demomaster.vo.ProjectVo;
import demomaster.vo.ProjectVoBase;
import demomaster.vo.plugin.ProjectNoPriVo;
import demomaster.vo.plugin.ProjectPriVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RestController
@RequestMapping(value = "/ASPToDockerController")
public class ASPToDockerController {

    @Autowired
    @Qualifier("DefaultDownloadAndParse")
    private DownloadAndParse downloadAndParse;


    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ASPService aspService;

    @Autowired
    private PanService panService;


    @ApiOperation(value = "asp生成docker")
    @GetMapping("/aspToDocker")
    public Response aspToDocker(
            @ApiParam(defaultValue = "http://www.asp300.net/SoftView/27/SoftView_69985.html")
            @RequestParam(value = "url") String url,
            @RequestParam(value = "cookie") String cookie,
            @RequestParam(value = "port") Integer port,
            @ApiParam(hidden = true)
            @RequestHeader(value = "host") String host) {
        Response response = new Response<>();
        try {
            ProjectVoBase projectVoBase = downloadAndParse.doWork(url, "GB2312", resourceService.getTmpDir(), cookie, port);
            ProjectVo projectVo = new ProjectVo();
            BeanUtils.copyProperties(projectVoBase,projectVo);
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

    @ApiOperation(value = "运行docker的命令")
    @GetMapping("/runDockerShell")
    public Response runDockerShell(
            @RequestParam(value = "shell")
                    String shell) {
        Response response = new Response<>();
        try {
            String result = ShellUtil.executeLinuxShellStr(shell, new ShellUtil.LocalFun());
            response.setCode(Code.System.OK);
            response.setContent(result);
            log.info("下载完成");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }


    @ApiOperation(value = "getLoginCookie")
    @GetMapping("/getLoginCookie")
    public Response getLoginCookie() {
        Response response = new Response<>();
        try {
            String cookieByLogin = URLUtils.getCookieByLogin("http://www.asp300.net/2012user/login.asp?action=chk", "hcwang-docker", "Ys20140913!");
            response.setCode(Code.System.OK);
            response.setContent(cookieByLogin);
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
                              @RequestParam(value = "cookie") String cookie,
                              @RequestParam(value = "port") Integer port) {
        Response response = new Response<>();
        try {
            Collection<String> listUrl = aspService.getListUrl(ASPService.PHP_LIST_PRE, from, to);
            for (String url : listUrl) {
                try {
                    ProjectVoBase projectVo = downloadAndParse.doWork(url, "GB2312", resourceService.getTmpDir(), cookie, port);
//                    projectService.insert(projectVo);
                    port++;
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
                    ProjectVoBase projectVo = downloadAndParse.doWork(url, "GB2312", resourceService.getTmpDir(), cookie, Integer.valueOf(vo.getDockerPort()));
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


}
