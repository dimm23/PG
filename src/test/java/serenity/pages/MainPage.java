package serenity.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage extends PageObject {
    public final String tableLocator = "//div[contains(@class, 'data-table__body')]";
    public final String tableRows = ".//div[contains(@class, 'data-table__row') and (@style='min-width: 100%;')]";
    public final String activePolicyRowLocator = ".//div[@class='data-table__row policy-active']";

    @FindBy(xpath = "//div[contains(@class, 'notification')]")
    public WebElement notification;

    @FindBy(xpath = "//div[@class='filter-resource__title']")
    public WebElement resourceTitle;

    //TODO: Change FindBy to id when will be added
    @FindBy(xpath = "//div[@class='show-resource__icon-btn-wrapper']/div")

    public List<WebElement> buttonsAboveTable;

    public WebElement buttonAddNew() {
        return buttonsAboveTable.get(0);
    }

    public WebElement buttonSyncFromFolder() {
        return buttonsAboveTable.get(3);
    }

    @FindBy(xpath = "//div[@class='load-icon']")
    public WebElement loadButton;

    @FindBy(xpath = "//div[@class='text-input']/div/input")
    public List<WebElement> addNewUserFormFields;

    @FindBy(xpath = "//input[@type='email']")
    public WebElement emailField;

    @FindBy(xpath = tableLocator)
    public WebElement table;

    @FindBy(xpath = "//div[contains(@class, 'modal__overlay')]")
    public WebElement modalOverlay;

    @FindBy(xpath = "//div[@class='checkbox-multiselect-wrapper']")
    public WebElement addFilterButton;

    @FindBy(xpath = "//div[./button[contains(@class, 'theme--neutral')]]")
    public WebElement buttonDeleteFilter;

    @FindBy(xpath = "//div[@class='checkbox-multiselect-wrapper__overlay']")
    public WebElement checkboxWrapperOverlay;

    public WebElement filter(String name) {
        return $("//span[contains(.,'" + name + "')]");
    }

    public WebElement filterInput(String name) {
        return $("//div[label[contains(.,'" + name + "')]]/input");
    }

    public WebElement rememberedPrinter(String name) {
        return $("//div[@class='sync-table__row'][./div[text()='" + name + "']]/div[2]/div/label/div");
    }

    @FindBy(xpath = "//div[contains(@class, 'sync-resource__arrow_prev_active')]")
    public WebElement buttonArrowPrev;

    public WebElement fieldWithLabel(String label) {
        return $("//div[./label[contains(., '" + label + "')]]/input");
    }

    public WebElement valueInGroupPeriod(String value) {
        return $("//li[contains(.,'" + value + "')]");
    }

    public WebElement checkboxWithText(String text) {
        return $("//label[./*[text()='" + text + "']]/div");
    }

    public WebElement checkboxColorPrinting() {
        ListOfWebElementFacades checkboxes = $$("//label[./*[text()='Цветная печать']]/div");
        return checkboxes.get(1);
    }

    public WebElement checkboxWithPrinterName(String printerName) {
        return $("//div[./*[text()='" + printerName + "']]//label/div");
    }

    public WebElement checkboxDoubleSidePrinting() {
        ListOfWebElementFacades checkboxes = $$("//label[./*[text()='Двухсторонняя печать']]/div");
        return checkboxes.get(1);
    }

    public WebElement dayOfWeekOrTime(String text) {
        return $("//div[contains(@class, 'select-time__items')]//div[contains(.,'" + text + "')]");
    }

    @FindBy(className = "ReactCrop__crop-selection")
    public WebElement cropOnImage;

    @FindBy(className = "show-investigation-resource__search-input")
    public WebElement investigationDecodedText;

    @FindBy(xpath = "//div[contains(@class, 'data-table__header')]/div/div[1]")
    public WebElement firstCellOfTableHeader;

    public WebElement optionFromDropDownMenu(String pointName) {
        return $("//li[contains(., '" + pointName + "')]");
    }

    public WebElement firstOptionFromDropDownMenu() {
        ListOfWebElementFacades optionsFromDropDown = $$("//ul/li");
        return optionsFromDropDown.get(0);
    }
}
