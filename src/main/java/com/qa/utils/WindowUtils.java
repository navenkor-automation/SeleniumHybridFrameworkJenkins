package com.qa.utils;

import org.openqa.selenium.WebDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WindowUtils {
    private WebDriver driver;

    public WindowUtils(WebDriver driver) {
        this.driver = driver;
    }

    // --- YOUR PREVIOUS METHODS ---
    public void switchToChildWindow() {
        String parentId = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(parentId)) {
                driver.switchTo().window(handle);
            }
        }
    }

    public void switchToParentWindow(String parentId) {
        driver.switchTo().window(parentId);
    }

    // --- UPDATED METHODS TO MATCH YOUR TEST CASE ---
    
    /**
     * Matches the call in WindowPaginationTest.java: line 16
     * Switches to a tab based on index (0-based)
     */
    public void switchToTab(int index) {
        Set<String> handles = driver.getWindowHandles();
        List<String> handlesList = new ArrayList<>(handles);
        if (index >= 0 && index < handlesList.size()) {
            driver.switchTo().window(handlesList.get(index));
        }
    }

    /**
     * Matches the call in WindowPaginationTest.java: line 21
     * Closes current tab and returns to parent
     */
    public void closeCurrentTabAndReturn(String parentId) {
        driver.close();
        driver.switchTo().window(parentId);
    }
}