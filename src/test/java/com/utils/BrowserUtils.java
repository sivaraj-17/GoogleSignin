package com.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
    public static Properties ticket;
    public static String downloadDir = System.getProperty("user.dir") + "\\src\\main\\resources\\Backup-code";

    public static void browserLaunch(String browser, String env, String url) {
        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().driverVersion("83.0.4103.61").setup();
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("download.default_directory", downloadDir);
            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.5735.16 Safari/537.36");

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setPlatform(Platform.LINUX);
            if (driver == null) {
                if (env.equalsIgnoreCase("Grid")) {
                    try {
                        driver = new RemoteWebDriver(new URL("http://10.118.11.94:4444/wd/hub"), capabilities); //xtime
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    driver = new ChromeDriver(options);
                }
                driver.get(url);
                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                driver.manage().window().maximize();
            }

            try {
                ticket = ResourceUtils.ticketProps();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void tearDown() {
        driver.quit();
    }
}
