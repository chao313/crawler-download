package demo.spring.boot.demospringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;

@SpringBootApplication
@EnableFeignClients
@MapperScan(value = {"demo.spring.boot.demospringboot.mybatis.dao", "demomaster.dao"})//mybatis
@ComponentScan(value = {"demo.spring.boot", "demomaster"})
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    /**
     * springboot 默认的就是 utf-8
     * 修改为iso-8859-1就会出现乱码
     *
     * @return
     */
    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        //StringHttpMessageConverter converter  = new StringHttpMessageConverter(Charset.forName("ISO-8859-1"));
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }


}
