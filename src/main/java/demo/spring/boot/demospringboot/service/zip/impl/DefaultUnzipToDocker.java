package demo.spring.boot.demospringboot.service.zip.impl;

import demo.spring.boot.demospringboot.config.DockerStructure;
import demo.spring.boot.demospringboot.framework.exception.catcher.TypeInterruptException;
import demo.spring.boot.demospringboot.service.ShellUtil;
import demo.spring.boot.demospringboot.service.zip.UnzipToDocker;
import demo.spring.boot.demospringboot.util.EncoderUtils;
import demo.spring.boot.demospringboot.util.SevenZipUtils;
import demo.spring.boot.demospringboot.util.UUIDUtils;
import demo.spring.boot.demospringboot.vo.LanguageType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 默认的解压函数
 */
@Slf4j
@Component
public class DefaultUnzipToDocker extends UnzipToDocker {


    @Override
    protected String unzipAndGetRoot(String fileInDirAbsolutePath,
                                     String fileName,
                                     StringBuilder sql,
                                     LanguageType checkLanguageType,
                                     AtomicReference<LanguageType> languageType) {
        List<String> fileNames = new ArrayList<>();//存放所有文件的名称
        String targetFileDir = fileInDirAbsolutePath + "_" + fileName;
        SevenZipUtils.unzip(fileInDirAbsolutePath, fileName, targetFileDir,
                new BiFunction<byte[], String, byte[]>() {
                    @SneakyThrows
                    @Override
                    public byte[] apply(byte[] bytes, String fileName) {
                        fileNames.add(fileName);
                        String charset = EncoderUtils.getCharset(bytes);
                        log.info("判断编码:{}", charset);
                        byte[] encodedBytes = null;
                        if (StringUtils.isNotBlank(charset)) {
                            encodedBytes = new String(bytes, charset).getBytes(StandardCharsets.UTF_8);
                        } else {
                            encodedBytes = bytes;
                        }
                        if (fileName.endsWith("sql")) {
                            sql.append(new String(encodedBytes));
                        }
                        if (null != checkLanguageType) {
                            for (LanguageType vo : LanguageType.values()) {
                                if (fileName.endsWith(vo.getType())) {
                                    if (!vo.equals(checkLanguageType)) {
                                        throw new TypeInterruptException("不是期望类型,期望类型是:" + checkLanguageType.getType() + "，实际类型是:" + vo.getType());
                                    }
                                }
                            }
                        }
                        return encodedBytes;
                    }
                });
        Arrays.stream(LanguageType.values()).forEach(vo -> {
            fileNames.forEach(fileNameTmp -> {
                if (fileNameTmp.endsWith(vo.getType())) {
                    languageType.set(vo);
                }
            });
        });
        log.info("检测到sql:{}", sql.toString());
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
        String dockerRealPath = workDirAbsolutePath + uuid;
        FileUtils.copyDirectory(new File(dockerModelPath), new File(dockerRealPath));//项目复制到目标文件夹下
        FileUtils.copyDirectory(new File(dataPath), new File(dockerRealPath + DockerStructure.CODE));//项目复制到目标data文件夹下
        return dockerRealPath;
    }

    @Override
    protected void makeUpSqlPath(String dockerRealPath, StringBuilder sql) throws IOException {
        File SQLFile = new File(dockerRealPath + DockerStructure.INSTALL_SQL);
        FileUtils.write(SQLFile, sql.toString(), StandardCharsets.UTF_8);
        return;
    }

    @Override
    protected void makeUpDecPath(String dockerRealPath) {
        return;
    }


    @Override
    protected String buildDockerImage(String dockerRealPath, String fileName) {
        String imageName = fileName
                .replace(" ", "")
                .replaceAll("[\\u4e00-\\u9fa5]", "")
                .replaceAll("【.*?】", "")
                .replaceAll("(.*?)\\..*", "$1")
                .toLowerCase();
        String shell = " docker build --rm -t " + imageName + " " + dockerRealPath;
        ShellUtil.executeLinuxShell(shell, new LocalFun());
        return imageName;
    }

    @Override
    protected Boolean pushDockerImage(String imageName) {
        String shell = " docker push " + imageName;
        ShellUtil.executeLinuxShell(shell, new LocalFun());
        return true;
    }

    @Override
    protected Boolean buildRunDockerContainer(String imageName, Integer port) {
        String containerName = imageName + "_";
        String shell = " docker run -d  --name " + containerName + " -p " + port + ":80  " + imageName;
        ShellUtil.executeLinuxShell(shell, new LocalFun());
        return true;
    }

    /**
     * 找到真正的项目
     *
     * @param fileParent
     * @param atomicReference
     */
    private void find(File fileParent, AtomicReference<String> atomicReference) {
        if (fileParent.listFiles().length == 1) {
            //如果size为1
            if (fileParent.listFiles()[0].isDirectory()) {
                //如果是文件夹
                find(fileParent.listFiles()[0], atomicReference);
            }
        } else {
            atomicReference.set(fileParent.getAbsolutePath());
        }
    }

    class LocalFun implements Function<InputStream, Object> {
        @Override
        public Object apply(InputStream inputStream) {
            try {
                String response = IOUtils.toString(inputStream, "UTF-8");
                log.info("执行结果:{}", response);
            } catch (IOException e) {
                log.error("e:{}", e.toString(), e.toString());
            }
            return null;
        }
    }
}
