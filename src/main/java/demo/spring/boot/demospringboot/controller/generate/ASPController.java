package demo.spring.boot.demospringboot.controller.generate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
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
import demo.spring.boot.demospringboot.util.URLUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
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

    @ApiOperation(value = "保存网盘")
    @GetMapping("/SavePan")
    public Response savePan(
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


    @ApiOperation(value = "批量保存网盘")
    @PostMapping("/batchSavePan")
    public Response batchSavePan(
            @ApiParam(value = "这里上传特定格式的数据" +
                    "<br>1.百度网盘链接：https://pan.baidu.com/s/1a1qbM4kgl8YeDBH-9Pmmtw,提取码：c6um" +
                    "<br>2.https://pan.baidu.com/s/1xnBt2zpqKbPFvVoDaw09dA")
            @RequestParam(name = "listFile")
                    MultipartFile listFile) {
        Response response = new Response<>();
        try {
            String regex = "百度网盘链接：(.*?),提取码：(.*)";
            List<String> list = IOUtils.readLines(listFile.getInputStream(), "UTF-8");
            for (String line : list) {
                try {
                    if (line.matches(regex)) {
                        String url = line.replaceAll(regex, "$1");
                        String passwd = line.replaceAll(regex, "$2");
                        boolean result = panService.savePan(url, passwd);//保存
                        log.info("{}保存：{}", line, result);
                    } else {
                        boolean result = panService.savePan(line);//保存
                        log.info("{}保存：{}", line, result);
                    }
                } catch (Exception e) {
                    log.info("{}保存失败：{}", line, e);
                }

            }
//            response.setContent(result);
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

    @ApiOperation(value = "批量获取网盘账号密码")
    @PostMapping("/batchGetPan")
    public Response batchGetPan(
            @ApiParam(value = "这里上传特定格式的数据" +
                    "<br>[\"\\/dllDown\\/?CodeID=69195&id=1\",\"\\/dllDown\\/?CodeID=69195&id=2\",\"\\/dllDown\\/?CodeID=69195&id=3\",\"\\/dllDown\\/?CodeID=69195&id=4\",\"\\/dllDown\\/?CodeID=69195&id=5\",\"\\/dllDown\\/?CodeID=69195&id=6\",\"\\/dllDown\\/?CodeID=69195&id=7\",\"\\/dllDown\\/?CodeID=69195&id=8\",\"\\/dllDown\\/?CodeID=69195&id=9\",\"\\/dllDown\\/?CodeID=69195&id=1\"]")
            @RequestParam(name = "listFile")
                    MultipartFile listFile) {
        Response response = new Response<>();
        try {
            JsonMapper jsonMapper = new JsonMapper();
            List<String> list = IOUtils.readLines(listFile.getInputStream(), "UTF-8");
            for (String line : list) {
                String urlAndPass = "";
                JsonNode jsonNode = jsonMapper.readTree(line);
                JsonNode href = jsonNode.get(0);
                String tmp = "http://www.asp300.net/" + href.textValue();
                try {
                    String cookieValue = "Hm_lvt_d7682ab43891c68a00de46e9ce5b76aa=1575797927,1577508495; UM_distinctid=1745fdc46d46a3-0117f8fccac71d-15306256-1aeaa0-1745fdc46d57f7; ASPSESSIONIDQQQCADST=DBAAHIPAKINCIDMOMPOFNCJF; Hm_lvt_b56620492a36e2654a40162e6dcf4864=1598886799,1600267113; Hm_lvt_e74ddd953605e0810eb230f1b13ce662=1598886799,1600267113; lastLoginDate=2020%2F9%2F16+19%3A59%3A00; UserType=5; loginok=True; username=hcwang%2Ddocker; Enddate=2040%2F9%2F8; LiuLanJiLu%5Fstr=70156%24%242702%24%24%D2%D7%D3%C5cms%C0%B6%C9%AB%B5%D8%C8%C8%B7%D6%CB%AE%C6%F7%B9%AB%CB%BE%CD%F8%D5%BE%C4%A3%B0%E5%D4%B4%C2%EB+%B4%F8%CA%D6%BB%FA%B6%CB%24%24%2F2012uploadpic%2F2020%2D9%2D16%2F7015620209161310511%5F110%2Egif%7C%7C38617%24%242709%24%24%D0%C4%B6%AF%D3%E9%C0%D6%CD%F8%CD%BC%C6%AC%D5%BE%B3%CC%D0%F2%24%24%2F2012images%2F94x94%2Egif%7C%7C39152%24%242704%24%24%C0%CB%C2%FE%BB%E9%C0%F1%CE%EF%C6%B7%D7%A8%C2%F4%B5%EA%24%24%2F2012images%2F94x94%2Egif%7C%7C39220%24%242708%24%24%BF%AD%D0%FD%CA%C0%BC%CD%C2%C3%D0%D0%C9%E7%CD%F8%C2%E7%CF%FA%CA%DB%CF%B5%CD%B3%24%24%2F2012images%2F94x94%2Egif%7C%7C38065%24%242708%24%24%CC%EC%D0%C4ERP%D4%B4%C2%EB%24%24%2F2012images%2F94x94%2Egif%7C%7C59021%24%242709%24%242015%D7%EE%D0%C2%D0%A1%D6%EDcms%CE%A2%D0%C5o2o%C9%FA%BB%EE%CD%A8%CF%B5%CD%B3V1%2E23%C6%C6%BD%E2%B0%E6+%CD%EA%C3%C0%C6%C6%BD%E2%CE%DE%B0%B5%C1%B4%CE%DE%BC%D3%C3%DC%24%24%2F2012uploadpic%2F2015%2D8%2D30%2F590212015830817391%5F110%2Egif%7C%7C; Hm_lpvt_e74ddd953605e0810eb230f1b13ce662=1600267122; Hm_lpvt_b56620492a36e2654a40162e6dcf4864=1600267126; CNZZDATA1254065253=737725373-1599336107-http%253A%252F%252Fwww.asp300.net%252F%7C1600264908";
                    Map<String, List<String>> headerFields = URLUtils.getHeaderFields(tmp, cookieValue);
                    String head = URLUtils.getContentType(tmp, cookieValue);
                    int responseCode = URLUtils.getResponseCode(tmp, cookieValue);
                    if (404 == responseCode) {
                        log.info("pan:{},url:{} ,404", urlAndPass, tmp);
                        continue;
                    }
                    if (302 == responseCode) {
                        log.info("pan:{},url:{} ,302", headerFields.get("Location").get(0), tmp);
                        continue;
                    }
                    if ("application/octet-stream".equalsIgnoreCase(head)) {
                        log.info("pan:{} ,url:{} :真实文件", urlAndPass, tmp);
                        continue;
                    }
                    String pageSourceResult = URLUtils.toString(tmp, cookieValue, "gb2312");
                    if (pageSourceResult.contains("对象已移动")) {
                        String hrefWithoutPass = Jsoup.parse(pageSourceResult).getElementsByTag("a").attr("href");
                        urlAndPass = hrefWithoutPass;//pan 没有密码
                    } else {
                        urlAndPass = aspService.getUrlAndPass(pageSourceResult);//获取url和密码
                    }
                    log.info("pan:{} ,url:{}", urlAndPass, tmp);
                } catch (Exception e) {
                    log.info("{} 保存失败：{}", tmp, e);
                }

            }
//            response.setContent(result);
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
