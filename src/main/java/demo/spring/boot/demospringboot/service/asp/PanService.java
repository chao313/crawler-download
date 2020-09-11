package demo.spring.boot.demospringboot.service.asp;

import demo.spring.boot.demospringboot.util.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@Component
public class PanService {

    /**
     * 获取List任务
     */
    public Collection<String> batchSavePan(String url, String passwd) throws IOException {
        Set<String> result = new LinkedHashSet<>();

        return result;
    }

    /**
     * 获取List任务
     */
    public boolean savePan(String url, String passwd) throws IOException {
        File file = ResourceUtils.getFile("classpath:chromedriver_win32/chromedriver72.0.3626.69.exe");
        // 设置系统属性
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless");
        options.addArguments("-lang=zh-cn");
        // 实例化ChromeDriver
        WebDriver driver = new ChromeDriver(options);
        ChromeDriver chromeDriver = ((ChromeDriver) driver);

        // 启动浏览器 打开url
        chromeDriver.navigate().to(url);


        chromeDriver.findElement(By.id("accessCode")).sendKeys(passwd);

        WebElement submitBtn = chromeDriver.findElement(By.id("submitBtn"));

        submitBtn.click();

        chromeDriver.findElement(By.xpath("//*[@id=\"fileTreeDialog\"]/div[4]/a[2]")).click();

        return true;
    }


}
