package com.qa.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.base.BaseTest;
import com.qa.pages.CommonElementsPage;

public class UtilityTest extends BaseTest {

    @Test
    public void verifyPaginationTest() {
        CommonElementsPage commonPage = new CommonElementsPage(driver);
        
        // Login and Navigate to Admin
        elementUtils.doSendKeys(By.name("username"), "Admin");
        elementUtils.doSendKeys(By.name("password"), "admin123");
        elementUtils.doClick(By.tagName("button"));
        elementUtils.doClick(By.xpath("//span[text()='Admin']"));

        // Use Pagination Utility to find a specific user in the big table
        boolean found = elementUtils.selectRecordFromPaginationTable(
                            commonPage.nextButton, 
                            commonPage.tableRows, 
                            "Charlie.Carter");
        
        Assert.assertTrue(found, "The user was not found in any pagination page!");
    }

    @Test
    public void verifyDragAndDrop() {
        // Note: OrangeHRM doesn't have much drag/drop, usually used in jQuery sites
        driver.get("https://jqueryui.com/resources/demos/droppable/default.html");
        CommonElementsPage commonPage = new CommonElementsPage(driver);
        
        actionUtils.doDragAndDrop(commonPage.source, commonPage.target);
        
        String text = elementUtils.doGetText(commonPage.target);
        Assert.assertEquals(text, "Dropped!");
    }

    @Test
    public void verifyAlertHandling() {
        // Trigger a JS Alert
        jsUtils.generateAlert("Are you sure?");
        
        // Handle it using our utility
        String alertText = alertUtils.getAlertText();
        alertUtils.acceptAlert();
        
        Assert.assertEquals(alertText, "Are you sure?");
    }
}