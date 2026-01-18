package com.qa.tests;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.base.BaseTest;
import com.qa.pages.AdminPage;
import com.qa.pages.LoginPage;
import com.qa.utils.ExcelUtil;

public class AdminTest extends BaseTest {

    @DataProvider
    public Object[][] getSearchData() {
        // Uses the sheet name "Users" from your Excel file
        return ExcelUtil.getTestData("Users"); 
    }

    @Test(priority = 4, dataProvider = "getSearchData")
    public void verifyAdminSearch(String userNameToSearch) {
        // 1. Validation: Prevent NULL errors in Jenkins
        if (userNameToSearch == null || userNameToSearch.isEmpty()) {
            throw new SkipException("Skipping: Excel row data is empty.");
        }

        LoginPage loginPage = new LoginPage(driver);
        AdminPage adminPage = new AdminPage(driver);

        // 2. Login: Now works because we added keys to config.properties
        loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
        System.out.println("Logged in for user search: " + userNameToSearch);
        
        // 3. Action: Navigate and Search
        elementUtils.doClick(adminPage.adminMenu);
        
        // 4: Perform Search
        elementUtils.doSendKeys(adminPage.usernameSearchField, userNameToSearch); 
        elementUtils.doClick(adminPage.searchButton);
        System.out.println("Search performed for: " + userNameToSearch);

        // 5. Stability
        elementUtils.scrollDown(500); 
        jsUtils.pause(2000); 

        // 6. Verification
        boolean isUserFound = elementUtils.isElementDisplayed(adminPage.userResultRecord);
        // This will appear clearly in your Extent Report
        if(isUserFound) {
            System.out.println("PASS: User found in table.");
        } else {
            System.out.println("FAIL: User not found in table.");
        }
        
        verificationUtils.verifyCondition(isUserFound, "User " + userNameToSearch + " not found!");
        verificationUtils.assertAll();
    }
}