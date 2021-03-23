package serenity.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;

public class LoginPage extends PageObject {

    // Locators
    @FindBy(xpath = "//input[@type='text']")
    public WebElement loginField;

    @FindBy(xpath = "//input[@type='password']")
    public WebElement passwordField;

    @FindBy(xpath = "//*[@class='error-message']")
    public WebElement error;
}
