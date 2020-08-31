package demo.spring.boot.demospringboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.URL;

/**
 * 初始化web驱动配置
 */
@Configuration
@Slf4j
public class WebDriverConfig {

    private final String path = "/chromedriver_win32/chromedriver72.0.3626.69.exe";

    @PostConstruct
    public void init() {
        URL url = WebDriverConfig.class.getResource(path);
        // 设置系统属性
        if (null == url) {
            log.warn("无法找到驱动:{}", path);
            return;
        }
        System.setProperty("webdriver.chrome.driver", url.getFile());
    }
}
