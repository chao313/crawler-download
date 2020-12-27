package demo.spring.boot.demospringboot.service.zip.impl;

import demo.spring.boot.demospringboot.config.StartConfig;
import demo.spring.boot.demospringboot.service.zip.DevToPro;
import demo.spring.boot.demospringboot.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@Slf4j
@Component
public class DefaultDevToProImpl extends DevToPro {

    @Autowired
    private StartConfig startConfig;

    @Override
    protected void init(String workDirAbsolutePath, String criteriaid, String localFsPathProZip) {
        String tmp = workDirAbsolutePath + "/" + criteriaid + "/" + UUIDUtils.generateUUID();
        String targetDirAbsolutePathName = tmp + "/" + criteriaid;
        String nameProZip = criteriaid + "_pro.zip";
        ProUtils.threadLocalVar.remove();
        ProUtils.threadLocalVar.get().put(ProUtils.PATH_ROOT, targetDirAbsolutePathName);
        ProUtils.threadLocalVar.get().put(ProUtils.PATH_APP, targetDirAbsolutePathName + "/app");
        ProUtils.threadLocalVar.get().put(ProUtils.PATH_SQL, targetDirAbsolutePathName + "/sql/db.sql");
        ProUtils.threadLocalVar.get().put(ProUtils.PATH_INIT, targetDirAbsolutePathName + "/init/init.sql");
        ProUtils.threadLocalVar.get().put(ProUtils.NAME_PRO_ZIP, nameProZip);
        ProUtils.threadLocalVar.get().put(ProUtils.PATH_PRO_ZIP, localFsPathProZip + "/" + nameProZip);
        ProUtils.threadLocalVar.get().put(ProUtils.TO_DELETE_DIR, tmp);
        File file = new File(ProUtils.threadLocalVar.get().get(ProUtils.PATH_APP));
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    protected String copyDataAndSqlFromContainer(String containerName, String path_sql, String path_root) throws IOException {
        ShellUtil.executeLinuxShellStr(CmdDockerUtils.getCopyAppCmd(containerName, path_root).split(" "), new ShellUtil.LocalFun());
        String sql = ShellUtil.executeLinuxShellStr(CmdDockerUtils.getCopySqlCmd(containerName), new ShellUtil.LocalFun());
        FileUtils.writeByteArrayToFile(new File(path_sql), sql.getBytes());
        return ProUtils.threadLocalVar.get().get(ProUtils.PATH_ROOT);
    }

    @Override
    protected String replaceAccountAndPassAndAdb(String path_root, String criteriaid) throws IOException {
        File root = new File(path_root);
        if (!root.isDirectory()) {
            log.error("操作的不是文件夹:{}", root);
        }
        listFile(root, new Function<AtomicReference<String>, Boolean>() {
            @Override
            public Boolean apply(AtomicReference<String> s) {
                Map<String, String> devToPro = ProUtils.getDevToProReplace(criteriaid);
                //开始替换
                String before = s.get();
                String after = new String(before);
                for (Map.Entry<String, String> entry : devToPro.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    after = after.replace(key, value);
                }
                s.set(after);
                if (!after.equals(s)) {
                    //前后不一致
                    return true;
                } else {
                    return false;
                }

            }
        });
        return null;
    }

    @Override
    protected String createDBAccountAndPassShell(String db, String account, String passwd, String path_sql, String path_init) throws IOException {
        List<String> orderCmds = CmdSqlUtils.getOrderCmds(db, account, passwd);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#!/bin/bash").append("\n");
        orderCmds.forEach(orderCmd -> {
            String format = MessageFormat.format("mysql -h {0} -u root -p123456 -e", startConfig.getLocalHostDockerMaster());
            stringBuilder.append(format).append("\"").append(orderCmd).append("\"\n");
        });
        FileUtils.writeByteArrayToFile(new File(path_init), stringBuilder.toString().getBytes());
        return null;
    }


    @Override
    protected Boolean saveToZip(String path_root, String path_pro_zip) throws FileNotFoundException {
        ZipUtils.toZip(path_root, new FileOutputStream(path_pro_zip), true);
        return true;
    }

    @Override
    protected void end() {
        String to_delete_dir = ProUtils.threadLocalVar.get().get(ProUtils.TO_DELETE_DIR);
        demo.spring.boot.demospringboot.util.FileUtils.deleteDirectory(to_delete_dir);
    }

    /**
     * 遍历全部的文件
     */
    private void listFile(File root, Function<AtomicReference<String>, Boolean> function) throws IOException {
        if (root.isDirectory()) {
            File[] childFiles = root.listFiles();
            for (File childFile : childFiles) {
                listFile(childFile, function);
            }
        } else {
            String fileStr = FileUtils.readFileToString(root, "UTF-8");
            if (null != function) {
                AtomicReference<String> content = new AtomicReference<>();
                content.set(fileStr);
                Boolean apply = function.apply(content);
                if (apply == true) {
                    FileUtils.writeByteArrayToFile(root, content.get().getBytes());
                }
            }
        }

    }
}
