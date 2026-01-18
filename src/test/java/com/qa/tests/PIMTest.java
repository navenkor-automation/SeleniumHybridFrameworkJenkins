package com.qa.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.base.BaseTest;
import com.qa.pages.PIMPage;
import com.qa.utils.ExcelUtil;

public class PIMTest extends BaseTest {

    @DataProvider
    public Object[][] getEmployeeData() {
        // Fetches data from "EmployeeSheet" in your Excel file
        return ExcelUtil.getTestData("EmployeeSheet");
    }

    @Test(dataProvider = "getEmployeeData")
    public void addMultipleEmployeesTest(String fName, String lName, String empId) {
        PIMPage pimPage = new PIMPage(driver);
        pimPage.navigateToAddEmployee();
        
        // Data comes from Excel now, not hardcoded!
        pimPage.createEmployee(fName, lName, empId, "src/test/resources/photo.png");
    }
}