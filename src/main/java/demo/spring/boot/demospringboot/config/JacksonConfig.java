package demo.spring.boot.demospringboot.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @description: JacksonConfig
 * 关键点：objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
 * 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化。
 * 其中枚举属性：JsonInclude.Include.NON_NULL有以下选择：
 * <p>
 * 属性	使用场景
 * Include.Include.ALWAYS	默认
 * Include.NON_DEFAULT	属性为默认值不序列化
 * Include.NON_EMPTY	属性为 空（""） 或者为 NULL 都不序列化
 * Include.NON_NULL	属性为NULL 不序列化
 */
@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;

    }
}

