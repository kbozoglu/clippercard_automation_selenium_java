package com.kb.selenium.clippercard.tests;

import com.kb.selenium.clippercard.ClipperCardConstants;
import com.kb.selenium.clippercard.model.LoginUser;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by kbozoglu on 06.07.2015.
 */
public class LoginTestV4 {
    private static Logger logger = Logger.getLogger(LoginTestV4.class);

    private static final String LOGIN_FAIL_MSG = "Invalid Credentials";

    private WebDriver webDriver;

    @BeforeMethod
    public void beforeMethod(){
        logger.info("Opening browser");
        webDriver = new FirefoxDriver();
        webDriver.get(ClipperCardConstants.CLIPPER_CARD_MAIN_PAGE);
    }

    @DataProvider(name = "loginTestUsersInMemoryDataProvider")
    public Object[][] getLoginTestUsersInMemory() {
        LoginUser user1 = new LoginUser("kevserbozoglu@gmail.com", "1234", false);
        LoginUser user2 = new LoginUser("kevser.test@gmail.com", "TestClipper123", true);

        Object[][] data = new Object[2][1];
        data[0][0] = user1;

        data[1][0] = user2;
        return data;
    }

    @Test(dataProvider = "loginTestUsersInMemoryDataProvider")
    public void testLogin(LoginUser user) throws InterruptedException {
        logger.info("Will try to login with user : " + user.getUsername());

        WebElement loginRolloverButton = webDriver.findElement(By.id("mytranslinkModuleRollover"));
        loginRolloverButton.click();

        webDriver.switchTo().frame("headeriFrame");

        WebElement usernameTextBox = webDriver.findElement(By.cssSelector("input[id$=':username'][class='login']"));
        usernameTextBox.sendKeys(user.getUsername());

        WebElement passWordTextBox = webDriver.findElement(By.cssSelector("input[id$=':password'][class='login']"));
        passWordTextBox.sendKeys(user.getPassword());

        WebElement submitButton = webDriver.findElement(By.cssSelector("input[id$=':submitLogin']"));
        submitButton.click();

        if(user.isSuccess()){
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


    @AfterMethod
    public void afterMethod(){
        logger.info("Quiting browser");
        webDriver.quit();
    }
}
