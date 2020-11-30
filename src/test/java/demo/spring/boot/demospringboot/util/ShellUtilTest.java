package demo.spring.boot.demospringboot.util;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ShellUtilTest {

    @Test
    public void exec() throws IOException {
        Runtime run = Runtime.getRuntime();
        InputStream in = null;
        Process process = run.exec(new String[]{"docker","ps","-a","-f" ,"status=running" ,"--format","{{.Names}}"});
        while (process.isAlive()) {
            in = process.getInputStream();
            String response = IOUtils.toString(in, "UTF-8");
            System.out.println("response:" + response);
            in = process.getErrorStream();
            response = IOUtils.toString(in, "UTF-8");
            in.close();
            System.out.println("response:" + response);
        }
    }
}
