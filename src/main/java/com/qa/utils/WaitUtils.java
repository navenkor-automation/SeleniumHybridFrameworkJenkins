package com.qa.utils;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class WaitUtils {
    private WebDriver driver;
    private WebDriverWait wait;
    private final int TIMEOUT = 20;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    // --- Explicit Wait Methods ---
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForFrameAndSwitch(By locator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    public boolean waitForInvisibility(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // --- Fluent Wait Method ---
    public WebElement waitForElementFluent(By locator) {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TIMEOUT))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return fluentWait.until(d -> d.findElement(locator));
    }
    
    public void waitForFrameAndSwitch(String idOrName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
    }

    // ============================================================
    // --- NEW HYBRID METHODS ADDED BELOW (Original code above) ---
    // ============================================================

    public String waitForTitleIs(String titleValue) {
        if (wait.until(ExpectedConditions.titleIs(titleValue))) {
            return driver.getTitle();
        }
        return null;
    }

    public boolean waitForURLContains(String urlFraction) {
        return wait.until(ExpectedConditions.urlContains(urlFraction));
    }

    public Alert waitForAlert() {
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public WebElement waitForPresenceOfElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}