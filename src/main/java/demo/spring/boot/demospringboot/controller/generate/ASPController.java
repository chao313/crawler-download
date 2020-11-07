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
import demo.spring.boot.demospringboot.util.RestTemplateUtils;
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
    @PostMapping("/batchGetPanUrl")
    public Response batchGetPanUrl(
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
                String cookieValue = "UM_distinctid=1747c4f5d8220e-02e3d8367e73f-383e570a-1fa400-1747c4f5d83478; ASPSESSIONIDQQRBACSS=GDGKGMCALGKPKPJMDLGAIIHL; Hm_lvt_b56620492a36e2654a40162e6dcf4864=1599788084,1600155786,1600155837,1600248079; Hm_lvt_e74ddd953605e0810eb230f1b13ce662=1599788085,1600155787,1600155838,1600248079; UserType=5; loginok=True; username=hcwang%2Ddocker; Enddate=2040%2F9%2F8; LiuLanJiLu%5Fstr=70142%24%242718%24%24%B8%F7%C0%E0%D6%B1%B2%A5%C6%BD%CC%A8%BE%DB%BA%CF%D6%B1%B2%A5%B5%BC%BA%BD%CD%F8%D5%BE%D4%B4%C2%EB%24%24%2F2012uploadpic%2F2020%2D9%2D16%2F7014220209161108271%5F110%2Egif%7C%7C70156%24%242702%24%24%D2%D7%D3%C5cms%C0%B6%C9%AB%B5%D8%C8%C8%B7%D6%CB%AE%C6%F7%B9%AB%CB%BE%CD%F8%D5%BE%C4%A3%B0%E5%D4%B4%C2%EB+%B4%F8%CA%D6%BB%FA%B6%CB%24%24%2F2012uploadpic%2F2020%2D9%2D16%2F7015620209161310511%5F110%2Egif%7C%7C67390%24%242716%24%24%D7%EE%D0%C2%D3%F1%BD%E0%B9%FA%BC%CA%BD%F0%C8%DA%C0%ED%B2%C6%D4%B4%C2%EB+wap%CD%EA%D5%FB%B0%E6+%B4%F8%D6%A7%B8%B6%BC%B0%B6%CC%D0%C5%B6%D4%BD%D3%BD%CC%B3%CC%24%24%2F2012uploadpic%2F2019%2D11%2D11%2F67390201911111039081%5F110%2Egif%7C%7C65023%24%242704%24%242018%D7%EE%D0%C2%B6%FE%B4%CE%BF%AA%B7%A2%B0%E6%BA%C3%C9%CC%B3%C7%B6%E0%D3%C3%BB%A7%C9%CC%B3%C7%CF%B5%CD%B3+%B6%E0%D6%D6%B5%C7%C2%BC%B7%BD%CA%BD+%D6%A7%B3%D6%B6%E0%D6%D6%D6%A7%B8%B6%B7%BD%CA%BD%24%24%2F2012uploadpic%2F2018%2D12%2D18%2F65023201812181108491%5F110%2Egif%7C%7C67702%24%242709%24%24%B0%D9%B7%D6%B0%D9%CA%D5%BF%A8%CD%F8%C0%F1%C6%B7%BF%A8%B6%D2%BB%BB%B6%FE%CA%D6%C0%F1%C6%B7%BF%A8%BB%D8%CA%D5%CD%F8%D5%BE%D4%B4%C2%EB+Thinkphp%C4%DA%BA%CB%24%24%2F2012uploadpic%2F2020%2D1%2D1%2F67702202011833031%5F110%2Egif%7C%7C39141%24%242709%24%24%B1%B4%B1%B4%C0%D6%D4%B0%C8%AB%D5%BE%CF%B5%CD%B3%24%24%2F2012images%2F94x94%2Egif%7C%7C; ASPSESSIONIDQQQCADST=EOIPGIPAHCCPNECGKIAMFJPE; Hm_lpvt_e74ddd953605e0810eb230f1b13ce662=1600305409; bj%5Fip%5Fid=3; bj%5Fcode%5Fid=70156; bj%5Fcode%5Fname=%D2%D7%D3%C5cms%C0%B6%C9%AB%B5%D8%C8%C8%B7%D6%CB%AE%C6%F7%B9%AB%CB%BE%CD%F8%D5%BE%C4%A3%B0%E5%D4%B4%C2%EB+%B4%F8%CA%D6%BB%FA%B6%CB; CNZZDATA1254065253=1770504000-1599813405-http%253A%252F%252Fwww.asp300.net%252F%7C1600301407; lastLoginDate=2020%2F9%2F16+22%3A38%3A00; Hm_lpvt_b56620492a36e2654a40162e6dcf4864=1600305628";
                try {
                    Map<String, List<String>> headerFields = URLUtils.getHeaderFields(tmp, cookieValue);
                    String contentType = URLUtils.getContentType(tmp, cookieValue);

                    int responseCode = URLUtils.getResponseCode(tmp, cookieValue);
                    if (302 == responseCode) {
                        log.info("302pan:{},url:{}", headerFields.get("Location").get(0), tmp);
                        continue;
                    }
                    if (404 == responseCode) {
                        log.info("404 pan:{},url:{}", urlAndPass, tmp);
                        continue;
                    }
                    if ("application/octet-stream".equalsIgnoreCase(contentType)) {
                        log.info("真实文件pan:{},url:{}", urlAndPass, tmp);
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
                    String toString = RestTemplateUtils.getToString(tmp, cookieValue, "");
                    log.info("toString:{}", toString);
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
    @PostMapping("/batchGetPanDriver")
    public Response batchGetPanDriver(
            @ApiParam(value = "这里上传特定格式的数据" +
                    "<br>[\"\\/dllDown\\/?CodeID=69195&id=1\",\"\\/dllDown\\/?CodeID=69195&id=2\",\"\\/dllDown\\/?CodeID=69195&id=3\",\"\\/dllDown\\/?CodeID=69195&id=4\",\"\\/dllDown\\/?CodeID=69195&id=5\",\"\\/dllDown\\/?CodeID=69195&id=6\",\"\\/dllDown\\/?CodeID=69195&id=7\",\"\\/dllDown\\/?CodeID=69195&id=8\",\"\\/dllDown\\/?CodeID=69195&id=9\",\"\\/dllDown\\/?CodeID=69195&id=1\"]")
            @RequestParam(name = "listFile")
                    MultipartFile listFile) {
        Response response = new Response<>();
        try {
            JsonMapper jsonMapper = new JsonMapper();
            List<String> list = IOUtils.readLines(listFile.getInputStream(), "UTF-8");
            int total = list.size();
            int i = 1;
            boolean flag = false;
            for (String line : list) {
                log.info("i:{}/total:{}", i++, total);
                if (flag == false) {
                    if (line.contains("CodeID=38715&id=1")) {
                        log.info("定位到");
                        flag = true;
                    }
                    log.info("跳过");
                    continue;
                }
                String urlAndPass = "";
                JsonNode jsonNode = jsonMapper.readTree(line);
                JsonNode href = jsonNode.get(0);
                if (null == href) {
                    log.info("为null跳过");
                    continue;
                }
                String tmp = "http://www.asp300.net/" + href.textValue();
                try {
                    urlAndPass = panService.batchGetPan(tmp);
                    log.info("pan:{} ,url:{}", urlAndPass, tmp);
                } catch (Exception e) {
                    log.info("{} 保存失败：{}", tmp, e);
                }

            }
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
