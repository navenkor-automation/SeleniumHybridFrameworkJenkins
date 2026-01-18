package com.qa.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.base.BaseTest;

public class LoginTest extends BaseTest {
	
	@Test
	public void loginTest() {
	    logger.info("**** Starting loginTest ****");
	    logger.info("Entering username...");
	    // your code...
	    logger.info("Entering password...");
	    // your code...
	    logger.info("**** loginTest Completed Successfully ****");
	}

    @Test(priority = 1, description = "Success Login with Synchronization and Highlights")
    public void verifyLoginSuccess() {
        // Using elementUtils for automatic waiting and flashing
        elementUtils.doSendKeys(By.name("username"), "Admin");
        elementUtils.doSendKeys(By.name("password"), "admin123");
        elementUtils.doClick(By.tagName("button"));

        // Verify login success by checking the dashboard header
        boolean isDashboardPresent = waitUtils.waitForVisibility(By.xpath("//h6[text()='Dashboard']")).isDisplayed();
        Assert.assertTrue(isDashboardPresent, "Login failed!");
    }

    @Test(priority = 2, description = "Visual Child Window Handling")
    public void verifyChildWindowHandling() {
        elementUtils.doClick(By.xpath("//p[contains(@class, 'login-forgot-header')]"));
        
        jsUtils.pause(5000);
        String parentWindow = driver.getWindowHandle();
        windowUtils.switchToChildWindow();
        
        System.out.println("Child Window Title: " + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains("OrangeHRM"), "Child window title mismatch!");
        
        // Return to parent window so the session remains stable for the Listener
        driver.switchTo().window(parentWindow); 
    }
    
    @Test(groups = {"exclude"}, /*priority = 3, */ description = "Intentional Failure for Report")
    public void verifyFailureReport() {
        elementUtils.doSendKeys(By.name("username"), "WrongUser");
        elementUtils.doSendKeys(By.name("password"), "WrongPass");
        elementUtils.doClick(By.tagName("button"));

        // 1. Explicitly wait for the error message to appear first
        // This ensures we aren't getting an empty string ""
        By errorLocator = By.xpath("//p[contains(@class, 'oxd-alert-content-text')]");
        waitUtils.waitForVisibility(errorLocator);

        // 2. Get the text
        String actualError = elementUtils.doGetText(errorLocator);
        
        System.out.println("Actual Error Captured: " + actualError);

        // 3. This MUST fail. If actualError is "Invalid credentials", it won't match "Correct Text"
        Assert.assertEquals(actualError, "Correct Text", "FAILURE: The error message did not match 'Correct Text'");
    }
}