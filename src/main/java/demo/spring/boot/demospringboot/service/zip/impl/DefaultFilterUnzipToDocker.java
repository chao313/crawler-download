package demo.spring.boot.demospringboot.service.zip.impl;

import demo.spring.boot.demospringboot.service.zip.UnzipFilter;
import demo.spring.boot.demospringboot.util.EncoderUtils;
import demo.spring.boot.demospringboot.util.SevenZipUtils;
import demo.spring.boot.demospringboot.vo.LanguageType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

/**
 * 默认的解压函数
 */
@Slf4j
@Component
public class DefaultFilterUnzipToDocker extends UnzipFilter {


    @Override
    protected String getZipType(String fileInDirAbsolutePath,
                                String fileName,
                                LanguageType checkLanguageType,
                                AtomicReference<LanguageType> languageType) {
        List<String> fileNames = new ArrayList<>();//存放所有文件的名称
        String targetFileDir = fileInDirAbsolutePath + "/tmp/" + "_" + fileName;
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
                        LanguageType.check(fileName, checkLanguageType);
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
        return fileInDirAbsolutePath + "/" + fileName;
    }


}
