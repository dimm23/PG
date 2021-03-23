package serenity.stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.ru.*;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import serenity.pages.*;
import serenity.user.*;

import java.util.List;


public class PoliciesSteps {

    @Steps
    Admin admin;
    CurrentPage currentPage;
    MainPage mainPage;

    @И("админ заполняет форму Настройка времени запрета печати с именем {string}")
    public void fillTimePoliciesForm(String policyName) {
        Faker faker = new Faker();

        admin.enterValueToField(policyName, mainPage.fieldWithLabel("Наименование"));
        admin.enterValueToField(faker.harryPotter().spell(), mainPage.fieldWithLabel("Описание"));

        admin.clickElement(mainPage.fieldWithLabel("Дни печати"));
        if (!mainPage.dayOfWeekOrTime("пн").getAttribute("class").contains("select-time__item_active")) {
            admin.clickElement(mainPage.dayOfWeekOrTime("пн"));
        }

        if (!mainPage.dayOfWeekOrTime("вт").getAttribute("class").contains("select-time__item_active")) {
            admin.clickElement(mainPage.dayOfWeekOrTime("вт"));
        }

        if (!mainPage.dayOfWeekOrTime("ср").getAttribute("class").contains("select-time__item_active")) {
            admin.clickElement(mainPage.dayOfWeekOrTime("ср"));
        }

        if (!mainPage.dayOfWeekOrTime("чт").getAttribute("class").contains("select-time__item_active")) {
            admin.clickElement(mainPage.dayOfWeekOrTime("чт"));
        }

        if (!mainPage.dayOfWeekOrTime("пт").getAttribute("class").contains("select-time__item_active")) {
            admin.clickElement(mainPage.dayOfWeekOrTime("пт"));
        }
        // Close panel
        admin.clickElement(mainPage.$("//div[@class='select-time__overlay']"));

        admin.clickElement(mainPage.fieldWithLabel("Часы печати"));
        for (int i = 0;  i < 24; i++) {
            if (!mainPage.dayOfWeekOrTime(String.valueOf(i))
                    .getAttribute("class").contains("select-time__item_active")) {
                admin.clickElement(mainPage.dayOfWeekOrTime(String.valueOf(i)));
            }
        }

        // Close panel
        admin.clickElement(mainPage.$("//div[@class='select-time__overlay']"));

        admin.clickButtonWithText("Сохранить");
    }

    @Дано("В системе существует политика водяных знаков {string}")
    public void watermarkPolicyIsPresent(String policyName) throws InterruptedException {
        admin.doLogin();

        // Go to Watermark policies page
        admin.clickElement(currentPage.linkWithText("Политики"));
        admin.clickElement(currentPage.linkWithText("Водяные знаки"));
        admin.clickElement(currentPage.linkWithText("Политики водяных знаков"));

        // Create policy if it is not created
        if (!mainPage.table.getText().contains(policyName)) {
            Thread.sleep(1000);
            admin.clickElement(mainPage.buttonsAboveTable.get(0));

            adminFillFormOfWatermarkPolicies(policyName);
        }
    }

    @Когда("Админ выбирает политику {string} и нажимает кнопку {string}")
    public void adminClickButtonOnPolicy(String policyName, String buttonName) {
        admin.adminClickButtonOnRow(policyName, buttonName);
    }

    @Тогда("политика {string} удаляется из таблицы")
    public void adminUnableToSeePolicyInTable(String policyName) {
        admin.unableToSeeTextInTable(policyName);
    }

    @И("админ заполняет форму Параметры политики водяных знаков с именем {string}")
    public void adminFillFormOfWatermarkPolicies(String policyName) {
        Faker faker = new Faker();

        admin.enterValueToField(policyName, mainPage.fieldWithLabel("Наименование"));
        admin.enterValueToField(faker.harryPotter().spell(), mainPage.fieldWithLabel("Описание"));

        admin.clickButtonWithText("Сохранить");
    }

    @Когда("админ добавляет новую политику водяных знаков {string}")
    public void adminAddNewWaterpolicy(String policyName) throws InterruptedException {
        Thread.sleep(1000);
        admin.clickElement(mainPage.buttonsAboveTable.get(0));

        adminFillFormOfWatermarkPolicies(policyName);
    }

    @Дано("В системе отсутствует политика водяных знаков {string}")
    public void watermarkPolicyIsNotPresent(String policyName) {
        admin.doLogin();

        // Go to Watermark policies page
        admin.clickElement(currentPage.linkWithText("Политики"));
        admin.clickElement(currentPage.linkWithText("Водяные знаки"));
        admin.clickElement(currentPage.linkWithText("Политики водяных знаков"));

        admin.addFilter("По наименованию", policyName);

        // Delete policy if it is created
        if (mainPage.table.getText().contains(policyName)) {
            adminClickButtonOnPolicy(policyName, "Удалить");
            admin.clickButtonWithText("Удалить");
        }

        // Ensure that policy is deleted
        admin.unableToSeeTextInTable(policyName);

        // Clear filter
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Дано("В системе отсутствует временная политика {string}")
    public void timepolicyIsPresent(String policyName) {
        admin.doLogin();

        // Go to Time policies page
        admin.clickElement(currentPage.linkWithText("Политики"));
        admin.clickElement(currentPage.linkWithText("Временные политики"));

        admin.addFilter("По наименованию", policyName);

        // Delete policy if it is created
        if (mainPage.table.getText().contains(policyName)) {
            adminClickButtonOnPolicy(policyName, "Удалить");
            admin.clickButtonWithText("Удалить");
        }
        admin.unableToSeeTextInTable(policyName); // Ensure that policy is deleted
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Когда("админ добавляет новую временную политику {string}")
    public void adminAddNewTimePolicy(String policyName) throws InterruptedException {
        Thread.sleep(1000);
        admin.clickElement(mainPage.buttonsAboveTable.get(0));

        fillTimePoliciesForm(policyName);
    }

    @Дано("В системе существует временная политика {string}")
    public void timePolicyIsCreated(String policyName) throws InterruptedException {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Политики"));
        admin.clickElement(currentPage.linkWithText("Временные политики"));

        admin.addFilter("По наименованию", policyName);

        // Add new policy if it is not created
        if (!mainPage.table.getText().contains(policyName)) {
            adminAddNewTimePolicy(policyName);
        }
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Дано("В системе отсутствует политика теневых копий {string}")
    public void shadowPolicyIsNotExist(String policyName) {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Политики"));
        admin.clickElement(currentPage.linkWithText("Политики теневых копий"));

        admin.addFilter("По наименованию", policyName);

        // Delete policy if it is created
        if (mainPage.table.getText().contains(policyName)) {
            adminClickButtonOnPolicy(policyName, "Удалить");
            admin.clickButtonWithText("Удалить");
        }

        // Ensure that policy is deleted
        admin.unableToSeeTextInTable(policyName);

        // Clear filter
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Когда("админ добавляет новую политику теневых копий {string}")
    public void adminAddNewShadowPolicy(String policyName) throws InterruptedException {
        Thread.sleep(1000);
        admin.clickElement(mainPage.buttonsAboveTable.get(0));

        adminFillFormOfShadowPolicies(policyName);
    }

    @Дано("В системе существует политика теневых копий {string}")
    public void shadowPolicyIsExist(String policyName) throws InterruptedException {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Политики"));
        admin.clickElement(currentPage.linkWithText("Политики теневых копий"));

        admin.addFilter("По наименованию", policyName);

        // Add new policy if it is not created
        if (!mainPage.table.getText().contains(policyName)) {
            adminAddNewShadowPolicy(policyName);
        }
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @И("админ заполняет форму Параметры политики теневых копий с наименованием {string}")
    public void adminFillFormOfShadowPolicies(String policyName) {
        Faker faker = new Faker();

        admin.enterValueToField(policyName, mainPage.fieldWithLabel("Наименование"));
        admin.enterValueToField(faker.beer().name(), mainPage.fieldWithLabel("Описание"));

        admin.clickButtonWithText("Сохранить");
    }

    @Дано("В системе отсутствует ограничительная политика {string}")
    public void restrictionPolicyIsNotExist(String policyName) {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Политики"));
        admin.clickElement(currentPage.linkWithText("Ограничительные политики"));

        admin.addFilter("По наименованию", policyName);

        // Delete policy if it is created
        if (mainPage.table.getText().contains(policyName)) {
            adminClickButtonOnPolicy(policyName, "Удалить");
            admin.clickButtonWithText("Удалить");
        }

        // Ensure that policy is deleted
        admin.unableToSeeTextInTable(policyName);

        // Clear filter
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Когда("админ добавляет новую ограничительную политику {string}")
    public void adminAddNewRestrictionPolicy(String policyName) throws InterruptedException {
        Thread.sleep(1000);
        admin.clickElement(mainPage.buttonsAboveTable.get(0));

        adminFillFormOfRestrictionPolicies(policyName);
    }

    @Дано("В системе существует ограничительная политика {string}")
    public void restrictionPolicyIsExist(String policyName) throws InterruptedException {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Политики"));
        admin.clickElement(currentPage.linkWithText("Ограничительные политики"));

        admin.addFilter("По наименованию", policyName);

        // Add policy if it is not exist
        if (!mainPage.table.getText().contains(policyName)) {
            adminAddNewRestrictionPolicy(policyName);
        }
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @И("админ заполняет форму Параметры огранчительной политики с наименованием {string}")
    public void adminFillFormOfRestrictionPolicies(String policyName) {
        Faker faker = new Faker();

        admin.enterValueToField(policyName, mainPage.fieldWithLabel("Наименование"));
        admin.enterValueToField(faker.animal().name(), mainPage.fieldWithLabel("Описание"));
        admin.clickElement(mainPage.checkboxColorPrinting());
        admin.clickElement(mainPage.checkboxDoubleSidePrinting());
        admin.enterValueToField("10", mainPage.fieldWithLabel("Количество страниц"));

        admin.clickButtonWithText("Сохранить");
    }

    @Дано("В системе установлена запрещающая временная политика")
    public void printingIsDisabledByTimePolicy() throws InterruptedException {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Политики"));
        admin.clickElement(currentPage.linkWithText("Ограничительные политики"));
        admin.clickElement(mainPage.firstCellOfTableHeader);
        admin.disableAllPolicies();

        admin.clickElement(currentPage.linkWithText("Временные политики"));
        admin.clickElement(mainPage.firstCellOfTableHeader);
        admin.disableAllPolicies();

        if (mainPage.table.findElements(By.xpath(mainPage.tableRows)).size() == 0) {
            adminAddNewTimePolicy("NewTimePolicy");
        }
        admin.clickButtonOnLastRow("Редактировать");
        admin.clickElement(mainPage.filter("Запрет печати по времени"));

        admin.clickElement(mainPage.checkboxWithText("Активна"));
        fillTimePoliciesForm("DisablePrintingByTime");
    }

    @Тогда("админ на странице Журналы видит статус {string} отправленного на печать файла и в Деталях {string}")
    public void adminAbleToSeeOnJournalPageStatusAndDetails(String status, String details) {

        WebDriverWait wait = new WebDriverWait(admin.getDriver(), 30);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(mainPage.notification));
        } catch (Exception ex) {
            throw new NoSuchElementException("There is no Notification about new record in Journal", ex);
        }

        admin.clickElement(mainPage.notification);
        admin.waitForLoader();
        List<WebElement> tableRows = mainPage.table.findElements(By.xpath(mainPage.tableRows));
        admin.abbleToSeeTextInElement(status, tableRows.get(0));
        if (!details.equals("")) {
            admin.abbleToSeeTextInElement(details, tableRows.get(0));
        }
    }

    @Дано("в системе существует ограничительная политика с параметрами {string} {string} {string}")
    public void restrictionPolicyIsAbleWithParams(String color,
                                                  String duplex,
                                                  String pagesLimit) throws InterruptedException {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Политики"));
        admin.clickElement(currentPage.linkWithText("Временные политики"));
        admin.clickElement(mainPage.firstCellOfTableHeader);
        admin.disableAllPolicies();

        admin.clickElement(currentPage.linkWithText("Ограничительные политики"));
        admin.clickElement(mainPage.firstCellOfTableHeader);
        admin.disableAllPolicies();

        if (mainPage.table.findElements(By.xpath(mainPage.tableRows)).size() == 0) {
            adminAddNewRestrictionPolicy("NewRestrictionPolicy");
        }

        admin.clickButtonOnLastRow("Редактировать");
        Faker faker = new Faker();
        admin.enterValueToField(faker.app().name(), mainPage.fieldWithLabel("Наименование"));
        admin.enterValueToField(faker.aquaTeenHungerForce().character(), mainPage.fieldWithLabel("Описание"));
        admin.clickElement(mainPage.checkboxWithText("Активна"));

        if (color.equals("да")) {
            if (mainPage.checkboxWithText("Цветная печать").getAttribute("class").contains("checked")) {
                admin.clickElement(mainPage.checkboxColorPrinting());
            }
        } else {
            if (!mainPage.checkboxWithText("Цветная печать").getAttribute("class").contains("checked")) {
                admin.clickElement(mainPage.checkboxColorPrinting());
            }
        }

        if (duplex.equals("да")) {
            if (mainPage.checkboxDoubleSidePrinting().getAttribute("class").contains("checked")) {
                mainPage.checkboxDoubleSidePrinting().click();
            }
        } else {
            if (!mainPage.checkboxDoubleSidePrinting().getAttribute("class").contains("checked")) {
                mainPage.checkboxDoubleSidePrinting().click();
            }
        }

        admin.enterValueToField(pagesLimit, mainPage.fieldWithLabel("Количество страниц"));

        admin.clickButtonWithText("Сохранить");
    }

    @И("в системе установлена разрешающая временная политика")
    public void restrictionPolicyAvailableWithAccessToPrint() {
        admin.waitForLoader();
        admin.clickElement(mainPage.buttonsAboveTable.get(0));
        admin.clickElement(mainPage.checkboxWithText("Разрешение печати по времени"));
        Faker faker = new Faker();
        fillTimePoliciesForm(faker.funnyName().name());
    }

    @Дано("В системе существует активная политика теневых копий")
    public void activeShadowPolicyIsExists() {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Политики"));
        admin.clickElement(currentPage.linkWithText("Временные политики"));
        admin.disableAllPolicies();

        admin.clickElement(currentPage.linkWithText("Ограничительные политики"));
        admin.disableAllPolicies();

        admin.clickElement(currentPage.linkWithText("Политики теневых копий"));
        admin.clickButtonOnLastRow("Редактировать");
        if (!mainPage.checkboxWithText("Активна").getAttribute("class").contains("checked")) {
            admin.clickElement(mainPage.checkboxWithText("Активна"));
            admin.clickButtonWithText("Сохранить");
        } else {
            admin.clickButtonWithText("Отмена");
        }
    }

    @Тогда("адним видит на странице Журналы что имя распечатанного файла отображается как ссылка")
    public void adminAbleToSeeFileNameAsLink() {
        WebDriverWait wait = new WebDriverWait(admin.getDriver(), 30);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(mainPage.notification));
        } catch (Exception ex) {
            throw new NoSuchElementException("There is no Notification about new record in Journal", ex);
        }
        admin.clickElement(mainPage.notification);
        List<WebElement> tableRows = mainPage.table.findElements(By.xpath(mainPage.tableRows));
        assert tableRows.get(0).findElement(By.tagName("a")) != null;
    }
}
