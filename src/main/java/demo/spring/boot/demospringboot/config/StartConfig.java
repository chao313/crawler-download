package demo.spring.boot.demospringboot.config;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * 启动配置
 * fsPath:
 * img: D:/fsPath/img
 * originZip: D:/fsPath/originZip
 */
@Slf4j
@Component
@RestController
@RequestMapping(value = "/StartConfig")
public class StartConfig implements InitializingBean {

    public static final String INNER_HOST = "http://127.0.0.1";

    public static final String OUT_HOST = "http://39.107.236.187";

    @Value(value = "${local.fsPath.img}")
    private String localFsPathImg;

    @Value(value = "${local.fsPath.tmp}")
    private String localFsPathTmp;

    @Value(value = "${local.fsPath.originZip}")
    private String localFsPathOriginZip;

    @Value(value = "${local.host.inner}")
    private String localHostInner;

    @Value(value = "${local.host.outer}")
    private String localHostOuter;

    @Value(value = "${local.host.portainer.inner}")
    private String localHostPortainerInner;

    @Value(value = "${local.host.portainer.outer}")
    private String localHostPortainerOuter;

    /**
     * docker REST API host
     */
    @Value(value = "${local.host.docker.api}")
    private String localHostDockerApi;

    public String getLocalFsPathImg() {
        return localFsPathImg;
    }

    public String getLocalFsPathOriginZip() {
        return localFsPathOriginZip;
    }

    public String getLocalFsPathTmp() {
        return localFsPathTmp;
    }

    public String getLocalHostInner() {
        return localHostInner;
    }

    public String getLocalHostOuter() {
        return localHostOuter;
    }

    public String getLocalHostPortainerInner() {
        return localHostPortainerInner;
    }

    public String getLocalHostPortainerOuter() {
        return localHostPortainerOuter;
    }

    public String getLocalHostDockerApi() {
        return localHostDockerApi;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //保证文件夹存在
        File localFsPathTmpFile = new File(localFsPathTmp);
        if (!localFsPathTmpFile.exists()) {
            localFsPathTmpFile.mkdirs();
        }
        File localFsPathImgFile = new File(localFsPathImg);
        if (!localFsPathImgFile.exists()) {
            localFsPathImgFile.mkdirs();
        }
        File localFsPathOriginZipFile = new File(localFsPathOriginZip);
        if (!localFsPathOriginZipFile.exists()) {
            localFsPathOriginZipFile.mkdirs();
        }
    }

    @ApiOperation(value = "获取容器的URL")
    @GetMapping("/getOuterPortainerUrl")
    public Response getOuterPortainerUrl() {
        Response response = new Response<>();
        try {
            response.setCode(Code.System.OK);
            response.setContent(this.getLocalHostPortainerOuter());
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
    public Response getInnerPortainerUrl() {
        Response response = new Response<>();
        try {
            response.setCode(Code.System.OK);
            response.setContent(this.getLocalHostPortainerInner());
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