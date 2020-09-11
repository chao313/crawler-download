package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.config.Bootstrap;
import demo.spring.boot.demospringboot.controller.wind.KafkaMsgRequest;
import demo.spring.boot.demospringboot.controller.wind.ProducerUpload;
import demo.spring.boot.demospringboot.controller.wind.kafka.KafkaProduceSendSyncService;
import demo.spring.boot.demospringboot.controller.wind.kafka.ProduceFactory;
import demo.spring.boot.demospringboot.controller.wind.kafka.RecordMetadataResponse;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.resource.service.ResourceService;
import demo.spring.boot.demospringboot.service.asp.ASPService;
import demo.spring.boot.demospringboot.service.asp.PanService;
import demo.spring.boot.demospringboot.util.MapUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping(value = "/ASPController")
public class ASPController {

    @Autowired
    private ASPService aspService;

    @Autowired
    private PanService panService;

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


    @ApiOperation(value = "批量下载")
    @GetMapping("/downloadListUrlToKafka")
    public Response downloadListUrlToKafka(
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
            @ApiParam(value = "kafka", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "bootstrap.servers", required = true)
                    String bootstrap_servers,
            @ApiParam(defaultValue = "TP_ASP30001")
            @RequestParam(name = "topic", required = true)
                    String topic,
            @ApiParam(value = "指定 partition -> 不指定就是null")
            @RequestParam(name = "partition", defaultValue = "0")
                    Integer partition,
            @ApiParam(defaultValue = "ASP")
            @RequestParam(name = "policyID", required = true)
                    String policyID

    ) {
        Response response = new Response<>();
        try {
            //生成生产者
            KafkaProduceSendSyncService<String, String> kafkaProduceSendSyncService =
                    ProduceFactory.getProducerInstance(bootstrap_servers, MapUtil.$(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "1073741824"))
                            .getKafkaProduceSendSyncService();
            //下载 入库
            Collection<String> result = Collections.synchronizedSet(new LinkedHashSet<>());
            Collection<String> listUrl = aspService.getListUrl(max);
            //并发下载
            listUrl.parallelStream().forEach(url -> {
                try {
                    byte[] bytes = aspService.downloadDetailToBytes(url, bisName, headless, useDriver);
                    KafkaMsgRequest kafkaMsgRequest = ProducerUpload.generateKafkaRequestMsg(policyID, topic, url, bytes);
                    RecordMetadataResponse recordMetadataResponse
                            = kafkaProduceSendSyncService.sendSync(topic, partition, url, kafkaMsgRequest.encodeData());
                    log.info("上传:{}", recordMetadataResponse);
                } catch (IOException | ExecutionException | InterruptedException e) {
                    log.info("e:{}", e.toString(), e);
                }

            });
            response.setContent(result);
            response.setCode(Code.System.OK);
            log.info("下载上传完成");
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

    @ApiOperation(value = "批量保存网盘")
    @GetMapping("/batchSavePan")
    public Response batchSavePan(
            @ApiParam(defaultValue = "https://pan.baidu.com/s/1wgcf5oabd1wYLrfy0dZb5A")
            @RequestParam(value = "url")
                    String url,
            @ApiParam(defaultValue = "mer1")
            @RequestParam(value = "passwd")
                    String passwd
    ) {
        Response response = new Response<>();
        try {
            boolean result = panService.savePan(url, passwd);//获取list任务
            response.setContent(result);
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
