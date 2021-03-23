package serenity.user;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import serenity.models.TableRowItems;
import serenity.pages.*;
import serenity.settings.*;

import java.io.File;
import java.util.List;


public class Admin extends ScenarioSteps {

    LoginPage loginPage;
    CurrentPage currentPage;
    MainPage mainPage;

    @Step
    public void adminClickButtonOnRow(String rowName, String buttonName) {
        // Get table rows
        List<WebElement> tableRows = null;
        try {
            tableRows = mainPage.table.findElements(By.xpath(mainPage.tableRows));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Get row by Name and click buttonName
        assert tableRows != null;
        for (WebElement r: tableRows) {
            Actions actions = new Actions(getDriver());
            actions.moveToElement(r).build().perform();
            if (r.getText().contains(rowName)) {
                TableRowItems row = new TableRowItems(r);
                try {
                    row.getClass().getMethod(buttonName).invoke(row);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    @Step
    public void addFilter(String filterType, String value) {
        clickElement(mainPage.addFilterButton);
        clickElement(mainPage.filter(filterType));
        enterValueToField(value, mainPage.filterInput(filterType));
        clickElement(mainPage.checkboxWrapperOverlay);
    }

    @Step
    public void enterValueToField(String text, WebElement field) {
        field.clear();
        field.sendKeys(text);
    }

    @Step
    public void clickButtonWithText(String buttonText) {
        currentPage.buttonWithText(buttonText).click();
    }

    @Step
    public void shouldSeeNotificationWithText(String text) {
        try {
            WebDriverWait wait = new WebDriverWait(currentPage.getDriver(), 10);
            wait.until(ExpectedConditions.visibilityOf(mainPage.notification));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        assert mainPage.notification.getText().equals(text);
    }

    @Step
    public void shouldBeAtPage(String pageTitle) {
        assert mainPage.resourceTitle.getText().equals(pageTitle);
    }

    @Step
    public void ableToSeeErrorMessage(String errorMessage) {
        assert loginPage.error.getText().equals(errorMessage);
    }

    @Step
    public void clickElement(WebElement webElement) {
        webElement.click();
    }

    @Step
    public void ableToSeeTextInTable(String text) {
        // Ожидаем чтобы скрылось модальное окно из предыдущего шага, прежде чем проверять наличие текста в таблице
        int timeout = 3;
        while (mainPage.modalOverlay != null) {
            if (timeout > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                timeout--;
            } else {
                break;
            }
        }
        assert mainPage.table.getText().contains(text);
    }

    @Step
    public void unableToSeeTextInTable(String rememberedName) {
        int timeout = 3;
        while (mainPage.modalOverlay != null) {
            if (timeout > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                timeout--;
            } else {
                break;
            }
        }
        assert !mainPage.table.getText().contains(rememberedName);
    }

    @Step
    public void uploadFileToFileInput(String fileName) {
        String currentDir = System.getProperty("user.dir");
        File folder = new File(currentDir + "\\src\\test\\files");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.getName().equals(fileName + ".png")) {
                WebElement input = getDriver().findElement(By.xpath("//input[@type='file']"));
                String script = "arguments[0].setAttribute('style', '')";
                ((JavascriptExecutor) getDriver()).executeScript(script, input);
                input.sendKeys(file.getPath());
            }
        }
    }

    @Step
    public void cropQRCodeOnPage() {
        String script = "arguments[0].setAttribute('style', 'top: 287px; left: 103px; width: 460px; height: 345px;')";
        ((JavascriptExecutor) getDriver()).executeScript(script, mainPage.cropOnImage);
        //Actions actions = new Actions(getDriver());
        WebElement crop = currentPage.$("//div[contains(@class, 'ReactCrop__crop-selection')]");
        //WebElement crop = mainPage.$("//div[contains(@class, 'ord-se')]");
        currentPage.$("//body").sendKeys(Keys.PAGE_DOWN);

        // Проблема с перемещением рамки crop-а, поэтому тест пока не реализован
        crop.getLocation().move(crop.getLocation().getX() + 10, crop.getLocation().getY() + 1);
    }

    @Step
    public void abbleToSeeTextInElement(String text, WebElement element) {
        assert element.getText().contains(text);
    }

    @Step
    public void doLogin() {
        // Open Login page
        loginPage.open();
        currentPage.getDriver().manage().window().setSize(settings.screenSize);

        // Login
        enterValueToField(settings.adminLoginName, loginPage.loginField);
        enterValueToField(settings.adminPassword, loginPage.passwordField);
        clickButtonWithText("Войти");
    }

    @Step
    public void waitForLoader() {
        // 5 секунд ожидаем когда скроется loader
        int timer = 10;
        while (currentPage.loader != null) {
            if (timer > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                timer--;
            } else {
                break;
            }
        }
    }

    @Step
    public void disableAllPolicies() {
        // Get table rows
        List<WebElement> tableRows = null;
        try {
            tableRows = mainPage.table.findElements(By.xpath(mainPage.activePolicyRowLocator));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assert tableRows != null;

        // Click activity checkbox if enabled
        for (WebElement r: tableRows) {
            Actions actions = new Actions(getDriver());
            actions.moveToElement(r).build().perform();

           r.findElements(By.xpath(".//label")).get(0).click();
           try {
               Thread.sleep(1000);
           } catch (Exception ex) {
               ex.printStackTrace();
           }
        }
    }

    @Step
    public void clickButtonOnLastRow(String buttonName) {
        // Get table rows
        List<WebElement> tableRows = null;
        try {
            tableRows = mainPage.table.findElements(By.xpath(mainPage.tableRows));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Get last row and click buttonName
        assert tableRows != null;

        Actions actions = new Actions(getDriver());
        actions.moveToElement(tableRows.get(tableRows.size() - 1)).build().perform();

        TableRowItems row = new TableRowItems(tableRows.get(tableRows.size() - 1));
        try {
            row.getClass().getMethod(buttonName).invoke(row);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
