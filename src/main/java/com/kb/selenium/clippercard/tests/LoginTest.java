package com.kb.selenium.clippercard.tests;

import com.kb.selenium.clippercard.ClipperCardConstants;
import com.kb.selenium.clippercard.dataProvider.sql.LoginTestDao;
import com.kb.selenium.clippercard.dataProvider.sql.UserDao;
import com.kb.selenium.clippercard.model.User;
import com.kb.selenium.clippercard.model.testcase.LoginTestCase;
import com.kb.selenium.clippercard.pageObjects.LoginElements;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by kbozoglu on 06.07.2015.
 */
public class LoginTest {
    private static Logger logger = Logger.getLogger(LoginTest.class);

    private static final String LOGIN_FAIL_MSG = "Invalid Credentials";

    private WebDriver webDriver;

    @BeforeClass
    public void beforeClass(){
        UserDao.findAllUsers();
    }

    @BeforeMethod
    public void beforeMethod(){
        logger.info("Opening browser");
        webDriver = new FirefoxDriver();
        webDriver.get(ClipperCardConstants.CLIPPER_CARD_MAIN_PAGE);
    }

    @DataProvider(name = "loginTestSqlDataProvider")
    public Object[][] getLoginTestCases() {
        List<LoginTestCase> loginTestCases = LoginTestDao.getAllTestCases();

        Object[][] data = new Object[loginTestCases.size()][1];

        int i = 0;
//        for(LoginTestCase testCase : loginTestCases){
//            data[i][0] = testCase;
//            i++;
//        }
        Iterator<LoginTestCase> iter = loginTestCases.iterator();
        while(iter.hasNext()) {
            data[i][0]= iter.next();
            i++;
        }

        return data;
    }

    @Test(dataProvider = "loginTestSqlDataProvider")
    public void testLogin(LoginTestCase testCase) throws InterruptedException {
        Long loginTestResultId = LoginTestDao.insertInitialTestResult(testCase);
        try{
            User user = testCase.getUser();
            logger.info("Will try to login with user : " + user.getFirstName() + " " + user.getLastName());

            LoginElements loginElements = new LoginElements();
            loginElements.init(webDriver);

            loginElements.loginRolloverButton.click();

            webDriver.switchTo().frame("headeriFrame");

            loginElements.usernameTextBox.sendKeys(user.getEmail());
            loginElements.passWordTextBox.sendKeys(user.getPassword());
            loginElements.submitButton.click();

            if(testCase.isSuccess()){
                WebDriverWait waitForMyClipperPage = new WebDriverWait(webDriver, ClipperCardConstants.DEFAULT_WAIT_TIME);
                waitForMyClipperPage.until(ExpectedConditions.titleContains("My Clipper"));
                logger.info("Successfully logged in");
            }
            else{
                By errorElementBy = By.cssSelector("span[id$=':err'] div span");
                WebDriverWait waitForLoginResponse = new WebDriverWait(webDriver, ClipperCardConstants.DEFAULT_WAIT_TIME);
                waitForLoginResponse.until(ExpectedConditions.visibilityOfElementLocated(errorElementBy));

                Assert.assertEquals(loginElements.errorSpan.getText(), LOGIN_FAIL_MSG);

                logger.info("Login failure");
            }

            LoginTestDao.updateTestResult(loginTestResultId, true);
        }
        catch (Exception e){
            LoginTestDao.updateTestResult(loginTestResultId, false);
        }
    }

    @AfterMethod
    public void afterMethod(){
        logger.info("Quiting browser");
        webDriver.quit();
    }
}
