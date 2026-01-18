package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.qa.utils.ElementUtils;

public class CommonElementsPage {
    private WebDriver driver;
    private ElementUtils elementUtils;

    // --- PAGINATION LOCATORS ---
    // These are standard across OrangeHRM Admin, PIM, and Recruitment modules
    public By tableRows = By.xpath("//div[@class='oxd-table-card']");
    public By nextButton = By.xpath("//button/i[@class='oxd-icon bi-chevron-right']/..");
    public By previousButton = By.xpath("//button/i[@class='oxd-icon bi-chevron-left']/..");
    
    // --- DRAG AND DROP LOCATORS ---
    public By source = By.id("draggable");
    public By target = By.id("droppable");

    public CommonElementsPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new ElementUtils(this.driver);
    }

    /**
     * Reusable method to find a specific record across all pages of a table.
     * Uses the logic we built in ElementUtils.
     */
    public boolean searchRecordGlobally(String recordName) {
        return elementUtils.selectRecordFromPaginationTable(nextButton, tableRows, recordName);
    }
}