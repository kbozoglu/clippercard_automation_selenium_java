package com.kb.selenium.clippercard.tests;

import com.kb.selenium.clippercard.ClipperCardConstants;
import com.kb.selenium.clippercard.dataProvider.loginUser.LoginUserDataProvider;
import com.kb.selenium.clippercard.dataProvider.loginUser.LoginUserExcellDataProvider;
import com.kb.selenium.clippercard.model.LoginUser;
import com.kb.selenium.clippercard.pageObjects.LoginElements;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by kbozoglu on 06.07.2015.
 */
public class LoginTestV8 {
    private static Logger logger = Logger.getLogger(LoginTestV8.class);

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

        LoginElements loginElements = new LoginElements();
        loginElements.init(webDriver);

        loginElements.loginRolloverButton.click();

        webDriver.switchTo().frame("headeriFrame");

        loginElements.usernameTextBox.sendKeys(user.getUsername());
        loginElements.passWordTextBox.sendKeys(user.getPassword());
        loginElements.submitButton.click();

        if(user.isSuccess()){
            WebDriverWait waitForMyClipperPage = new WebDriverWait(webDriver, ClipperCardConstants.DEFAULT_WAIT_TIME);
            waitForMyClipperPage.until(ExpectedConditions.titleContains("My Clipper"));
            logger.info("Successfully logged in");
        }
        else{
            By errorElementBy = By.cssSelector("span[id$=':err'] div span");
            WebDriverWait waitForLoginResponse = new WebDriverWait(webDriver, ClipperCardConstants.DEFAULT_WAIT_TIME);
            waitForLoginResponse.until(ExpectedConditions.visibilityOfElementLocated(errorElementBy));

            Assert.assertEquals(loginElements.errorSpan.getText(), LOGIN_FAIL_MSG + "Force Fail");

            logger.info("Login failure");
        }
    }

    @AfterMethod
    public void afterMethod(ITestResult testResult){
        try {
            if (testResult.isSuccess() == false) {
                File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(ClipperCardConstants.SCREEN_SHOTS_DIR + "loginFailed" + System.currentTimeMillis() + ".jpg"));
            }
        }
        catch (IOException e) {
           logger.error("Error while taking screen shot", e);
        }
        finally {
            webDriver.quit();
        }
    }
}
