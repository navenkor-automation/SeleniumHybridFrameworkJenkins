package com.qa.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports();

        	String startTime = new java.text.SimpleDateFormat("MMM dd, yyyy HH:mm:ss").format(new java.util.Date());
        	extent.setSystemInfo("Execution Start Time", startTime);
        	
        	ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/ExtentReport.html");
            //ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setReportName("OrangeHRM Automation Results");
            spark.config().setDocumentTitle("QA Regression Report");
            spark.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
            spark.config().setTheme(Theme.DARK);
            
            extent.attachReporter(spark);
            
            extent.setSystemInfo("Environment", "Production");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Organization", "N Software Testing"); // Custom branding
            extent.setSystemInfo("User", "QA Automation Engineer");
            
         // Inside your getInstance() method
            String jenkinsBuild = System.getenv("BUILD_NUMBER");
            if (jenkinsBuild == null) {
                jenkinsBuild = "Local Run"; // Fallback if you run from Eclipse
            }
            extent.setSystemInfo("Jenkins Build #", jenkinsBuild);

        }
        return extent;
        
    }    
}