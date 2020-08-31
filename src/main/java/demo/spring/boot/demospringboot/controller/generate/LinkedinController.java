package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.service.WebDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/linkedin")
public class LinkedinController {

    private static Logger LOGGER = LoggerFactory.getLogger(LinkedinController.class);

    @GetMapping("/test")
    public Response test() {
        Response response = new Response<>();
        try {
            WebDriverService.test();
            response.setCode(Code.System.OK);
            response.setContent("");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            LOGGER.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @GetMapping("/test2")
    public Response test2() {
        Response response = new Response<>();
        try {
            WebDriverService.test2();
            response.setCode(Code.System.OK);
            response.setContent("");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            LOGGER.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @GetMapping("/asp3000Chrome")
    public Response asp3000Chrome() {
        Response response = new Response<>();
        try {
            WebDriverService.asp3000Chrome();
            response.setCode(Code.System.OK);
            response.setContent("");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            LOGGER.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @GetMapping("/asp3000HtmlUnitDriver")
    public Response asp3000HtmlUnitDriver() {
        Response response = new Response<>();
        try {
            WebDriverService.asp3000HtmlUnitDriver();
            response.setCode(Code.System.OK);
            response.setContent("");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            LOGGER.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

    @GetMapping("/esChrome")
    public Response esChrome() {
        Response response = new Response<>();
        try {
            WebDriverService.esChrome();
            response.setCode(Code.System.OK);
            response.setContent("");
        } catch (Exception e) {
            response.setCode(Code.System.FAIL);
            response.setMsg(e.getMessage());
            response.addException(e);
            LOGGER.error("异常 ：{} ", e.getMessage(), e);
        }
        return response;

    }

}
