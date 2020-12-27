package demo.spring.boot.demospringboot.service.zip.impl;

import demo.spring.boot.demospringboot.config.DockerStructure;
import demo.spring.boot.demospringboot.service.zip.UnzipToDockerPro;
import demo.spring.boot.demospringboot.util.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * 默认的解压函数
 */
@Slf4j
@Component
public class DefaultUnzipToDockerPro extends UnzipToDockerPro {


    @Override
    protected String unzip(String fileInDirAbsolutePath, String fileName) {
        List<String> fileNames = new ArrayList<>();//存放所有文件的名称
        String targetFileDir = fileInDirAbsolutePath + "/tmp/" + "_" + fileName;
        SevenZipUtils.unzip(fileInDirAbsolutePath, fileName, targetFileDir,
                new BiFunction<byte[], String, byte[]>() {
                    @SneakyThrows
                    @Override
                    public byte[] apply(byte[] bytes, String fileName) {
                        fileNames.add(fileName);
                        String charset = EncoderUtils.getCharset(bytes);
//                        log.info("判断编码:{}", charset);
                        byte[] encodedBytes = null;
                        if (StringUtils.isNotBlank(charset)) {
                            encodedBytes = new String(bytes, charset).replace("gb2312", "UTF-8").getBytes(StandardCharsets.UTF_8);
                        } else {
                            encodedBytes = bytes;
                        }
                        return encodedBytes;
                    }
                });
        return targetFileDir;
    }

    @Override
    protected String getDataPath(String rootPath) {
        AtomicReference<String> atomicReference = new AtomicReference<>();
        this.find(new File(rootPath), atomicReference);
        return atomicReference.get();
    }

    @Override
    protected String copyCombineCodeAndDocker(String dataPath,
                                              String dockerModelPath,
                                              String workDirAbsolutePath) throws IOException {
        String uuid = UUIDUtils.generateUUID();
        String dockerRealPath = workDirAbsolutePath + "/" + uuid;
        FileUtils.copyDirectory(new File(dockerModelPath), new File(dockerRealPath));//项目复制到目标文件夹下
        FileUtils.copyDirectory(new File(dataPath), new File(dockerRealPath + DockerStructure.CODE));//项目复制到目标data文件夹下
        return dockerRealPath;
    }

    @Override
    protected void makeUpDecPath(String dockerRealPath, Map<String, byte[]> descMap) throws IOException {
        if (null != descMap) {
            String descFileDirPath = dockerRealPath + DockerStructure.DSC;
            for (Map.Entry<String, byte[]> entry : descMap.entrySet()) {
                String fileName = entry.getKey();
                byte[] bytes = entry.getValue();
                FileUtils.writeByteArrayToFile(new File(descFileDirPath + "/" + fileName), bytes);
            }
        }
        return;
    }


    @Override
    protected void buildDockerImage(String dockerRealPath, String imageName) {
        String shell = CmdDockerUtils.build(imageName, dockerRealPath);
        ShellUtil.executeLinuxShell(shell, new ShellUtil.LocalFun());
    }

    @Override
    public Boolean pushDockerImage(String imageName) {
        //tag
        ShellUtil.executeLinuxShellStr(CmdDockerUtils.getTagCmd(imageName), new ShellUtil.LocalFun());
        //push
        ShellUtil.executeLinuxShellStr(CmdDockerUtils.getPushCmd(imageName), new ShellUtil.LocalFun());
        //移除
        ShellUtil.executeLinuxShellStr(CmdDockerUtils.removeImage(imageName), new ShellUtil.LocalFun());
        ShellUtil.executeLinuxShellStr(CmdDockerUtils.getRemoveTagCmd(imageName), new ShellUtil.LocalFun());
        return true;
    }

    @Override
    protected String buildRunDockerContainer(String imageName, Integer port) {
        String containerName = imageName + "_";
//        String shell = CmdDockerUtils.create(containerName, port, 80, imageName);
//        ShellUtil.executeLinuxShell(shell, new ShellUtil.LocalFun());
        return containerName;
    }

    /**
     * 找到真正的项目
     *
     * @param fileParent
     * @param atomicReference
     */
    private void find(File fileParent, AtomicReference<String> atomicReference) {
        /**
         * 添加移除.url文件
         */
        List<File> collect = Arrays.stream(fileParent.listFiles()).filter(file -> {
            if (file.getName().endsWith(".url") || file.getName().equalsIgnoreCase(".DS_Store")) {
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toList());
        if (collect.size() == 1) {
            //如果size为1
            if (fileParent.listFiles()[0].isDirectory()) {
                //如果是文件夹
                find(fileParent.listFiles()[0], atomicReference);
            }
        } else {
            atomicReference.set(fileParent.getAbsolutePath());
        }
    }
}
