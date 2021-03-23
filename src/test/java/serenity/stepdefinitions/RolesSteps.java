package serenity.stepdefinitions;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.Keys;
import serenity.pages.*;
import serenity.user.*;

public class RolesSteps {
    @Steps
    Admin admin;
    CurrentPage currentPage;
    MainPage mainPage;

    @Дано("в системе отсутствует роль {string}")
    public void roleIsNotExist(String roleName) {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Настройки"));
        admin.clickElement(currentPage.linkWithText("Администрирование"));
        admin.clickElement(currentPage.linkWithText("Роли"));

        if (mainPage.table.getText().contains(roleName)) {
            admin.adminClickButtonOnRow(roleName, "Удалить");
            admin.clickButtonWithText("Удалить");
            // Если появляется уведомление о том что есть операторы с этой ролью
            try {
                admin.clickButtonWithText("Удалить");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Когда("админ добавляет новую роль {string}")
    public void adminAddNewRole(String roleName) {
        admin.clickElement(mainPage.buttonAddNew());
        adminFillFormOfRolesSettings(roleName);
    }

    @Дано("в системе существует роль {string}")
    public void theRoleIsExist(String roleName) {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Настройки"));
        admin.clickElement(currentPage.linkWithText("Администрирование"));
        admin.clickElement(currentPage.linkWithText("Роли"));

        if (!mainPage.table.getText().contains(roleName)) {
            adminAddNewRole(roleName);
        }
    }

    @Когда("админ выбират роль {string} и кликает кнопку {string}")
    public void adminSelectRoleAndClickButton(String roleName, String buttonName) {
        admin.adminClickButtonOnRow(roleName, buttonName);
    }

    @И("админ заполняет форму Параметры роли с названием {string}")
    public void adminFillFormOfRolesSettings(String roleName) {
        admin.enterValueToField(roleName, mainPage.fieldWithLabel("Название роли"));

        // Костыль: Переключаюсь в поле Полномочие клавишей Таб, потому что не получилось найти
        // элемент в поле который реагирует на клик. Если кликать на input то пишет element not intractable
        currentPage.getDriver().switchTo().activeElement().sendKeys(Keys.TAB);
        currentPage.getDriver().switchTo().activeElement().sendKeys("Testing...");
        currentPage.getDriver().switchTo().activeElement().sendKeys(Keys.TAB);
        admin.enterValueToField("Отчеты", mainPage.fieldWithLabel("Полномочия"));
        mainPage.fieldWithLabel("Полномочия").sendKeys(Keys.ENTER);

        admin.clickButtonWithText("Сохранить");
    }
}
