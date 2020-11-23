package demo.spring.boot.demospringboot.util;

import org.junit.Test;

import java.io.IOException;

public class URLUtilsTest {

    @Test
    public void testCookie() throws IOException {
        String cookieValue = "ASPSESSIONIDACQTTSAQ=IGEELHJBJCGHIBCOJFPOKHAB;UserType=5;";
//                String loginSource = asp300FeignService.login("hcwang-docker", "Ys20140913!", "+++%B5%C7+%C2%BC+++");
        String url = "http://www.asp300.net/dllDown/?CodeID=69985&id=1";
        String pageSourceResult = URLUtils.toString(url, cookieValue, "gb2312");
        System.out.println("pageSourceResult:" + pageSourceResult);

    }
}
