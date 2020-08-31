package demo.spring.boot.demospringboot.service;

import demo.spring.boot.demospringboot.service.asp.ASPService;
import demo.spring.boot.demospringboot.util.RegexUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebDriverService {


    private static Logger LOGGER = LoggerFactory.getLogger(WebDriverService.class);

    public static void test() {
        try {
            File file = ResourceUtils.getFile("classpath:chromedriver_win32/chromedriver72.0.3626.69.exe");
            // 设置系统属性
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless");
            options.addArguments("-lang=zh-cn");
            // 实例化ChromeDriver
            WebDriver driver = new ChromeDriver(options);


            // 启动浏览器 打开百度
            driver.navigate().to("https://www.linkedin.com/login");

            ChromeDriver chromeDriver = ((ChromeDriver) driver);
            //输入查询的wd
            chromeDriver.findElementById("username").sendKeys("15618622161");
            //点击查询
            chromeDriver.findElementById("password").sendKeys("Ys20140913");
            WebDriverService.snapshot(chromeDriver, "第一张截图");
            //点击登陆
            chromeDriver.findElement(By.xpath("//button[text()='登录']")).click();

            chromeDriver.manage().getCookies();


            //搜索万得
            chromeDriver.findElementByClassName("search-global-typeahead__input").sendKeys("万得");
            Thread.sleep(800);
            chromeDriver.getKeyboard().sendKeys(Keys.DOWN);
            Thread.sleep(800);
            chromeDriver.getKeyboard().sendKeys(Keys.ENTER);

            //点击 查看领英上的全部...
            Thread.sleep(1000);
            chromeDriver.findElement(By.xpath("//span[contains(text(),'查看领英上的全部')]")).click();
            Thread.sleep(1000);
            //点击 全部筛选条件
            chromeDriver.findElement(By.xpath("//span[text()='全部筛选条件']")).click();
            Thread.sleep(1000);
            //设置职位 -> cp
            chromeDriver.findElementById("search-advanced-title").sendKeys("ceo");
            Thread.sleep(1000);
            //点击 确定
            chromeDriver.findElement(By.xpath("//span[text()='确定']")).click();
            Thread.sleep(1000);
            String text = chromeDriver.findElement(By.xpath("//div[@class='display-flex']")).getText();
            LOGGER.info("text:{}", text);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void test2() {
        try {
            ChromeOptions options = new ChromeOptions();
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chromeOptions", options);
            DesiredCapabilities requiredCapabilities = new DesiredCapabilities();
            requiredCapabilities.setCapability("lang", "zh-cn");

            HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(capabilities, requiredCapabilities);
            // 启动浏览器 打开百度

            htmlUnitDriver.navigate().to("https://www.linkedin.com/login");


            //输入查询的wd
            htmlUnitDriver.findElementById("username").sendKeys("15618622161");
            //点击查询
            htmlUnitDriver.findElementById("password").sendKeys("Ys20140913");
            htmlUnitDriver.getPageSource();
            //点击登陆
            htmlUnitDriver.findElement(By.xpath("//button[text()='Sign in']")).click();

            htmlUnitDriver.manage().getCookies();

            //搜索万得
            htmlUnitDriver.findElementByClassName("search-global-typeahead__input").sendKeys("万得");
            Thread.sleep(800);
            htmlUnitDriver.getKeyboard().sendKeys(Keys.DOWN);
            Thread.sleep(800);
            htmlUnitDriver.getKeyboard().sendKeys(Keys.ENTER);

            //点击 查看领英上的全部...
            Thread.sleep(1000);
            htmlUnitDriver.findElement(By.xpath("//span[contains(text(),'查看领英上的全部')]")).click();
            Thread.sleep(1000);
            //点击 全部筛选条件
            htmlUnitDriver.findElement(By.xpath("//span[text()='全部筛选条件']")).click();
            Thread.sleep(1000);
            //设置职位 -> cp
            htmlUnitDriver.findElementById("search-advanced-title").sendKeys("ceo");
            Thread.sleep(1000);
            //点击 确定
            htmlUnitDriver.findElement(By.xpath("//span[text()='确定']")).click();
            Thread.sleep(1000);
            String text = htmlUnitDriver.findElement(By.xpath("//div[@class='display-flex']")).getText();
            LOGGER.info("text:{}", text);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 完成截图功能
     *
     * @param drivername
     * @param filename
     */
    public static void snapshot(TakesScreenshot drivername, String filename) {
        // this method will take screen shot ,require two parameters ,one is driver name, another is file name

        String currentPath = System.getProperty("user.dir"); //get current work folder
        System.out.println(currentPath);
        File scrFile = drivername.getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy somewhere
        try {
            System.out.println("save snapshot path is:" + currentPath + "/" + filename);
            FileUtils.copyFile(scrFile, new File(currentPath + "\\" + filename));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Can't save screenshot");
            e.printStackTrace();
        } finally {

            System.out.println("screen shot finished");
        }
    }


    public static void asp3000Chrome() {
        try {
            File file = ResourceUtils.getFile("classpath:chromedriver_win32/chromedriver72.0.3626.69.exe");
            // 设置系统属性
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless");
            options.addArguments("-lang=zh-cn");
            // 实例化ChromeDriver
            WebDriver driver = new ChromeDriver(options);

            // 启动浏览器 打开百度
            driver.navigate().to("http://www.asp300.net/SoftList/11/11_1.html");
            ChromeDriver chromeDriver = ((ChromeDriver) driver);
            boolean flag = true;
            do {
                Set<String> downUrls = ASPService.getHrefFromListPage(chromeDriver.getPageSource());
                downUrls.forEach(downUrl -> {
                    LOGGER.info("下载URL: {}", "wget " + downUrl + " -O " + RegexUtil.RegexValueListFirst(downUrl, "CodeID=(\\d*?)\\\\&", 1));
                });
                WebElement element = chromeDriver.findElement(By.xpath("//a[text()='下页']"));
                if (null == element) {
                    flag = false;
                } else {
                    element.click();
                }
            } while (flag);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void asp3000HtmlUnitDriver() {
        try {
            ChromeOptions options = new ChromeOptions();
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chromeOptions", options);
            DesiredCapabilities requiredCapabilities = new DesiredCapabilities();
            requiredCapabilities.setCapability("lang", "zh-cn");

            HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(capabilities, requiredCapabilities);

            // 启动浏览器 打开百度
            htmlUnitDriver.navigate().to("http://www.asp300.net/SoftList/11/11_1.html");


            htmlUnitDriver.findElement(By.xpath("//a[text()='下页']")).click();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void esChrome() {
        try {
//            "美国", "印度", "日本", "加拿大", "英国", "中国台湾", "韩国", "澳大利亚",
//                    "开曼群岛", "法国", "马来西亚", "波兰", "新加坡", "德国", "百慕大", "泰国", "以色列", "印度尼西亚",
//                    "土耳其", "意大利", "瑞典", "俄罗斯", "南非", "瑞士", "菲律宾", "挪威", "中国香港", "巴西", "智利",
//                    "希腊", "埃及", "中国", "荷兰", "秘鲁", "西班牙", "比利时", "丹麦", "英属维尔京群岛", "芬兰", "爱尔兰",
//                    "新西兰", "墨西哥", "根西岛", "泽西岛(英)", "奥地利", "马恩岛", "哥伦比亚", "葡萄牙", "马绍尔群岛",
//                    "匈牙利", "卢森堡", "塞浦路斯", "阿根廷", "捷克", "巴拿马", "格恩西岛", "波多黎各", "哈萨克斯坦",
//                    "直布罗陀",
//                    List<String> list = Arrays.asList("泽西岛", "立陶宛", "巴哈马", "巴基斯坦", "尼日利亚", "巴林", "斯洛文尼亚", "津巴布韦",
//                    "爱沙尼亚", "黎巴嫩", "伯利兹", "保加利亚", "克罗地亚", "卡塔尔", "巴巴多斯", "斯洛伐克", "毛里求斯"
//                    , "赞比亚", "阿拉伯联合酋长国", "马尔维那斯群岛", "马耳他", "K1", "K2", "列支敦士登",
//                    "利比里亚", "哥斯达黎加", "坦桑尼亚", "孟加拉国",
//                    "安提瓜和巴布达", "巴布亚新几内亚", "库拉索岛",
//                    "拉脱维亚", "摩洛哥", "摩纳哥", "科威特",
//                    "突尼斯", "肯尼亚", "英属印度洋领地", "荷属安的列斯",
//                    "阿曼", "肯尼亚", "英属印度洋领地", "荷属安的列斯",
//                    "阿曼", "马拉维");
            List<String> list = Arrays.asList("印度");
            File file = ResourceUtils.getFile("classpath:chromedriver_win32/chromedriver72.0.3626.69.exe");
            // 设置系统属性
            System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
            ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless");
            options.addArguments("-lang=zh-cn");
            // 实例化ChromeDriver
            WebDriver driver = new ChromeDriver(options);

            // 启动浏览器 打开百度

            ChromeDriver chromeDriver = ((ChromeDriver) driver);

            for (String country : list) {
                String url = "http://10.200.5.217:5601/app/kibana#/discover?_g=(refreshInterval:(display:Off,pause:!f,value:0),time:(from:now-15m,mode:quick,to:now))&_a=(columns:!(F21_0088,F1_0088),filters:!(),index:tb_object_0088,interval:auto,query:(query_string:(analyze_wildcard:!t,query:'F25_0088:298050000%20AND%20F21_0088:" + country + "')),sort:!(_score,desc),vis:(aggs:!((params:(field:F21_0088,orderBy:'2',size:20),schema:segment,type:terms),(id:'2',schema:metric,type:count)),type:histogram))&indexPattern=tb_object_0088&type=histogram";
                driver.navigate().to(url);

                int sum = 0;
                int dataSum = 0;
                Thread.sleep(8000);
                WebElement sumElement = chromeDriver.findElement(By.xpath("//*[@id=\"kibana-body\"]/div[2]/div/div/div/div[2]/div[2]/div[1]/strong"));
                if (null != sumElement) {
                    //去除逗号
                    sum = Integer.valueOf(sumElement.getText().replace(",", ""));
                } else {
                    LOGGER.info("无数据异常");
                }

                for (int i = 0; i < 3000; i++) {
                    ((ChromeDriver) driver).executeScript("var heilth = window.scrollTo(0,document.body.scrollHeight)");
                }

//                /**
//                 * 如果数据不一致，会不停的执行
//                 */
//                do {
//                    ((ChromeDriver) driver).executeScript("var heilth = window.scrollTo(0,document.body.scrollHeight)");
//
//                    WebElement text = chromeDriver.findElement(By.xpath("//*[@id=\"kibana-body\"]/div[2]/div/div/div/div[2]/div[2]/div[2]/div[3]"));
//                    if (null != text) {
////                        dataSum = text.getText().split("\n").length;
//                        dataSum = appearNumber(text.getText(), country);
//                        LOGGER.info("翻页 -> dataSum :{}", dataSum);
//                    }
//                } while (sum != dataSum);
                Thread.sleep(3000);
                String text = chromeDriver.findElement(By.xpath("//*[@id=\"kibana-body\"]/div[2]/div/div/div/div[2]/div[2]/div[2]/div[3]")).getText();
                LOGGER.info("country:{} -> {}", country, text);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText  源字符串
     * @param findText 要查找的字符串
     * @return
     */
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }


}
