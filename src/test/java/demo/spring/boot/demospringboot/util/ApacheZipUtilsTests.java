package demo.spring.boot.demospringboot.util;


import org.junit.Test;

import java.io.InputStream;

public class ApacheZipUtilsTests {

    @Test
    public void unzip() throws Exception {
        InputStream resourceAsStream = ApacheZipUtilsTests.class.getResourceAsStream("/kehucx.rar");
        ApacheZipUtils.unZip(resourceAsStream);
    }
}
