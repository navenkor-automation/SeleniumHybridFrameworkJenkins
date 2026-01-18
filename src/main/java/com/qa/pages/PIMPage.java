package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.qa.utils.ElementUtils;

public class PIMPage {
    private WebDriver driver;
    private ElementUtils elementUtils;

    // 1. Locators (By ORs)
    private By pimMenu = By.xpath("//span[text()='PIM']");
    private By addEmployeeBtn = By.xpath("//a[text()='Add Employee']");
    private By firstName = By.name("firstName");
    private By lastName = By.name("lastName");
    private By employeeId = By.xpath("//label[text()='Employee Id']/../../div[2]/input");
    private By imageUpload = By.xpath("//input[@type='file']");
    private By saveButton = By.xpath("//button[@type='submit']");
    
    // Locators for Personal Details (After saving)
    private By nationalityDropdown = By.xpath("//label[text()='Nationality']/../../div[2]//div[@class='oxd-select-text-input']");
    private By allNationalityOptions = By.xpath("//div[@role='listbox']//span");
    private By dateOfBirth = By.xpath("//label[text()='Date of Birth']/../../div[2]//input");

    // 2. Constructor
    public PIMPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new ElementUtils(driver);
    }

    // 3. Page Actions
    public void navigateToAddEmployee() {
        elementUtils.doClick(pimMenu);
        elementUtils.doClick(addEmployeeBtn);
    }

    public void createEmployee(String fName, String lName, String eId, String filePath) {
        elementUtils.doSendKeys(firstName, fName);
        elementUtils.doSendKeys(lastName, lName);
        elementUtils.doSendKeys(employeeId, eId);
        elementUtils.doUploadFile(imageUpload, filePath); // Using your Upload Utility
        elementUtils.doClick(saveButton);
    }

    public void fillPersonalDetails(String nationality, String dob) {
        // Handling the Custom Dropdown
        elementUtils.doClick(nationalityDropdown);
        elementUtils.doSelectValueFromDropdown(allNationalityOptions, nationality);
        
        // Handling the Date Picker
        elementUtils.selectDate(dateOfBirth, dob);
        elementUtils.doClick(saveButton);
    }
}