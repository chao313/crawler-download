package demo.spring.boot.demospringboot.config;

import feign.Logger;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
@Slf4j
public class FeignServiceConfig {

    /**
     * Logger.Level 的具体级别如下：
     * NONE：不记录任何信息
     * BASIC：仅记录请求方法、URL以及响应状态码和执行时间
     * HEADERS：除了记录 BASIC级别的信息外，还会记录请求和响应的头信息
     * FULL：记录所有请求与响应的明细，包括头信息、请求体、元数据
     *
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 为feign重写异常解析(把错误信息放入msg中)
     * ->ErrorDecoder已经内置的Default
     * ->配合切面来使用，发生异常时,把错误数据返回
     *
     * @return
     */
    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new ErrorDecoder.Default() {
            @Override
            public Exception decode(String methodKey, Response response) {
                String responseError = "为解析异常";
                try {
                    responseError = IOUtils.toString(response.body().asInputStream(), "UTF-8");
                    log.error("feign请求发生异常:{}", responseError);
                } catch (IOException e) {
                    log.error("解析返回值异常:{}", response, e);
                }
                return new RuntimeException(responseError);
            }
        };
    }


}


