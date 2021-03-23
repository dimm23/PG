package serenity.stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.ru.*;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.Cookie;
import serenity.pages.*;
import serenity.user.*;
import serenity.settings.*;


public class LoginPageSteps {

    @Steps
    Admin admin;
    LoginPage loginPage;
    CurrentPage currentPage;
    MainPage mainPage;

    @Дано("админ на странице логин")
    public void adminAtLoginPage() {
        loginPage.open();
        currentPage.getDriver().manage().window().setSize(settings.screenSize);
    }

    @Когда("админ вводит логин и пароль")
    public void adminEnterLoginAndPassword() {
        admin.enterValueToField(settings.adminLoginName, loginPage.loginField);
        admin.enterValueToField(settings.adminPassword, loginPage.passwordField);
        admin.clickButtonWithText("Войти");
    }

    @Тогда("админ попадает на страницу {string}")
    public void adminCameToPage(String pageTitle) {
        admin.shouldBeAtPage(pageTitle);
    }

    @Когда("админ вводит {string} в поле {string}")
    public void adminTypeToField(String text, String fieldName) {
        if (text.equals("Произвольное название")) {
            Faker faker = new Faker();
            text = faker.app().name();
        }
        currentPage.getDriver().manage().addCookie(new Cookie("NAME", text));
        admin.enterValueToField(text, mainPage.fieldWithLabel(fieldName));
    }

    @Когда("админ вводит {string} в поле Логин")
    public void adminEnterValueTologinField(String text) {
        admin.enterValueToField(text, loginPage.loginField);
    }

    @И("админ вводит {string} в поле Пароль")
    public void adminEnterValueToPasswordField(String text) {
        admin.enterValueToField(text, loginPage.passwordField);
    }

    @Тогда("админ видит ошибку {string}")
    public void adminAbbleToSeeError(String errorMessage) {
        admin.ableToSeeErrorMessage(errorMessage);
    }
}
