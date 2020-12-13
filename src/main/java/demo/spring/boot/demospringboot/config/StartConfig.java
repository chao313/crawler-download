package demo.spring.boot.demospringboot.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 启动配置
 * fsPath:
 * img: D:/fsPath/img
 * originZip: D:/fsPath/originZip
 */
@Component
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
}