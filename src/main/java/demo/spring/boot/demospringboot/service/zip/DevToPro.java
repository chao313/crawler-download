package demo.spring.boot.demospringboot.service.zip;

import demo.spring.boot.demospringboot.util.ProUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 专门用于生成pro的zip
 */
@Slf4j
@Component
public abstract class DevToPro {

    protected abstract void init(String workDirAbsolutePath, String targetDirName, String localFsPathProZip);

    /**
     * 解压并且获取解压缩的根目录
     *
     * @param containerName 操作的容器的名称
     * @return 返回临文件夹的绝对路径
     */
    protected abstract String copyDataAndSqlFromContainer(String containerName, String path_sql, String path_root) throws IOException;

    /**
     * 替换账号密码
     */
    protected abstract String replaceAccountAndPassAndAdb(String path_root, String criteriaid) throws IOException;

    /**
     * 生成创建数据库和账号密码的脚本
     */
    protected abstract String createDBAccountAndPassShell(String db, String account, String passwd, String path_sql, String path_init) throws IOException;


    /**
     * 生成为zip(删除临时文件)
     */
    protected abstract Boolean saveToZip(String targetDirAbsolutePathName, String criteriaid) throws FileNotFoundException;

    /**
     * 后续操作者
     */
    protected abstract void end();

    /**
     * 返回pro_zip的名称
     */
    public String doWork(String criteriaid, String containerName, String workDirAbsolutePath, String localFsPathProZip) throws IOException {
        this.init(workDirAbsolutePath, criteriaid, localFsPathProZip);
        //从容器中把数据复制到指定目录
        this.copyDataAndSqlFromContainer(containerName, ProUtils.getVByK(ProUtils.PATH_SQL), ProUtils.getVByK(ProUtils.PATH_ROOT));
        //过滤全部的文件,把特殊的字符替换掉(mysql的ip,账号,密码)
        this.replaceAccountAndPassAndAdb(ProUtils.getVByK(ProUtils.PATH_ROOT), criteriaid);
        //创建库,账号,密码
        this.createDBAccountAndPassShell(ProUtils.getProDb(criteriaid), ProUtils.getProAccount(criteriaid), ProUtils.getProPasswd(criteriaid), ProUtils.getVByK(ProUtils.PATH_SQL), ProUtils.getVByK(ProUtils.PATH_INIT));
        //保存为zip存入 proZip目录
        this.saveToZip(ProUtils.getVByK(ProUtils.PATH_ROOT), ProUtils.getVByK(ProUtils.PATH_PRO_ZIP));
        this.end();
        return ProUtils.getVByK(ProUtils.NAME_PRO_ZIP);
    }
}
