package demo.spring.boot.demospringboot.controller.resource.controller;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.controller.resource.service.ResourceService;
import demo.spring.boot.demospringboot.controller.resource.vo.FileDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/ResourceController")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    private static String preByFileName = "/ResourceController/preByFileName";
    private static String downloadByFileName = "/ResourceController/downloadByFileName";

    @Value("${server.servlet.context-path}")
    private String rootPath;//项目的


    @PostMapping("/addFile")
    public Response addFile(MultipartFile file) {
        Response<Boolean> response = new Response<>();
        try {
            boolean b = resourceService.addFile(file.getBytes(), file.getOriginalFilename());
            response.setCode(Code.System.OK);
            response.setContent(b);
            log.info("上传结果:{}", b);

        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @GetMapping("/existFileByName")
    public Response existFileByName(@RequestParam(value = "fileName") String fileName) {
        Response<Boolean> response = new Response<>();
        try {
            boolean b = resourceService.existFileByName(fileName);
            response.setCode(Code.System.OK);
            response.setContent(b);
            log.info("文件是否存在:{}", b);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @ApiOperation(value = "根据文件名称删除文件")
    @GetMapping("/deleteFileByName")
    public Response deleteFileByName(@RequestParam(value = "fileName") String fileName) {
        Response<Boolean> response = new Response<>();
        try {
            boolean b = resourceService.deleteFileByName(fileName);
            response.setCode(Code.System.OK);
            response.setContent(b);
            log.info("文件是否存在:{}", b);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @ApiOperation(value = "列出所有的文件")
    @PostMapping("/getFiles")
    public Response getFiles() {
        Response<List<File>> response = new Response<>();
        try {
            List<File> listFile = resourceService.getFiles();
            response.setCode(Code.System.OK);
            response.setContent(listFile);
            log.info("文件是列表:{}", listFile);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @ApiOperation(value = "列出所有的文件")
    @PostMapping("/getFileDetails")
    public Response getFileDetails(@RequestBody FileDetail query) {
        Response response = new Response<>();
        try {
            List<File> listFile = resourceService.getFiles();

            List<FileDetail> fileDetails = new ArrayList<>();
            listFile.forEach(file -> {
                FileDetail fileDetail = new FileDetail();
                fileDetail.setCreateTime(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified())));
                fileDetail.setFileName(file.getName());
                fileDetail.setType(file.getName().substring(file.getName().lastIndexOf(".")));//jpg,png,png
                fileDetail.setPreRelationViewUrl(rootPath + ResourceController.preByFileName + "?fileName=" + file.getName());//绝对url
                fileDetail.setDownloadUrl(rootPath + ResourceController.downloadByFileName + "?fileName=" + file.getName());//下载url
                fileDetails.add(fileDetail);

            });

            List<FileDetail> resultFileDetails = new ArrayList<>();
            fileDetails.forEach(fileDetail -> {
                /**
                 * todo 这里需要根据条件过滤
                 *
                 */
            });


            response.setCode(Code.System.OK);
            response.setContent(fileDetails);
            log.info("文件是列表:{}", listFile);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @ApiOperation(value = "根据文件名称获取文件细节")
    @GetMapping("/getFileDetailByFileName")
    public Response getFileDetailByFileName(@RequestParam(value = "fileName") String fileName) {
        Response response = new Response<>();
        try {
            File file = resourceService.getFilesByFileName(fileName);

            /**
             * 转换生成 FileDetail
             */
            FileDetail fileDetail = new FileDetail();
            fileDetail.setCreateTime(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified())));
            fileDetail.setFileName(file.getName());
            fileDetail.setType(file.getName().substring(file.getName().lastIndexOf(".")));//jpg,png,png
            fileDetail.setPreRelationViewUrl(rootPath + ResourceController.preByFileName + "?fileName=" + file.getName());//绝对url
            fileDetail.setDownloadUrl(rootPath + ResourceController.downloadByFileName + "?fileName=" + file.getName());//下载url

            response.setCode(Code.System.OK);
            response.setContent(fileDetail);
            log.info("文件是列表:{}", fileDetail);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @ApiOperation(value = "下载指定文件的文件")
    @GetMapping("/downloadByFileName")
    public ResponseEntity<byte[]> downloadByFileName(@RequestParam(value = "fileName") String fileName) throws Exception {

        boolean b = resourceService.existFileByName(fileName);
        if (false == b) {
            throw new Exception("指定文件不存在");
        }

        byte[] fileBytes = resourceService.getFileBytesByName(fileName);

        HttpHeaders headers = new HttpHeaders();//设置响应头
        headers.add("Content-Disposition", "attachment;filename=" + fileName);//下载的文件名称
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<>(fileBytes, headers, statusCode);
        return response;
    }

    @ApiOperation(value = "预览指定文件的文件")
    @GetMapping("/preByFileName")
    public ResponseEntity<byte[]> preByFileName(@RequestParam(value = "fileName") String fileName) throws Exception {

        boolean b = resourceService.existFileByName(fileName);
        if (false == b) {
            throw new Exception("指定文件不存在");
        }

        byte[] fileBytes = resourceService.getFileBytesByName(fileName);
        String fileContentTypeBytesByName = this.contentTypePlugin(fileName);//根据名称获取MIME
        HttpHeaders headers = new HttpHeaders();//设置响应头
        headers.add(HttpHeaders.CONTENT_TYPE, fileContentTypeBytesByName);//指定类型
        HttpStatus statusCode = HttpStatus.OK;//设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<>(fileBytes, headers, statusCode);
        return response;
    }

    /**
     * 根据名称获取MIME
     *
     * @param fileName
     * @return
     */
    private String contentTypePlugin(String fileName) throws IOException {
        String fileContentTypeBytesByName = "";
        if (fileName.endsWith("pdf")) {
            fileContentTypeBytesByName = "application/pdf";
        }
        if (fileName.endsWith("png")) {
            fileContentTypeBytesByName = "image/jpeg";
        }
        if (StringUtils.isBlank(fileContentTypeBytesByName)) {
            //如果为null ， 就调用系统的
            fileContentTypeBytesByName = resourceService.getFileContentTypeBytesByName(fileName);
        }
        return fileContentTypeBytesByName;
    }
}
