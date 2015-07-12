package com.kb.selenium.clippercard.tests;
import com.kb.selenium.clippercard.dataProvider.mainPageUrls.NavigationMenuExcellDataProvider;
import com.kb.selenium.clippercard.ClipperCardConstants;
import com.kb.selenium.clippercard.dataProvider.mainPageUrls.ParticipatingAgencyExcellDataProvider;
import com.kb.selenium.clippercard.model.NavigationMenuItem;
import com.kb.selenium.clippercard.model.ParticipatingAgency;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by kbozoglu on 08.07.2015.
 */
public class MainPageUrlsTest {

    private WebDriver webDriver;

    @BeforeMethod
    public void beforeMethod() {
        webDriver = new FirefoxDriver();
        webDriver.get(ClipperCardConstants.CLIPPER_CARD_MAIN_PAGE);
    }
    @AfterMethod
    public void afterMethod() {
        webDriver.quit();
    }

    @Test(priority = 1)
    public void testTopNavigationLinks() {
        List<NavigationMenuItem> menuItemList = NavigationMenuExcellDataProvider.getTopNavigationMenu();

        for (NavigationMenuItem menuItem : menuItemList) {
            WebElement menuLink = webDriver.findElement(By.cssSelector("#topNav #" + menuItem.getId() + " a"));
            menuLink.click();
//            new WebDriverWait(webDriver, ClipperCardConstants.DEFAULT_WAIT_TIME)
//                    .until(ExpectedConditions.titleIs(menuItem.getTargetPageTitle()));

            Assert.assertEquals(webDriver.getTitle(), menuItem.getTargetPageTitle());
        }
    }
    @Test(priority = 2)
    public void testParticipatingAgenciesLink() {
        //webDriver.get(ClipperCardConstants.CLIPPER_CARD_MAIN_PAGE);

        List<ParticipatingAgency> participatingAgencyList = ParticipatingAgencyExcellDataProvider.getFromExcel();
        for (ParticipatingAgency participatingAgency : participatingAgencyList) {
            WebElement agencyIcon = webDriver.findElement(By.cssSelector("img[alt='" + participatingAgency.getIconAltText() + "']"));

            agencyIcon.click();

            Assert.assertEquals(webDriver.getTitle(), participatingAgency.getTargetPageTitle());
//            new WebDriverWait(webDriver, ClipperCardConstants.DEFAULT_WAIT_TIME)
//                    .until(ExpectedConditions.titleIs(participatingAgency.getTargetPageTitle()));

            webDriver.navigate().back();

            new WebDriverWait(webDriver, ClipperCardConstants.DEFAULT_WAIT_TIME)
                    .until(ExpectedConditions.titleIs(ClipperCardConstants.CLIPPER_CARD_MAIN_PAGE_TITLE));
        }

    }
}