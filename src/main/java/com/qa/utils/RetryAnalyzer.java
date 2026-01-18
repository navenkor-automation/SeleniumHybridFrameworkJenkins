package com.qa.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    // You can increase this to 2 if your environment is very unstable
    private static final int maxRunCount = 1; 

    @Override
    public boolean retry(ITestResult result) {
        // ITestResult.FAILURE = 2. Only retry if it actually failed.
        if (!result.isSuccess() && result.getStatus() == ITestResult.FAILURE) {
            if (count < maxRunCount) {
                count++;
                
                // --- NEW HYBRID LOGGING ---
                System.out.println("### Retrying test: " + result.getName() 
                                   + " | Attempt: " + count + " of " + maxRunCount + " ###");
                
                return true; 
            }
        }
        return false;
    }
}