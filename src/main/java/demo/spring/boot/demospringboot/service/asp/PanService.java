package demo.spring.boot.demospringboot.service.asp;

import demo.spring.boot.demospringboot.util.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@Component
public class PanService {

    @Autowired
    private ASPService aspService;

    private WebDriver driver = null;

    public void init() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:chromedriver_win32/chromedriver72.0.3626.69.exe");
        // 设置系统属性
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless");
        //设置共享数据
        options.addArguments("user-data-dir=C:\\Users\\hcwang.docker\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("-lang=zh-cn");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // 实例化ChromeDriver
        driver = new ChromeDriver(options);
    }

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
    public boolean savePan(String url, String passwd) throws IOException, InterruptedException {
        synchronized (this) {
            if (null == driver) {
                this.init();
            }
        }
        ChromeDriver chromeDriver = ((ChromeDriver) driver);
        //添加cookie
        chromeDriver.manage().getCookies().add(new Cookie("王海潮", "王海潮"));
        Thread.sleep(1000);
        // 启动浏览器 打开url
        chromeDriver.navigate().to(url);
        try {
            new WebDriverWait(chromeDriver, 10).until(ExpectedConditions.presenceOfElementLocated((By.id("accessCode"))));
            chromeDriver.findElement(By.id("accessCode")).sendKeys(passwd);
            new WebDriverWait(chromeDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("提取文件")));
            chromeDriver.findElementByPartialLinkText("提取文件").click();
        } catch (Exception e) {
            log.info("无法找到accessCode元素，继续下一步,{}", e.toString());
        }
        new WebDriverWait(chromeDriver, 100).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("保存到网盘")));
        chromeDriver.findElementByPartialLinkText("保存到网盘").click();
        new WebDriverWait(chromeDriver, 100).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("确定")));
        chromeDriver.findElementByPartialLinkText("确定").click();
        return true;
    }

    /**
     * 获取List任务
     */
    public boolean savePan(String url) throws IOException, InterruptedException {
        synchronized (this) {
            if (null == driver) {
                this.init();
            }
        }
        ChromeDriver chromeDriver = ((ChromeDriver) driver);
        //添加cookie
        chromeDriver.manage().getCookies().add(new Cookie("王海潮", "王海潮"));
        Thread.sleep(1000);
        // 启动浏览器 打开url
        chromeDriver.navigate().to(url);
        new WebDriverWait(chromeDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("保存到网盘")));
        chromeDriver.findElementByPartialLinkText("保存到网盘").click();
        new WebDriverWait(chromeDriver, 100).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText("确定")));
        chromeDriver.findElementByPartialLinkText("确定").click();
        return true;
    }

    /**
     * 批量获取pan账号密码
     */
    public synchronized String batchGetPan(String url) throws IOException, InterruptedException {
        synchronized (this) {
            if (null == driver) {
                this.init();
            }
        }
        String urlAndPass = "";
        ChromeDriver chromeDriver = ((ChromeDriver) driver);
        //添加cookie
        Thread.sleep(1000);
        // 启动浏览器 打开url
        chromeDriver.navigate().to(url);
        String pageSource = chromeDriver.getPageSource();
        if (pageSource.contains("百度网盘链接")) {
            urlAndPass = aspService.getUrlAndPass(pageSource);
        }
        if (pageSource.contains("保存到网盘")) {
            urlAndPass = chromeDriver.getCurrentUrl();
        }
        return urlAndPass;
    }

}
