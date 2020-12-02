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

    @Value(value = "${local.fsPath.img}")
    private String localFsPathImg;

    @Value(value = "${local.fsPath.tmp}")
    private String localFsPathTmp;

    @Value(value = "${local.fsPath.originZip}")
    private String localFsPathOriginZip;

    public String getLocalFsPathImg() {
        return localFsPathImg;
    }

    public String getLocalFsPathOriginZip() {
        return localFsPathOriginZip;
    }

    public String getLocalFsPathTmp() {
        return localFsPathTmp;
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