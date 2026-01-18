package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.qa.utils.ElementUtils;

public class LoginPage {
    private WebDriver driver;
    private ElementUtils elementUtils;

    private By username = By.name("username");
    private By password = By.name("password");
    private By loginBtn = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtils = new ElementUtils(this.driver);
    }

    public void doLogin(String un, String pwd) {
        if (un == null || pwd == null) {
            System.out.println("CRITICAL: Login skipped - credentials are NULL. Check config.properties.");
            return;
        }
        elementUtils.doSendKeys(username, un);
        elementUtils.doSendKeys(password, pwd);
        elementUtils.doClick(loginBtn);
    }
}