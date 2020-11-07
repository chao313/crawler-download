package demo.spring.boot.demospringboot.controller.generate;

import demo.spring.boot.demospringboot.framework.Code;
import demo.spring.boot.demospringboot.framework.Response;
import demo.spring.boot.demospringboot.service.asp.BookService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/linkedin")
public class BookController {

    private static Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;


    @GetMapping("/getBook")
    public Response asp3000Chrome(
            @ApiParam(defaultValue = "http://www.iqishu.la/du/14/14026/8652901.html") @RequestParam(value = "url") String url,
            @ApiParam(defaultValue = "下一章") @RequestParam(value = "next") String next) {
        Response response = new Response<>();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            bookService.read(url, stringBuilder, next);
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
