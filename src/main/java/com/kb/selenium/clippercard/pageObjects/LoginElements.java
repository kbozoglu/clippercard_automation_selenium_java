package com.kb.selenium.clippercard.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by kbozoglu on 07.07.2015.
 */
public class LoginElements {
    private WebDriver driver;

    public void init(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.ID, using = "mytranslinkModuleRollover")
    public WebElement loginRolloverButton;

    @FindBy(how = How.CSS, using = "input[id$=':username'][class='login']")
    public WebElement usernameTextBox;

    @FindBy(how = How.CSS, using = "input[id$=':password'][class='login']")
    public WebElement passWordTextBox;

    @FindBy(how = How.CSS, using = "input[id$=':submitLogin']")
    public WebElement submitButton;

    @FindBy(how = How.CSS, using = "span[id$=':err'] div span")
    public WebElement errorSpan;
}
