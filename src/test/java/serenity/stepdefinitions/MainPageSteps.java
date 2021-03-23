package serenity.stepdefinitions;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import serenity.pages.*;
import serenity.user.*;

import java.util.List;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class MainPageSteps {

    @Steps
    Admin admin;
    CurrentPage currentPage;
    MainPage mainPage;

    @И("админ кликает ссылку {string}")
    public void adminClickLink(String linkText) {
        currentPage.linkWithText(linkText).click();
    }

    @Тогда("админ видит уведомление {string}")
    public void adminAbbleToSeeNotification(String text) {
        admin.shouldSeeNotificationWithText(text);
    }

    @И("админ кликает кнопку {string}")
    public void adminClickButton(String buttonText) {
        admin.clickButtonWithText(buttonText);
    }

    @Тогда("админ видит в таблице {string}")
    public void adminAbbleToSeeTextInTable(String text) {
        adminScrollDownTable();
        admin.ableToSeeTextInTable(text);
    }

    @И("админ добавляет фильтр {string} со значением {string}")
    public void adminAddFilterWithValue(String filterName, String value) {
        admin.clickElement(mainPage.addFilterButton);
        admin.clickElement(mainPage.filter(filterName));
        admin.enterValueToField(value, mainPage.filterInput(filterName));
        admin.clickElement(mainPage.checkboxWrapperOverlay);
    }

    @И("админ прокручивает таблицу вниз")
    public void adminScrollDownTable() {
        List<WebElement> tableRows = mainPage.table.findElements(By.xpath(".//div[@class='data-table__row']"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(tableRows.get(tableRows.size() - 1)).build().perform();
    }

    @Тогда("админ не видит в таблице {string}")
    public void adminUnableToSeeTextInTable(String text) {
        admin.unableToSeeTextInTable(text);
    }

    @И("админ загружает изображение {string}")
    public void adminUploadFile(String fileName) {
        admin.uploadFileToFileInput(fileName);
    }

    @И("админ выделяет QR код на странице Расследования")
    public void adminCropCRCodeOnPage() {
        admin.cropQRCodeOnPage();
    }

    @Тогда("админ видит распознанный текст {string} QR изображения")
    public void adminAbbleToSeeQRCodeText(String text) {
        admin.abbleToSeeTextInElement(text, mainPage.investigationDecodedText);
    }

    /*@И("админ переходит на страницу {string}")
    public void adminGoToPage(String pagePath) {
        String[] links = pagePath.split(" - ");
        for (String lnk : links){
            admin.clickElement(currentPage.linkWithText(lnk));
        }
    }*/
}
