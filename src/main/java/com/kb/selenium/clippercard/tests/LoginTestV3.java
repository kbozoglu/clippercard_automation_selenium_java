package com.kb.selenium.clippercard.tests;

import com.kb.selenium.clippercard.ClipperCardConstants;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by kbozoglu on 06.07.2015.
 */
public class LoginTestV3 {
    private static Logger logger = Logger.getLogger(LoginTestV3.class);

    private static final String LOGIN_FAIL_MSG = "Invalid Credentials";

    private WebDriver webDriver;

    @BeforeMethod
    public void beforeMethod(){
        logger.info("Opening browser");
        webDriver = new FirefoxDriver();
        webDriver.get(ClipperCardConstants.CLIPPER_CARD_MAIN_PAGE);
    }

    @Test
    public void testLogin1() throws InterruptedException {
        logger.info("Start running testLogin1");
        testInternalLogin("kevs.test@gmail.com", "TestClipper123", false);
    }

    @Test
    public void testLogin2() throws InterruptedException {
        logger.info("Start running testLogin2");
        testInternalLogin("kevser.test@gmail.com", "TestClipper123", true);
    }

    @AfterMethod
    public void afterMethod(){
        logger.info("Quiting browser");
        webDriver.quit();
    }

    private void testInternalLogin(String username, String password, boolean success){
        logger.info("Will try to login with user : " + username);

        WebElement loginRolloverButton = webDriver.findElement(By.id("mytranslinkModuleRollover"));
        loginRolloverButton.click();

        webDriver.switchTo().frame("headeriFrame");

        WebElement usernameTextBox = webDriver.findElement(By.cssSelector("input[id$=':username'][class='login']"));
        usernameTextBox.sendKeys(username);

        WebElement passWordTextBox = webDriver.findElement(By.cssSelector("input[id$=':password'][class='login']"));
        passWordTextBox.sendKeys(password);

        WebElement submitButton = webDriver.findElement(By.cssSelector("input[id$=':submitLogin']"));
        submitButton.click();

        if(success){
            WebDriverWait waitForMyClipperPage = new WebDriverWait(webDriver, ClipperCardConstants.DEFAULT_WAIT_TIME);
            waitForMyClipperPage.until(ExpectedConditions.titleContains("My Clipper"));
            logger.info("Successfully logged in");
        }
        else{
            By errorElementBy = By.cssSelector("span[id$=':err'] div span");
            WebDriverWait waitForLoginResponse = new WebDriverWait(webDriver, ClipperCardConstants.DEFAULT_WAIT_TIME);
            waitForLoginResponse.until(ExpectedConditions.visibilityOfElementLocated(errorElementBy));

            WebElement errorElement = webDriver.findElement(errorElementBy);
            Assert.assertEquals(errorElement.getText(), LOGIN_FAIL_MSG);

            logger.info("Login failure");
        }
    }
}
