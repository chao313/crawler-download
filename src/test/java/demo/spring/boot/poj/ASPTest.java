package demo.spring.boot.poj;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

@Slf4j
public class ASPTest {

    /**
     * http://www.asp300.net/2012oth/pwdDown.asp
     */
    @Test
    public void xx() throws IOException {
        String url = "http://www.asp300.net/2012oth/pwdDown.asp?B1=%CF%C2%D4%D8&tj=1&yzm=";
        for (int i = 132784; i < 999999; i++) {
            String s = IOUtils.toString(new URL(url + i), "GB2312");
            log.info("s:{}", s);
            System.out.println("i:{}" + i + ",s:" + s);
            if (!s.contains("输入的下载密码不正确") && !s.contains("过期")) {
                System.out.println("s:" + s);
                log.info("s:{}", s);
            }

        }

    }


}
