package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage {
    WebDriver driver;

    public By adminMenu = By.xpath("//span[text()='Admin']");
    public By usernameSearchField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
    public By searchButton = By.xpath("//button[@type='submit']");
    public By userHeader = By.xpath("//h6[contains(.,'User Management')]");
    
    // ADD THIS LINE TO FIX THE ADMIN TEST ERROR
    public By userResultRecord = By.xpath("//div[@class='oxd-table-card']");

    public AdminPage(WebDriver driver) {
        this.driver = driver;
    }

}