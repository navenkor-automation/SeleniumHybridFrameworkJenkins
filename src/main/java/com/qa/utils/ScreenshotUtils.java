package com.qa.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    // --- YOUR PREVIOUS METHOD ---
    public static String captureScreenshot(WebDriver driver, String fileName) {
        String path = System.getProperty("user.dir") + "/screenshots/" + fileName + ".png";
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    // ============================================================
    // --- NEW HYBRID METHODS ADDED BELOW (Original code above) ---
    // ============================================================

    /**
     * Captures screenshot with a timestamp to prevent overwriting files.
     * Use this for unique tracking of failures.
     */
    public static String captureScreenshotWithTimestamp(WebDriver driver, String testName) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String folderPath = System.getProperty("user.dir") + "/screenshots/";
        
        // Ensure directory exists
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String destination = folderPath + testName + "_" + timeStamp + ".png";
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(destination));
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
        return destination;
    }

    /**
     * Captures screenshot as a Base64 string.
     * Best for embedding images directly into Extent Reports or Allure Reports.
     */
    public static String getScreenshotAsBase64(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
}