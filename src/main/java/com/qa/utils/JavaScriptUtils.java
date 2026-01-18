package com.qa.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {
    private WebDriver driver;

    public JavaScriptUtils(WebDriver driver) {
        this.driver = driver;
    }

    // --- STYLE A: Background Flashing (Original logic) ---
    public void flash(WebElement element) {
        String bgColor = element.getCssValue("backgroundColor");
        for (int i = 0; i < 10; i++) {
            changeColor("rgb(255, 0, 0)", element); // Red background
            changeColor(bgColor, element); // Original background
        }
    }

    private void changeColor(String color, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
        try {
            Thread.sleep(30); 
        } catch (InterruptedException e) {}
    }

    // --- STYLE B: Border Highlighting ---
    public void drawBorder(WebElement element) {
        String originalBorder = element.getCssValue("border"); 
        for (int i = 0; i < 3; i++) { 
            applyBorderStyle("3px solid red", element); 
            applyBorderStyle(originalBorder, element);  
        }
    }
    
    // --- STEADY HIGHLIGHT: Red border once ---
    public void setBorder(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border = '3px solid red'", element);
    }

    // --- STEADY HIGHLIGHT: Red background once ---
    public void setBackground(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.backgroundColor = 'rgb(255, 255, 0)'", element); 
    }

    private void applyBorderStyle(String borderStyle, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border = '" + borderStyle + "'", element);
        try {
            Thread.sleep(50); 
        } catch (InterruptedException e) {}
    }

    // --- SCROLLING & OTHER JS METHODS ---
    public void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollPageByPixels(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + pixels + ")");
    }

    public void clickElementByJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public String getTitleByJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript("return document.title;").toString();
    }

    public void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {}
    }

    // ============================================================
    // --- NEW HYBRID METHODS ADDED BELOW (Unchanged original code above) ---
    // ============================================================

    public void refreshBrowserByJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(0)");
    }

    public void scrollPageDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollPageUp() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
    }

    public String getPageInnerText() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript("return document.documentElement.innerText;").toString();
    }
    
    public void generateAlert(String message) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("alert('" + message + "')");
    }
}