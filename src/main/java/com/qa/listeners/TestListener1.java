package com.qa.listeners;

import org.openqa.selenium.*;
import org.apache.commons.io.FileUtils;
import org.testng.*;
import com.qa.base.BaseTestOld;
import java.io.File;

public class TestListener1 extends BaseTestOld implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        // Captures screenshot on failure
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File("./screenshots/" + result.getName() + ".png"));
            System.out.println("Screenshot captured for failed test: " + result.getName());
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }
}