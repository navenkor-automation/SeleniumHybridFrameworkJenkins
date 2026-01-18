package com.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.base.BaseTest;

public class WindowFrameTest extends BaseTest {

    @Test(priority = 5) // Continues the sequence after AdminTest
    public void testWindowHandle() {
        // 1. Click using elementUtils
        elementUtils.doClick(By.xpath("//a[text()='OrangeHRM, Inc']"));
        
        // 2. Switch focus using windowUtils
        jsUtils.pause(5000); 
        String parentWindow = driver.getWindowHandle();
        windowUtils.switchToChildWindow();
        
        // 3. Verify
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("orangehrm.com"), "Failed to switch to the new window!");
        
        // 4. Return focus to the original tab (Safer for the Listener than closing)
        driver.switchTo().window(parentWindow); 
    }

    @Test(priority = 6)
    public void testFrames() {
        // 1. Wait for frame and switch (Crucial for Jenkins timing)
    	waitUtils.waitForFrameAndSwitch(By.id("mce_0_ifr"));        
        WebElement editor = driver.findElement(By.id("tinymce"));
        
        // 2. Scroll into view and click to gain focus
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editor);
        editor.click(); 

        // 3. Clear existing text using the JS backup
        try {
            editor.clear();
        } catch (Exception e) {
            System.out.println("Standard clear failed, using JS clear...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].innerHTML = '';", editor);
        }
        
        // 4. Type the content
        editor.sendKeys("Automation Testing is ready!");
        
        // 5. Switch back to the main page so the next test doesn't fail!
        driver.switchTo().defaultContent();
    }
}