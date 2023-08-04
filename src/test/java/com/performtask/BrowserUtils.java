package com.performtask;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BrowserUtils {
    public static WebDriver driver = null;
    static Properties ticket;
    static String downloadDir = System.getProperty("user.dir") + "\\src\\main\\resources\\Backup-code";

    public static void browserLaunch(String browser, String url) {

        if (browser.equalsIgnoreCase("Chrome")) {
//            WebDriverManager.chromedriver().setup();
            System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("download.default_directory", downloadDir);
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.5735.16 Safari/537.36");
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setPlatform(Platform.LINUX);
            if (driver == null) {
                if (true) {

                    try {
                        driver = new RemoteWebDriver(new URL("http://10.118.11.94:4444/wd/hub"), capabilities); //xtime
//                        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities); //own

                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    driver = new ChromeDriver(options);
                }
                driver.get(url);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
            }
        } else if (browser.equalsIgnoreCase("FireFox")) {
            WebDriverManager.firefoxdriver().setup();
            if (driver == null) {
                FirefoxOptions options = new FirefoxOptions();
                options.setCapability("moz:webdriverClick", false);
                driver = new FirefoxDriver(options);
                driver.get(url);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
            }
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            if (driver == null) {
                driver = new EdgeDriver();
                driver.get(url);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
            }
        }

        try {
            ticket = ResourceUtils.ticketProps();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void tearDown() {
        driver.quit();
    }
}
