package com.qa.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, 
                         Constructor testConstructor, Method testMethod) {
        
        // --- YOUR PREVIOUS LOGIC (Unchanged) ---
        // 1. Automatically sets the retry logic for every @Test method in the project
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
        
        // ============================================================
        // --- NEW HYBRID LOGIC ADDED BELOW (Original code above) ---
        // ============================================================

        /*
         * 2. EXCEL/CONFIG DRIVEN EXECUTION:
         * You can add logic here to check if a test should be enabled.
         * For example: if (testMethod.getName().equals("testToSkip")) { annotation.setEnabled(false); }
         */
        
        // Example: Only run tests if they are not specifically disabled in a config
        // String status = ConfigReader.get("runStatus"); 
        // if(status.equalsIgnoreCase("false")) { annotation.setEnabled(false); }
    }
}