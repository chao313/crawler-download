package demo.spring.boot.demospringboot.config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            connector.setProperty("relaxedQueryChars", "|{}[]\\");//解决 The valid characters are defined in RFC 7230 and RFC 3986
            connector.setMaxParameterCount(-1);//解决请求的参数太多
        });
        return factory;
    }
}
