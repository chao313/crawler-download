package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.config.StartConfig;
import demo.spring.boot.demospringboot.controller.resource.service.ResourceService;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.util.DockerCmdUtils;
import demo.spring.boot.demospringboot.util.ShellUtil;
import demo.spring.boot.demospringboot.util.UUIDUtils;
import demo.spring.boot.demospringboot.util.ZipUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * 专门用于运行Docker命令
 */
@Slf4j
@RestController
@RequestMapping(value = "/DockerShellController")
public class DockerShellController {


    @Autowired
    private ResourceService resourceService;

    @Autowired
    private StartConfig startConfig;

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

    @ApiOperation(value = "获取容器的URL")
    @GetMapping("/getOuterPortainerUrl")
    public Response getOuterPortainerUrl(
            @RequestParam(value = "containerName")
                    String containerName) {
        Response response = new Response<>();
        try {
            String fullId = ShellUtil.executeLinuxShellStr(DockerCmdUtils.getFullId(containerName), new ShellUtil.LocalFun());
            response.setCode(Code.System.OK);
            response.setContent(startConfig.getLocalHostPortainerOuter() + "/" + fullId);
            log.info("下载完成");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @ApiOperation(value = "获取容器的URL")
    @GetMapping("/getInnerPortainerUrl")
    public Response getInnerPortainerUrl(
            @RequestParam(value = "containerName")
                    String containerName) {
        Response response = new Response<>();
        try {
            String fullId = ShellUtil.executeLinuxShellStr(DockerCmdUtils.getFullId(containerName), new ShellUtil.LocalFun());
            response.setCode(Code.System.OK);
            response.setContent(startConfig.getLocalHostPortainerInner() + "/" + fullId);
            log.info("下载完成");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    /**
     * docker cp 237_:/app ./xx
     *
     * @param containerName
     * @param path
     * @return
     */
    @ApiOperation(value = "从容器中提取code")
    @GetMapping("/getDataFromContainer")
    public Response getCodeFromContainer(
            @RequestParam(name = "containerName") String containerName,
            @ApiParam(hidden = true) @RequestHeader(value = "host") String host,
            HttpServletRequest httpServletRequest) {
        Response response = new Response<>();
        try {
            String tmpDir = resourceService.getTmpDir();//获取工作目录
            String UUIDDirPath = tmpDir + UUID.randomUUID();
            File UUIDDir = new File(UUIDDirPath);
            if (!UUIDDir.exists()) {
                UUIDDir.mkdirs();
            }
            String shell = "docker cp " + containerName + ":/app " + UUIDDir.getAbsolutePath();
            ShellUtil.executeLinuxShell(shell, new ShellUtil.LocalFun());
            String zipTmpWillRemovedName = UUIDUtils.generateUUID() + ".zip";
            File zipTmpWillRemoved = resourceService.addNewFile(zipTmpWillRemovedName);
            ZipUtils.toZip(UUIDDir.getAbsolutePath(), new FileOutputStream(zipTmpWillRemoved), true);
            String url = "http://" + host + resourceService.getContextPath() + "/ResourceController/downloadByFileName?fileName=" + zipTmpWillRemovedName;
            return Response.Ok(url);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }


}