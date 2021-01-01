package demo.spring.boot.demospringboot.controller.generate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import demo.spring.boot.demospringboot.config.StartConfig;
import demo.spring.boot.demospringboot.controller.resource.service.ResourceService;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.thread.ThreadPoolExecutorService;
import demo.spring.boot.demospringboot.util.CmdDockerUtils;
import demo.spring.boot.demospringboot.util.ShellUtil;
import demo.spring.boot.demospringboot.util.UUIDUtils;
import demo.spring.boot.demospringboot.util.ZipUtils;
import demomaster.service.ProjectPlusService;
import demomaster.vo.ProjectPlusVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    @Autowired
    private ProjectPlusService projectPlusService;

    @Autowired
    private ThreadPoolExecutorService threadPoolExecutorService;

    private static final String DOCKER_HUB_URL = "https://hub.docker.com/v2/repositories/chao313/?page_size=25&page=1&ordering=last_updated";


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

    @ApiOperation(value = "运行dockers的命令")
    @GetMapping("/runDockerShells")
    public Response runDockerShells(
            @RequestParam(value = "shell")
                    List<String> shells) {
        Response response = new Response<>();
        try {
            StringBuilder sb = new StringBuilder();
            shells.forEach(shell -> {
                String result = ShellUtil.executeLinuxShellStr(shell, new ShellUtil.LocalFun());
                sb.append(result).append("\n");
            });
            response.setCode(Code.System.OK);
            response.setContent(sb.toString());
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
            String fullId = ShellUtil.executeLinuxShellStr(CmdDockerUtils.getFullId(containerName), new ShellUtil.LocalFun());
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
            String fullId = ShellUtil.executeLinuxShellStr(CmdDockerUtils.getFullId(containerName), new ShellUtil.LocalFun());
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

    @ApiOperation(value = "推送Image")
    @GetMapping("/pushDocker")
    public Response pushDocker(
            @RequestParam(value = "criteriaid") String criteriaid) {
        Response response = new Response<>();
        try {
            ProjectPlusVo query = new ProjectPlusVo();
            query.setCriteriaid(criteriaid);
            List<ProjectPlusVo> projectVos = projectPlusService.queryBase(query);

            for (ProjectPlusVo vo : projectVos) {
                String dockerImageName = vo.getDevDockerImageName();
                if (StringUtils.isNotBlank(dockerImageName)) {
                    //镜像名称不为空 -> 推送
                    ShellUtil.executeLinuxShellStr(CmdDockerUtils.getTagCmd(dockerImageName), new ShellUtil.LocalFun());
                    ShellUtil.executeLinuxShellStr(CmdDockerUtils.getPushCmd(dockerImageName), new ShellUtil.LocalFun());
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

    @ApiOperation(value = "推送Image(批量)")
    @GetMapping("/pushDockerBatch")
    public Response pushDockerBatch() {
        Response response = new Response<>();
        try {
            Set<String> results = new HashSet<>(1024);
            this.getAllDockerHubImages(results, DOCKER_HUB_URL);
            ProjectPlusVo query = new ProjectPlusVo();
            List<ProjectPlusVo> projectVos = projectPlusService.queryBase(query);
            for (ProjectPlusVo vo : projectVos) {
                if (StringUtils.isNotBlank(vo.getDevDockerImageName())) {
                    if (!results.contains(vo.getDevDockerImageName())) {
                        //镜像名称不为空 -> 推送
                        threadPoolExecutorService.addWork(new Runnable() {
                            @Override
                            public void run() {
                                ShellUtil.executeLinuxShellStr(CmdDockerUtils.getTagCmd(vo.getDevDockerImageName()), new ShellUtil.LocalFun());
                                ShellUtil.executeLinuxShellStr(CmdDockerUtils.getPushCmd(vo.getDevDockerImageName()), new ShellUtil.LocalFun());
                            }
                        });
                    }
                }
            }
            threadPoolExecutorService.waitComplete();
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;
    }

    //递归方式来获取全部的Docker
    private void getAllDockerHubImages(Collection<String> results, String url) throws IOException {
        String tmp = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        JSONObject jsonObject = JSON.parseObject(tmp);

        if (jsonObject.containsKey("results")) {
            JSONArray resultTmp = jsonObject.getJSONArray("results");
            for (Object vo : resultTmp) {
                if (vo instanceof JSONObject) {
                    String name = ((JSONObject) vo).getString("name");
                    results.add(name);
                }
            }
        }
        if (jsonObject.containsKey("next")) {
            String next = jsonObject.getString("next");
            if (StringUtils.isNotBlank(next) && !next.equalsIgnoreCase("null"))
                this.getAllDockerHubImages(results, next);
        }
    }

}