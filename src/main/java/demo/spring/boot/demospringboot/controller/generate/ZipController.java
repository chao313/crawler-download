package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.controller.resource.service.ResourceService;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.util.EncoderUtils;
import demo.spring.boot.demospringboot.util.SevenZipUtils;
import demo.spring.boot.demospringboot.vo.LanguageType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.sevenzipjbinding.ArchiveFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

/**
 * 准备步骤:（目前还未成熟，多做几个之后再总结）
 * 1.准备zip压缩包(rar转换成zip)
 * 2.zip解压全部转utf-8
 * 3.定位sql和数据库链接地址,创建库并导入
 * 4.生成本地docker,上传docker注册中心
 * 可以开发界面，上传文件，一键打包
 * 5.运行测试情况
 */
@Slf4j
@RestController
@RequestMapping(value = "/ZipController")
public class ZipController {

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "处理压缩包")
    @PostMapping("/deal")
    public Response deal(
            @ApiParam(value = "这里上传zip包")
            @RequestParam(name = "zipFile")
                    MultipartFile zipFile) {
        Response response = new Response<>();
        try {

            String fileName = zipFile.getOriginalFilename();
            boolean b = resourceService.addFile(zipFile.getBytes(), fileName);
            zipFile.getInputStream();
            String zipFilePath = resourceService.getTmpDir();
            String zipFileName = fileName;
            String targetFileDir = "_" + fileName;
            List<String> fileNames = new ArrayList<>();//存放所有文件的名称
            StringBuilder sqlBuilder = new StringBuilder();//存放sql数据
            SevenZipUtils.unzip(zipFilePath, zipFileName, zipFilePath + targetFileDir, ArchiveFormat.RAR,
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
                                sqlBuilder.append(new String(bytes, charset));
                            }
                            return encodedBytes;
                        }
                    });
            AtomicReference<LanguageType> languageTypeResult = new AtomicReference<>();
            Arrays.stream(LanguageType.values()).forEach(languageType -> {
                fileNames.forEach(fileNameTmp -> {
                    if (fileNameTmp.endsWith(languageType.getType())) {
                        languageTypeResult.set(languageType);
                    }
                });
            });
            log.info("检测到sql:{}", sqlBuilder.toString());
            return Response.Ok(languageTypeResult.get());
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

}
