package com.qa.utils;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.qa.utils.JavaScriptUtils;
import com.qa.utils.WaitUtils;

public class ElementUtils {
    private WebDriver driver;
    private JavaScriptUtils jsUtils;
    private WaitUtils waitUtils;

    public ElementUtils(WebDriver driver) {
        this.driver = driver;
        this.jsUtils = new JavaScriptUtils(driver);
        this.waitUtils = new WaitUtils(driver);
    }
    
    

    // --- ELEMENT INTERACTION ---
	/*
	 * public void doSendKeys(By locator, String value) { WebElement element =
	 * waitUtils.waitForVisibility(locator); jsUtils.setBorder(element);
	 * element.clear(); element.sendKeys(value); }
	 */ 
    public void doSendKeys(By locator, String value) {
        if (value == null) {
            System.out.println("SKIPPING: The value for locator " + locator + " is NULL. Check your Excel data.");
            return; // This prevents the crash
        }
        
        try {
            WebElement element = getElement(locator);
            element.clear();
            element.sendKeys(value);
        } catch (Exception e) {
            System.out.println("Error while sending keys to: " + locator);
            e.printStackTrace();
        }
    }

    private WebElement getElement(By locator) {
        return driver.findElement(locator);
    }


	public void doClick(By locator) {
        WebElement element = waitUtils.waitForClickable(locator);
        element.click();
    }

    public String doGetText(By locator) {
        return waitUtils.waitForVisibility(locator).getText();
    }

    // --- DROPDOWN UTILITIES (Standard Select Tag) ---
    public void doSelectByIndex(By locator, int index) {
        Select select = new Select(waitUtils.waitForVisibility(locator));
        select.selectByIndex(index);
    }

    public void doSelectByVisibleText(By locator, String text) {
        Select select = new Select(waitUtils.waitForVisibility(locator));
        select.selectByVisibleText(text);
    }

    public void doSelectByValue(By locator, String value) {
        Select select = new Select(waitUtils.waitForVisibility(locator));
        select.selectByValue(value);
    }

    // --- CUSTOM DROPDOWN (Bootstrap/Div/Li - used in OrangeHRM) ---
    public void doSelectValueFromDropdown(By locator, String value) {
        List<WebElement> optionsList = driver.findElements(locator);
        for (WebElement e : optionsList) {
            if (e.getText().equals(value)) {
                e.click();
                break;
            }
        }
    }

    // --- FILE UPLOAD ---
    public void doUploadFile(By locator, String filePath) {
        driver.findElement(locator).sendKeys(filePath);
    }

    // --- DATE PICKER ---
    public void selectDate(By locator, String dateValue) {
        WebElement dateField = waitUtils.waitForVisibility(locator);
        dateField.clear();
        dateField.sendKeys(dateValue);
    }

    // --- SCROLLING ---
    public void scrollDown(int pixels) {
        jsUtils.scrollPageByPixels(pixels);
    }

    public void scrollToElement(By locator) {
        WebElement element = waitUtils.waitForVisibility(locator);
        jsUtils.scrollIntoView(element);
    }
    
    // --- PAGINATION ---
    public boolean selectRecordFromPaginationTable(By nextBtn, By allRecords, String targetName) {
        boolean isFound = false;
        while (!isFound) {
            List<WebElement> rows = driver.findElements(allRecords);
            for (WebElement row : rows) {
                if (row.getText().contains(targetName)) {
                    isFound = true;
                    return true; 
                }
            }
            WebElement next = driver.findElement(nextBtn);
            if (next.isEnabled() && next.isDisplayed()) {
                next.click();
            } else {
                break; 
            }
        }
        return isFound;
    }

    // ============================================================
    // --- NEW HYBRID METHODS ADDED BELOW (Original code above) ---
    // ============================================================

    public boolean isElementDisplayed(By locator) {
        return waitUtils.waitForVisibility(locator).isDisplayed();
    }

    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    public List<String> getElementsTextList(By locator) {
        List<WebElement> eleList = getElements(locator);
        List<String> eleTextList = new ArrayList<>();
        for (WebElement e : eleList) {
            String text = e.getText();
            if (!text.isEmpty()) {
                eleTextList.add(text);
            }
        }
        return eleTextList;
    }
}