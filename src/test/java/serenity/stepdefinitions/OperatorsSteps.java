package serenity.stepdefinitions;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.Keys;
import serenity.pages.*;
import serenity.user.*;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class OperatorsSteps {
    @Steps
    Admin admin;
    CurrentPage currentPage;
    MainPage mainPage;


    @Дано("в системе отсутствует оператор {string}")
    public void operatorIsNotExist(String operatorLogin) {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Настройки"));
        admin.clickElement(currentPage.linkWithText("Администрирование"));
        admin.clickElement(currentPage.linkWithText("Операторы"));

        if (mainPage.table.getText().contains(operatorLogin)) {
            admin.adminClickButtonOnRow(operatorLogin, "Удалить");
            admin.clickButtonWithText("Удалить");
        }
    }

    @Когда("админ добавляет нового оператора с логином {string} и почтой {string}")
    public void adminAddNewOperator(String operatorLogin, String operatorEmail) {
        admin.clickElement(mainPage.buttonAddNew());
        admin.enterValueToField(operatorLogin, mainPage.fieldWithLabel("Логин"));
        adminFillFormOfOperatorSettings(operatorLogin, operatorEmail);
    }

    @Дано("в системе существует оператор {string}")
    public void operatorIsExist(String operatorLogin) {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Настройки"));
        admin.clickElement(currentPage.linkWithText("Администрирование"));
        admin.clickElement(currentPage.linkWithText("Операторы"));

        if (!mainPage.table.getText().contains(operatorLogin)) {
            adminAddNewOperator(operatorLogin, "1");
        }
    }

    @Когда("админ выбирает оператора {string} и кликает кнопку {string}")
    public void adminSelectOperatorAndClickButton(String operatorLogin, String buttonName) {
        admin.adminClickButtonOnRow(operatorLogin, buttonName);
    }

    @И("админ заполняет форму Параметры оператора с логином {string} и почтой {string}")
    public void adminFillFormOfOperatorSettings(String operatorLogin, String operatorEmail) {
        admin.enterValueToField("1", mainPage.fieldWithLabel("Пароль"));
        admin.enterValueToField(operatorEmail, mainPage.fieldWithLabel("Почта"));

        getDriver().switchTo().activeElement().sendKeys(Keys.TAB);
        admin.clickElement(mainPage.firstOptionFromDropDownMenu());

        admin.clickButtonWithText("Сохранить");
    }
}
