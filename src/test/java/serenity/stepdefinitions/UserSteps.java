package serenity.stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import net.thucydides.core.annotations.Steps;
import serenity.pages.*;
import serenity.user.*;

public class UserSteps {
    @Steps
    Admin admin;
    CurrentPage currentPage;
    MainPage mainPage;

    @Когда("Админ выбирает пользователя {string} и нажимает кнопку {string}")
    public void adminClickButtonOnRow(String userName, String buttonName) {
        admin.adminClickButtonOnRow(userName, buttonName);
    }

    @Дано("В системе отсутствует пользователь {string}")
    public void userIsNotExist(String userName) {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Пользователи"));
        admin.clickElement(currentPage.linkWithText("Пользователи"));

        admin.addFilter("По логину", userName);

        if (mainPage.table.getText().contains(userName)) {
            adminClickButtonOnRow(userName, "Удалить");
            admin.clickButtonWithText("Удалить");
        }
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Когда("админ добавляет нового пользователя с логином {string}")
    public void adminAddNewUser(String userLogin) throws InterruptedException {
        Thread.sleep(1000);
        admin.clickElement(mainPage.buttonsAboveTable.get(0));
        adminFillUserSettingsForm(userLogin);
    }

    @И("админ заполняет форму Параметры пользователя с логином {string}")
    public void adminFillUserSettingsForm(String userLogin) {
        Faker faker = new Faker();
        String userName = faker.name().fullName();
        String userEmail = faker.internet().emailAddress();

        admin.enterValueToField(userLogin, mainPage.fieldWithLabel("Логин"));
        admin.enterValueToField(userName, mainPage.fieldWithLabel("Имя"));
        admin.enterValueToField(userEmail, mainPage.emailField);
        admin.clickButtonWithText("Сохранить");
    }

    @Дано("В системе существует пользователь {string}")
    public void userIsExist(String userLogin) throws InterruptedException {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Пользователи"));
        admin.clickElement(currentPage.linkWithText("Пользователи"));

        admin.addFilter("По логину", userLogin);

        if (!mainPage.table.getText().contains(userLogin)) {
            adminAddNewUser(userLogin);
        }
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Дано("В системе отсутствует группа пользователей {string}")
    public void groupOfUsersIsNotExist(String groupName) {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Пользователи"));
        admin.clickElement(currentPage.linkWithText("Группы"));

        admin.addFilter("По имени", groupName);

        if (mainPage.table.getText().contains(groupName)) {
            adminClickButtonOnRow(groupName, "Удалить");
            admin.clickButtonWithText("Удалить");
        }

        // Ensure that group is deleted
        admin.unableToSeeTextInTable(groupName);

        // clear filter
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Когда("админ добавляет новую группу пользователей {string}")
    public void adminAddNewGroupOfUsers(String groupName) throws InterruptedException {
        Thread.sleep(1000);
        admin.clickElement(mainPage.buttonsAboveTable.get(0));
        adminFillGroupSettingsForm(groupName);
    }

    @Дано("В системе существует группа пользователей {string}")
    public void groupOfUsersIsExists(String groupName) {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Пользователи"));
        admin.clickElement(currentPage.linkWithText("Группы"));

        admin.addFilter("По имени", groupName);

        if (!mainPage.table.getText().contains(groupName)) {
            adminFillGroupSettingsForm(groupName);
        }
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Когда("Админ выбирает группу пользователей {string} и нажимает кнопку {string}")
    public void adminSelectGroupAndClickButton(String groupName, String buttonName) {
        admin.adminClickButtonOnRow(groupName, buttonName);
    }

    @И("админ заполняет форму Параметры группы с наименованием {string}")
    public void adminFillGroupSettingsForm(String groupName) {
        admin.enterValueToField(groupName, mainPage.fieldWithLabel("Наименование"));
        admin.enterValueToField("0", mainPage.fieldWithLabel("Первоначальный взнос"));
        admin.enterValueToField("0", mainPage.fieldWithLabel("Размер начисления"));
        admin.clickElement(mainPage.fieldWithLabel("Период"));
        admin.clickElement(mainPage.valueInGroupPeriod("Ежемесячно"));

        admin.clickButtonWithText("Сохранить");
    }

    @Дано("пользователь {string} отсутствует в списке при отключении синхронизации каталогов {string}")
    public void userIsNotShownInListIfDisabledSyncFromFolders(String userLogin, String foldersList) {
        admin.doLogin();

        try {
            Thread.sleep(1000); // wait for menu animation
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        admin.clickElement(mainPage.buttonSyncFromFolder());

        String[] folders = foldersList.split(", ");
        // Disable checkboxes for folders
        for (String folder : folders) {
            admin.enterValueToField(folder, mainPage.fieldWithLabel("Поиск"));
            if (mainPage.checkboxWithText(folder).getAttribute("class").contains("theme--checked")) {
                admin.clickElement(mainPage.checkboxWithText(folder));
            }
        }
        admin.clickButtonWithText("Сохранить");

        admin.addFilter("По логину", userLogin);
        admin.unableToSeeTextInTable(userLogin);
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Когда("админ включает синхронизацию по каталогам {string}")
    public void adminEnableSyncFromFolders(String foldersList) throws InterruptedException {
        Thread.sleep(1000); // wait for menu animation
        admin.clickElement(mainPage.buttonSyncFromFolder());

        String[] folders = foldersList.split(", ");
        // Enable checkboxes for folders
        for (String folder : folders) {
            admin.enterValueToField(folder, mainPage.fieldWithLabel("Поиск"));
            if (!mainPage.checkboxWithText(folder).getAttribute("class").contains("theme--checked")) {
                admin.clickElement(mainPage.checkboxWithText(folder));
            }
        }
        admin.clickButtonWithText("Сохранить");
    }
}
