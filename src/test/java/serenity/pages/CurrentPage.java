package serenity.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CurrentPage extends PageObject {
    public WebElement buttonWithText(String text) {
        return $("//button[contains(., '" + text + "')]");
    }

    public WebElement linkWithText(String text) {
        return $(By.linkText(text));
    }

    @FindBy(xpath = "//div[contains(@class, 'theme--circular')]")
    public WebElement loader;
}
