package demo.spring.boot.demospringboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.URL;

/**
 * 初始化web驱动配置
 */
@Configuration
@Slf4j
public class WebDriverConfig implements InitializingBean {

    @Value(value = "${local.chrome.driverPath}")
    private String path;

    public void init() {
        URL url = WebDriverConfig.class.getResource(path);
        // 设置系统属性
        if (null == url) {
            log.warn("无法找到驱动:{}", path);
            return;
        }
        System.setProperty("webdriver.chrome.driver", url.getFile());
        log.info("驱动加载成功");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}
