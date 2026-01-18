package com.qa.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.base.BaseTest;

public class WindowPaginationTest extends BaseTest {

    @Test
    public void verifyWindowSwitching() {
        // Example: Clicking social media link in footer
        elementUtils.doClick(By.xpath("//a[contains(@href, 'linkedin')]"));
        
        String parentID = driver.getWindowHandle();
        windowUtils.switchToTab(1); // Switch to the new tab
        
        System.out.println("New Window Title: " + driver.getTitle());
        Assert.assertTrue(driver.getTitle().contains("LinkedIn"));
        
        windowUtils.closeCurrentTabAndReturn(parentID);
    }

    @Test
    public void verifyPagination() {
        elementUtils.doClick(By.xpath("//span[text()='PIM']"));
        
        By nextBtn = By.xpath("//button/i[@class='oxd-icon bi-chevron-right']/..");
        By records = By.xpath("//div[@class='oxd-table-card']");
        
        // Search for a specific record across all pages
        boolean isFound = elementUtils.selectRecordFromPaginationTable(nextBtn, records, "Charlie");
        Assert.assertTrue(isFound, "Record not found in pagination table");
    }
}