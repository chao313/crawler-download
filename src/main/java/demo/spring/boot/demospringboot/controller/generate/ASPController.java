package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.resource.service.ResourceService;
import demo.spring.boot.demospringboot.service.asp.ASPService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.LinkedHashSet;

@Slf4j
@RestController
@RequestMapping(value = "/ASPController")
public class ASPController {

    @Autowired
    private ASPService aspService;

    @Autowired
    private ResourceService resourceService;


    @ApiOperation(value = "下载详情页")
    @GetMapping("/downloadDetail")
    public Response downloadDetail(

            @ApiParam(defaultValue = "http://www.asp300.net/SoftView/27/SoftView_69985.html")
            @RequestParam(value = "url")
                    String url,
            @ApiParam(defaultValue = "ASP")
            @RequestParam(value = "bisName")
                    String bisName,
            @ApiParam(defaultValue = "true")
            @RequestParam(value = "headless")
                    boolean headless,
            @ApiParam(defaultValue = "false")
            @RequestParam(value = "useDriver")
                    boolean useDriver,
            @ApiParam(hidden = true)
            @RequestHeader(value = "host") String host) {
        Response response = new Response<>();
        try {
            String fileName = aspService.downloadDetailToFile(url, bisName, headless, useDriver);
            String downloadUrl = "http://" + host + resourceService.getContextPath() + "ResourceController/downloadByFileName?fileName=" + fileName;
            response.setContent(downloadUrl);
            response.setCode(Code.System.OK);
            log.info("下载完成");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @ApiOperation(value = "批量下载")
    @GetMapping("/downloadListUrl")
    public Response downloadListUrl(
            @ApiParam(defaultValue = "ASP")
            @RequestParam(value = "bisName")
                    String bisName,
            @ApiParam(defaultValue = "10")
            @RequestParam(value = "max")
                    int max,
            @ApiParam(defaultValue = "true")
            @RequestParam(value = "headless")
                    boolean headless,
            @ApiParam(defaultValue = "false")
            @RequestParam(value = "useDriver")
                    boolean useDriver,
            @ApiParam(hidden = true)
            @RequestHeader(value = "host") String host

    ) {
        Response response = new Response<>();
        try {
            Collection<String> fileNames = aspService.downloadListUrl(bisName, max, headless, useDriver);
            Collection<String> result = new LinkedHashSet<>();
            fileNames.forEach(fileName -> {
                String downloadUrl = "http://" + host + resourceService.getContextPath() + "ResourceController/downloadByFileName?fileName=" + fileName;
                result.add(downloadUrl);
            });
            response.setContent(result);
            response.setCode(Code.System.OK);
            log.info("下载完成");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }


    @ApiOperation(value = "获取下载的list")
    @GetMapping("/getListUrl")
    public Response getListUrl(
            @ApiParam(defaultValue = "10")
            @RequestParam(value = "max")
                    int max) {
        Response response = new Response<>();
        try {
            Collection<String> listUrl = aspService.getListUrl(max);//获取list任务
            response.setContent(listUrl);
            response.setCode(Code.System.OK);
            log.info("获取完成");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

}
