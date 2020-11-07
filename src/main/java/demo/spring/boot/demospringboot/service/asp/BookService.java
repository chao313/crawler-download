package demo.spring.boot.demospringboot.service.asp;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Component
public class BookService {

    @Autowired
    private ASPService aspService;

    private WebDriver driver = null;

    @Value(value = "${local.chrome.dataPath}")
    private String dataPath;

    public synchronized void init() throws FileNotFoundException {
//        File file = ResourceUtils.getFile("classpath:chromedriver_win32/chromedriver72.0.3626.69.exe");
//         设置系统属性
//        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless");
        //设置共享数据
        options.addArguments("user-data-dir=" + dataPath);
        options.addArguments("-lang=zh-cn");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // 实例化ChromeDriver
        driver = new ChromeDriver(options);
    }


    /**
     * 获取List任务
     */
    public boolean read(String url, StringBuilder stringBuilder, String next) throws IOException, InterruptedException {
        synchronized (this) {
            if (null == driver) {
                this.init();
            }
        }
        ChromeDriver chromeDriver = ((ChromeDriver) driver);
        // 启动浏览器 打开url
        chromeDriver.navigate().to(url);
        try {
            do {
                String body = Jsoup.parse(chromeDriver.getPageSource()).getElementById("content1").text();
                stringBuilder.append(body).append("\n");
                new WebDriverWait(chromeDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(next)));
                chromeDriver.findElementByPartialLinkText(next).click();
            }
            while (true);
        } catch (Exception e) {
            log.info("无法找到accessCode元素，继续下一步,{}", e.toString());
        }
        return true;
    }


}
