package demo.spring.boot.demospringboot;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoSpringBootApplicationTests {

    @Value("classpath:2.txt")
    Resource resource;

    @Test
    public void contextLoads() throws IOException {
        File file = resource.getFile();
        List<String> list = FileUtils.readLines(file, "UTF-8");
        log.info("");
        LinkedHashSet<String> strings = new LinkedHashSet<>(list);

        StringBuilder stringBuilder = new StringBuilder();
        strings.forEach(s -> {
            stringBuilder.append(s).append("\n");
        });
        IOUtils.write(stringBuilder.toString().getBytes(), new FileOutputStream(new File("4.txt")));


    }

}
