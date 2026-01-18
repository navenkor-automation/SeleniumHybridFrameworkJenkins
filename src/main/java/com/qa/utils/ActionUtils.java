package com.qa.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionUtils {
    private WebDriver driver;
    private Actions actions;
    private WaitUtils waitUtils; // Added for stability

    public ActionUtils(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(this.driver);
        this.waitUtils = new WaitUtils(this.driver);
    }

    // --- YOUR PREVIOUS METHODS (Unchanged) ---
    
    public void doMoveToElement(By locator) {
        actions.moveToElement(waitUtils.waitForVisibility(locator)).perform();
    }

    public void doDoubleClick(By locator) {
        actions.doubleClick(waitUtils.waitForClickable(locator)).perform();
    }

    public void doContextClick(By locator) {
        actions.contextClick(waitUtils.waitForClickable(locator)).perform();

    }

    public void doDragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = waitUtils.waitForVisibility(sourceLocator);
        WebElement target = waitUtils.waitForVisibility(targetLocator);
        actions.dragAndDrop(source, target).perform();
    }

    public void selectAllAndDelete(By locator) {
        WebElement element = waitUtils.waitForClickable(locator);
        element.click();
        Keys modifier = System.getProperty("os.name").contains("Mac") ? Keys.COMMAND : Keys.CONTROL;
        
        actions.keyDown(modifier).sendKeys("a").keyUp(modifier)
               .sendKeys(Keys.BACK_SPACE).perform();
    }
    
    public void doSendKeysWithPause(By locator, String value, int pauseInMs) {
        WebElement element = waitUtils.waitForVisibility(locator);
        char[] chars = value.toCharArray();
        for (char c : chars) {
            actions.sendKeys(element, String.valueOf(c)).pause(pauseInMs).perform();
        }
    }

    // ============================================================
    // --- NEW HYBRID METHODS ADDED BELOW (Original code above) ---
    // ============================================================

    /**
     * Hovers over an element and then clicks it. 
     * Perfect for multi-level menus.
     */
    public void doMoveToElementAndClick(By locator) {
        actions.moveToElement(waitUtils.waitForVisibility(locator)).click().perform();
    }

    /**
     * Performs a click and hold action. 
     */
    public void doClickAndHold(By locator) {
        actions.clickAndHold(waitUtils.waitForClickable(locator)).perform();
    }

    /**
     * Releases the mouse at the current location.
     */
    public void doRelease() {
        actions.release().perform();
    }
}