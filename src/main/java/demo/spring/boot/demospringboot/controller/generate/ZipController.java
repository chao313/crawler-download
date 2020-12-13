package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.config.DockerStructure;
import demo.spring.boot.demospringboot.controller.resource.service.ResourceService;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.util.ShellUtil;
import demo.spring.boot.demospringboot.service.zip.UnzipFilter;
import demo.spring.boot.demospringboot.service.zip.UnzipToDocker;
import demo.spring.boot.demospringboot.service.zip.impl.DefaultUnzipToDocker;
import demo.spring.boot.demospringboot.util.EncoderUtils;
import demo.spring.boot.demospringboot.util.SevenZipUtils;
import demo.spring.boot.demospringboot.util.UUIDUtils;
import demo.spring.boot.demospringboot.util.ZipUtils;
import demo.spring.boot.demospringboot.vo.LanguageType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;

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


    private static final String sourceDir = "docker/model";


    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UnzipToDocker unzipToDocker;

    @Autowired
    private UnzipFilter unzipFilter;

    @ApiOperation(value = "处理压缩包")
    @PostMapping("/deal")
    public Response deal(
            @ApiParam(value = "这里上传zip包")
            @RequestParam(name = "zipFile")
                    MultipartFile zipFile) {
        Response response = new Response<>();
        try {
            //复制docker的model到目标文件夹下
            String uuid = UUID.randomUUID().toString().replace("-", "");//操作文件夹名称
//            String workOperateDir = resourceService.getTmpDir() + "_" + zipFile.getOriginalFilename() + "/" + uuid;
            String workOperateDir = resourceService.getTmpDir() + "_" + zipFile.getOriginalFilename().replace(" ", "") + "/" + uuid;
            org.apache.commons.io.FileUtils.copyDirectory(new File(DockerStructure.DOCKER_MODEL_Dir_Path), new File(workOperateDir));//项目复制到目标文件夹下


            String fileName = zipFile.getOriginalFilename();
            boolean b = resourceService.addFile(zipFile.getBytes(), fileName);
            zipFile.getInputStream();
            String zipFilePath = resourceService.getTmpDir();
            String zipFileName = fileName;
            String targetFileDir = zipFilePath + "__" + fileName;
            List<String> fileNames = new ArrayList<>();//存放所有文件的名称
            StringBuilder sqlBuilder = new StringBuilder();//存放sql数据
            SevenZipUtils.unzip(zipFilePath, zipFileName, targetFileDir,
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
                                sqlBuilder.append(new String(encodedBytes));
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
            AtomicReference<String> atomicReference = new AtomicReference<>();
            this.find(new File(targetFileDir), atomicReference);
            org.apache.commons.io.FileUtils.copyDirectory(new File(atomicReference.get()), new File(workOperateDir + "/code/"));//项目复制到目标文件夹下
            String shellPath = new File(workOperateDir).getAbsolutePath();
            String shell = " docker build --rm -t tmp " + shellPath;
//            shell = "docker build --rm -t tmp /Users/chao/IdeaWorkspace/crawler-download/locationResourcePath/e012c44049744210a7f6713e38952d57 ";
//            ShellUtil.run(shell);
//            new ShellSDK("127.0.0.1", "chao", "Ys20140913", 22).login().executeSup(shell);
//            ShellUtil.getResult(shell);
            ShellUtil.executeLinuxShell(shell, new Function<InputStream, Object>() {
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
            });

            ShellUtil.executeLinuxShell("docker stop tmp ", new Function<InputStream, Object>() {
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
            });

            ShellUtil.executeLinuxShell("docker rm tmp ", new Function<InputStream, Object>() {
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
            });

            ShellUtil.executeLinuxShell("docker run --rm  --name tmp -p 8901:80 tmp", new Function<InputStream, Object>() {
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
            });
            return Response.Ok(languageTypeResult.get());
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

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

    /**
     * 生成shell的路径
     */
    private String generatePath(String path) {
        StringBuilder shellPath = new StringBuilder();
        Arrays.stream(path.split("/")).forEach(tmp -> {
            if (StringUtils.isNotBlank(tmp)) {
                shellPath.append("/\"").append(tmp).append("\"");
            }
        });
        return shellPath.toString();
    }

//    @ApiOperation(value = "处理压缩包")
//    @PostMapping("/deal2")
//    public Response deal2(
//            @ApiParam(value = "这里上传zip包")
//            @RequestParam(name = "zipFile")
//                    MultipartFile zipFile,
//            @RequestParam(name = "port")
//                    Integer port) {
//        Response response = new Response<>();
//        try {
//            String tmpFileName = zipFile.getOriginalFilename();
//            boolean b = resourceService.addFile(zipFile.getBytes(), tmpFileName);
//            String fileInDirAbsolutePath = resourceService.getTmpDir();
//            String workDirAbsolutePath = resourceService.getTmpDir();
//            String fileName = tmpFileName;
//            String dockerModelDirPath = DockerStructure.DOCKER_MODEL_Dir_Path;
//            unzipToDocker.doWork(fileInDirAbsolutePath, workDirAbsolutePath, fileName, dockerModelDirPath, port,null,null);
//            return Response.Ok(true);
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.getMessage());
//            response.addException(e);
//            log.error("异常 ：{} ", e.getMessage(), e);
//        }
//        return response;
//
//    }

//    @ApiOperation(value = "批量处理压缩包")
//    @PostMapping("/deal3")
//    public Response deal3(
//            @RequestParam(name = "dir") String dir,
//            @RequestParam(name = "portMin") Integer portMin,
//            @RequestParam(name = "portMax") Integer portMax) {
//        Response response = new Response<>();
//        try {
//            File file = new File(dir);
//            if (!file.exists()) {
//                throw new RuntimeException("文件存在");
//            }
//            if (!file.isDirectory()) {
//                throw new RuntimeException("不是文件夹");
//            }
//            AtomicInteger port = new AtomicInteger();
//            port.set(portMin);
//            Arrays.stream(file.listFiles()).forEach(tmp -> {
//                String fileInDirAbsolutePath = dir;
//                String workDirAbsolutePath = resourceService.getTmpDir();
//                String fileName = tmp.getName();
//                String dockerModelDirPath = DockerStructure.DOCKER_MODEL_Dir_Path;
//                Integer tmpPort = port.get();
//                if (tmpPort > portMax) {
//                    throw new RuntimeException("达到最大的端口");
//                }
//                try {
//                    boolean b = unzipToDocker.doWork(fileInDirAbsolutePath, workDirAbsolutePath, fileName, dockerModelDirPath, tmpPort,null,null);
//                    if (b == true) {
//                        port.getAndIncrement();
//                        log.info("创建成功,当前端口号+1:{}", port.get());
//                    }
//                } catch (Exception e) {
//                    log.error("e:{}", e.toString(), e);
//                }
//            });
//
//            return Response.Ok(true);
//        } catch (Exception e) {
//            response.setCode(Code.System.FAIL);
//            response.setMsg(e.getMessage());
//            response.addException(e);
//            log.error("异常 ：{} ", e.getMessage(), e);
//        }
//        return response;
//
//    }

    @ApiOperation(value = "过滤压缩包")
    @PostMapping("/filterZip")
    public Response filterZip(@RequestParam(name = "dir") String dir,
                              @RequestParam(name = "targetDir") String targetDir,
                              @RequestParam(name = "checkLanguageType") LanguageType checkLanguageType) {
        Response response = new Response<>();
        try {
            File file = new File(dir);
            File targetFileDir = new File(targetDir);
            if (!file.exists()) {
                throw new RuntimeException("源文件不存在");
            }
            if (!file.isDirectory()) {
                throw new RuntimeException("源文件不是文件夹");
            }
            if (!targetFileDir.exists()) {
                throw new RuntimeException("目标文件不存在");
            }
            if (!targetFileDir.isDirectory()) {
                throw new RuntimeException("目标文件不是文件夹");
            }
            Arrays.stream(file.listFiles()).forEach(tmp -> {
                String fileInDirAbsolutePath = dir;
                String workDirAbsolutePath = targetDir;
                String fileName = tmp.getName();

                AtomicReference<LanguageType> languageType = new AtomicReference<>();
                try {
                    boolean b = unzipFilter.doWork(fileInDirAbsolutePath, fileName, checkLanguageType, languageType, workDirAbsolutePath);
                    if (b == true) {
                        log.info("过滤成功:{}", fileName);
                    }
                } catch (Exception e) {
                    log.error("e:{}", e.toString(), e);
                }
            });

            return Response.Ok(true);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }





}
