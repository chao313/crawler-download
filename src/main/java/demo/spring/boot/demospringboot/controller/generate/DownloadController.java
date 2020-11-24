package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.controller.resource.service.ResourceService;
import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.service.download.DownloadAndParse;
import demo.spring.boot.demospringboot.util.URLUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/DownloadController")
public class DownloadController {

    @Autowired
    @Qualifier("DefaultDownloadAndParse")
    private DownloadAndParse downloadAndParse;


    @Autowired
    private ResourceService resourceService;


    @ApiOperation(value = "下载详情页")
    @GetMapping("/downloadDetail")
    public Response downloadDetail(
            @ApiParam(defaultValue = "http://www.asp300.net/SoftView/27/SoftView_69985.html")
            @RequestParam(value = "url")
                    String url,
            @RequestParam(value = "cookie")
                    String cookie,
            @ApiParam(hidden = true)
            @RequestHeader(value = "host") String host) {
        Response response = new Response<>();
        try {
            downloadAndParse.doWork(url, "GB2312", resourceService.getTmpDir(), cookie);
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


    @ApiOperation(value = "getLoginCookie")
    @GetMapping("/getLoginCookie")
    public Response getLoginCookie() {
        Response response = new Response<>();
        try {
            String cookieByLogin = URLUtils.getCookieByLogin("http://www.asp300.net/2012user/login.asp?action=chk", "hcwang-docker", "Ys20140913!");
            response.setCode(Code.System.OK);
            response.setContent(cookieByLogin);
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            log.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }
}
