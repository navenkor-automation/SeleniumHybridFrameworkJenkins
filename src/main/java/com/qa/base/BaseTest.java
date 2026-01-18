package com.qa.base;

import java.time.Duration;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

// All your utility imports...
import com.qa.utils.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
    
    protected WebDriver driver;
    protected Properties prop;
    protected ConfigReader configReader;
    protected ElementUtils elementUtils;
    protected JavaScriptUtils jsUtils;
    protected WaitUtils waitUtils;
    protected ActionUtils actionUtils;
    protected AlertUtils alertUtils;
    protected WindowUtils windowUtils;
    protected VerificationUtils verificationUtils;
    
    public static Logger logger;

    @BeforeMethod
    public void setup() {
    	// This line ensures the log shows which specific Test class is running
        logger = LogManager.getLogger(this.getClass());        
        logger.info("Starting execution for: " + this.getClass().getName());
        
        // ... your driver setup code
        configReader = new ConfigReader();
        prop = configReader.init_prop();
        
        // --- 1. NEW: Perform Health Check before starting the browser ---
        checkConfigHealth();
        
        String browserName = prop.getProperty("browser");
        String url = prop.getProperty("url");

        if (browserName == null) { browserName = "chrome"; }
        browserName = browserName.trim().toLowerCase();

        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            org.openqa.selenium.chrome.ChromeOptions options = new org.openqa.selenium.chrome.ChromeOptions();
            if ("true".equalsIgnoreCase(prop.getProperty("headless"))) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
        } else if (browserName.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

            waitUtils = new WaitUtils(driver);
            jsUtils = new JavaScriptUtils(driver);
            actionUtils = new ActionUtils(driver);
            windowUtils = new WindowUtils(driver);
            elementUtils = new ElementUtils(driver);
            verificationUtils = new VerificationUtils(driver);

            if (url != null) {
                driver.get(url.trim());
            }
        }
    }
    
    
    // --- 2. NEW: Health Check Method for Jenkins Console ---
    public void checkConfigHealth() {

String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        
        System.out.println("====================================================");
        System.out.println("        HYBRID FRAMEWORK EXECUTION START            ");
        System.out.println("        Time: " + timestamp);
        System.out.println("====================================================");
        
        String url = prop.getProperty("url");
        String user = prop.getProperty("username");
        String excel = prop.getProperty("excelpath");

        if (url != null && user != null && excel != null) {
        	System.out.println("[STATUS] : PASS");
            System.out.println("[URL] : " + url);
            System.out.println("[LOGIN USER] : " + user);
        } else {
            System.out.println("[STATUS] : FAIL - Check your config.properties file!");
            if (user == null) System.out.println("[ERROR] : 'username' key is missing!");
        }
        System.out.println("====================================================");
    }

    // --- 3. NEW: Screenshot Utility for Listeners ---
    public String getScreenshot(String testCaseName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + testCaseName + ".png";
        try {
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public WebDriver getDriver() {
        return driver;
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}