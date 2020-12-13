package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.util.ShellUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 专门用于运行Docker命令
 */
@Slf4j
@RestController
@RequestMapping(value = "/DockerShellController")
public class DockerShellController {


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


}