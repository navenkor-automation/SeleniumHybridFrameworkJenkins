package com.qa.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.base.BaseTest;
import com.qa.utils.ExtentManager;
import com.qa.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;



public class TestListener implements ITestListener {
	
	// Color Constants
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        System.out.println("--- Test Suite Started ---");
    }

    @Override
    public void onTestStart(ITestResult result) {
    	
    	// VISUAL SEPARATION IN CONSOLE
        System.out.println("\n--- STARTING TEST: " + result.getMethod().getMethodName() + " ---");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    	// This will print "PASSED" in Green
    	System.out.println(ANSI_GREEN + " [RESULT] : PASSED" + ANSI_RESET);
        System.out.println("--------------------------------------------------");
        test.get().log(Status.PASS, "Test Passed Successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(ANSI_RED + "[RESULT] : FAILED" + ANSI_RESET);
        
        // 1. Safe way to get the error message
        String errorMessage = (result.getThrowable() != null) ? result.getThrowable().getMessage() : "Unknown Error";
        System.out.println(ANSI_RED + "Reason: " + errorMessage + ANSI_RESET);
        
        test.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());

        // 2. Defensive Driver Retrieval
        try {
            Object testClass = result.getInstance();
            if (testClass instanceof BaseTest) {
                WebDriver driver = ((BaseTest) testClass).getDriver();
                if (driver != null) {
                    String imgPath = ScreenshotUtils.captureScreenshot(driver, result.getName());
                    test.get().addScreenCaptureFromPath(imgPath);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
        System.out.println("--------------------------------------------------");
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(ANSI_YELLOW + " [RESULT] : SKIPPED" + ANSI_RESET);
        // Create the test instance if it wasn't created yet to avoid NullPointer
        if (test.get() == null) {
            ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
            test.set(extentTest);
        }
        test.get().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        System.out.println("--- Test Suite Finished | Report Generated ---");
    }
}