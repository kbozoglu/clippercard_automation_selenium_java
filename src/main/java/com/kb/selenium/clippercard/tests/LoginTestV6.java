package com.kb.selenium.clippercard.tests;

import com.kb.selenium.clippercard.ClipperCardConstants;
import com.kb.selenium.clippercard.dataProvider.loginUser.LoginUserDataProvider;
import com.kb.selenium.clippercard.dataProvider.loginUser.LoginUserExcellDataProvider;
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

import java.util.List;

/**
 * Created by kbozoglu on 06.07.2015.
 */
public class LoginTestV6 {
    private static Logger logger = Logger.getLogger(LoginTestV6.class);

    private static final String LOGIN_FAIL_MSG = "Invalid Credentials";

    private WebDriver webDriver;

    @BeforeMethod
    public void beforeMethod(){
        logger.info("Opening browser");
        webDriver = new FirefoxDriver();
        webDriver.get(ClipperCardConstants.CLIPPER_CARD_MAIN_PAGE);
    }

    @DataProvider(name = "loginTestUsersExcellDataProvider")
    public Object[][] getLoginTestUsersFromExcell() {
        LoginUserDataProvider dataProvider = new LoginUserExcellDataProvider();
        List<LoginUser> testUsers = dataProvider.getTestLoginUsers();

        Object[][] data = new Object[testUsers.size()][1];

        int i = 0;
        for(LoginUser user : testUsers){
            data[i][0] = user;
            i++;
        }

        return data;
    }

    @Test(dataProvider = "loginTestUsersExcellDataProvider")
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
