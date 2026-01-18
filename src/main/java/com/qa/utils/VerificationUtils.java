package com.qa.utils;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class VerificationUtils {
    private SoftAssert softAssert;

    public VerificationUtils() {
        softAssert = new SoftAssert();
    }
    
    

    public VerificationUtils(WebDriver driver) {
        softAssert = new SoftAssert();
	}



	// --- YOUR PREVIOUS METHODS ---
    public void verifyText(String actual, String expected, String message) {
        softAssert.assertEquals(actual, expected, message);
    }

    public void assertAll() {
        softAssert.assertAll();
    }

    // ============================================================
    // --- NEW HYBRID METHODS ADDED BELOW (Original code above) ---
    // ============================================================

    /**
     * Verifies boolean conditions (e.g., isDisplayed, isEnabled)
     */
    public void verifyCondition(boolean condition, String message) {
        softAssert.assertTrue(condition, message);
    }

    /**
     * Verifies if the actual text contains the expected partial string
     * Very useful for dynamic web content.
     */
    public void verifyContains(String actual, String expectedPartial, String message) {
        boolean contains = actual != null && actual.contains(expectedPartial);
        softAssert.assertTrue(contains, message + " [Actual: " + actual + " | Expected to contain: " + expectedPartial + "]");
    }

    /**
     * Verifies that an object/string is not null
     */
    public void verifyNotNull(Object obj, String message) {
        softAssert.assertNotNull(obj, message);
    }
}