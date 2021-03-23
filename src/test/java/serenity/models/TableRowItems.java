package serenity.models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TableRowItems {

    // Locators
    String groups = ".//div[contains(@class, 'icon_folder')]";
    String edit = ".//div[contains(@class, 'icon_pen')]";
    String delete = ".//div[contains(@class, 'icon_trash')]";
    String sync = ".//div[contains(@class, 'icon_sync')]";
    String unsync = ".//div[contains(@class, 'icon_unsync')]";

    public TableRowItems(WebElement element){
        this.baseElement = element;

        try {
            this.Группы = baseElement.findElement(By.xpath(groups));
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        try {
            this.Редактировать = baseElement.findElement(By.xpath(edit));
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        try {
            this.Удалить = baseElement.findElement(By.xpath(delete));
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        try {
            this.Синхронизировать = baseElement.findElement(By.xpath(sync));
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        try {
            this.Рассинхронизировать = baseElement.findElement(By.xpath(unsync));
        } catch (Exception ex) {
            ex.getStackTrace();
        }

        if (!baseElement.findElements(By.xpath(".//div[contains(@class, 'data-table__row-item_align')]"))
                .get(0).getText().equals("")) {
            this.Наименование = baseElement
                    .findElements(By.xpath(".//div[contains(@class, 'data-table__row-item_align')]")).get(0);
        } else {
            this.Наименование = baseElement
                    .findElements(By.xpath(".//div[contains(@class, 'data-table__row-item_align')]")).get(1);
        }
    }

    // Elements
    private WebElement baseElement;
    private WebElement Группы;
    private WebElement Редактировать;
    private WebElement Удалить;
    private WebElement Синхронизировать;
    private WebElement Рассинхронизировать;
    public WebElement Наименование;

    // Click on Element
    public void Группы() {
        Группы.click();
    }

    public void Редактировать() {
        Редактировать.click();
    }

    public void Удалить() {
        Удалить.click();
    }

    public void Синхронизировать() {
        Синхронизировать.click();
    }

    public void Рассинхронизировать() {
        Рассинхронизировать.click();
    }
}
