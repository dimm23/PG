package serenity.stepdefinitions;

import io.cucumber.java.ru.*;
import net.thucydides.core.annotations.Steps;
import serenity.pages.*;
import serenity.user.*;
import serenity.settings.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PrintingSteps {
    public static final long TIMEOUT = 15000L;
    @Steps
    Admin admin;
    CurrentPage currentPage;
    MainPage mainPage;

    @Когда("пользователь печатает {string} файл")
    public void userPrintFile(String fileType) {
        // да / нет приходит из теста проверки результата печати в зависимости от настроек политик
        if (fileType.equals("нет")) {
            fileType = "1doc";
        } else if (fileType.equals("да")) {
            fileType = "20doc";
        }

        String command = "\\\\" + settings.pg_host + " -u " + settings.pg_user + " -p " + settings.pg_password
                + " \"C:\\Program Files (x86)\\xStarter\\xStarter.exe\" /run PG /taskname Print_" + fileType;
        executeCommand(command);
    }

    private void executeCommand(String command) {
        try {
            String currentDir = System.getProperty("user.dir");
            Process pr = Runtime.getRuntime().exec(currentDir + "\\src\\test\\files\\PsExec.exe " + command);
            readStdOutOfProcessAndClose(pr);
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка выполнения команды на компьютере пользователя", ex);
        }
    }

    private void readStdOutOfProcessAndClose(Process pr) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()))) {
            // При отправке некоторых команд процесс зависает на in.readLine(),
            // поэтому сделал принудительное ожидание окончания процесса
            Thread.sleep(TIMEOUT);
            /*String line;
            while ((line = in.readLine()) != null){
                System.out.println(line);
            }
            pr.waitFor();*/
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка чтения ответа компьютера пользователя", ex);
        }
    }

    @Дано("в списке сетевых принтеров отсутствует принтер {string}")
    public void printerIsNotShownInTable(String printerName) {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Принтеры"));
        admin.clickElement(currentPage.linkWithText("Сетевые"));

        admin.waitForLoader();
        if (mainPage.table.getText().contains(printerName)) {

            admin.adminClickButtonOnRow(printerName, "Рассинхронизировать");
            admin.clickButtonWithText("Рассинхронизировать");
        }
        admin.unableToSeeTextInTable(printerName);
    }

    @Когда("админ синхронизирует принтер {string} на принт-сервере {string}")
    public void adminSyncPrinterOnPrintServer(String printerName, String printServerName) throws InterruptedException {
        // Go to print servers page
        admin.clickElement(currentPage.linkWithText("Агенты печати"));
        admin.clickElement(currentPage.linkWithText("Принт-серверы"));

        admin.adminClickButtonOnRow(printServerName, "Синхронизировать");
        admin.clickElement(mainPage.checkboxWithPrinterName(printerName));
        admin.clickElement(mainPage.buttonArrowPrev);
        Thread.sleep(1500); //Wait for sync process
        admin.clickButtonWithText("Закрыть");
    }

    @Дано("в системе отсутствует группа принтеров {string}")
    public void groupOfPrintersIsNotExist(String groupName) {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Принтеры"));
        admin.clickElement(currentPage.linkWithText("Группы"));

        admin.addFilter("По наименованию", groupName);

        if (mainPage.table.getText().contains(groupName)) {
            admin.adminClickButtonOnRow(groupName, "Удалить");
            admin.clickButtonWithText("Удалить");
        }

        admin.unableToSeeTextInTable(groupName);
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Когда("админ добавляет новую группу принтеров {string}")
    public void adminAddNewGroupOfPrinters(String groupName) throws InterruptedException {
        Thread.sleep(1000);
        admin.clickElement(mainPage.buttonAddNew());

        adminFillGroupName(groupName);
    }

    @Дано("в системе существует группа принтеров {string}")
    public void groupOfPrintersIsExist(String groupName) throws InterruptedException {
        admin.doLogin();

        admin.clickElement(currentPage.linkWithText("Принтеры"));
        admin.clickElement(currentPage.linkWithText("Группы"));

        admin.addFilter("По наименованию", groupName);
        admin.waitForLoader();

        // Click button delete at row if its name contains groupName
        if (mainPage.table.getText().contains(groupName)) {
        //  List<WebElement> tableRows = mainPage.table.findElements(By.xpath(mainPage.tableRows));

            //  for (int i = 0; i < tableRows.size(); i++){
            admin.adminClickButtonOnRow(groupName, "Удалить");
            admin.clickButtonWithText("Удалить");
        //  }
        } else {
            adminAddNewGroupOfPrinters(groupName);
        }
        admin.clickElement(mainPage.buttonDeleteFilter);
    }

    @Когда("админ выбирает группу принтеров {string} и кликает кнопку {string}")
    public void adminSelectGroupOfPrintersAndClickButton(String groupName, String buttonName) {
        admin.adminClickButtonOnRow(groupName, buttonName);
    }

    @И("админ заполняет форму Параметры группы принтеров с наименованием {string}")
    public void adminFillGroupName(String groupName) {
        admin.enterValueToField(groupName, mainPage.fieldWithLabel("Наименование"));
        admin.clickButtonWithText("Сохранить");
    }

    @И("на компьютере пользователя настроена Двухсторонняя цветная печать")
    public void setColorPrintingWithDoubleSideMode() {
        String command = "\\\\" + settings.pg_host + " -u " + settings.pg_user + " -p " + settings.pg_password
                + " \"C:\\Program Files (x86)\\xStarter\\xStarter.exe\" /run PG /taskname SetColorTrueDuplexMode";
        executeCommand(command);
    }

    @И("на компьютере пользователя настроена Односторонняя цветная печать")
    public void setColorPrintingWithSimplexMode() {
        String command = "\\\\" + settings.pg_host + " -u " + settings.pg_user + " -p " + settings.pg_password
                + " \"C:\\Program Files (x86)\\xStarter\\xStarter.exe\" /run PG /taskname SetColorTrueSimplexMode";
        executeCommand(command);
    }

    @И("на компьютере пользователя настроена Двухсторонняя чернобелая печать")
    public void setBWPrintingWithDoubleSideMode() {
        String command = "\\\\" + settings.pg_host + " -u " + settings.pg_user + " -p " + settings.pg_password
                + " \"C:\\Program Files (x86)\\xStarter\\xStarter.exe\" /run PG /taskname SetColorFalseDuplexMode";
        executeCommand(command);
    }

    @И("на компьютере пользователя настроена Односторонняя чернобелая печать")
    public void setBWPrintingWithSimplexMode() {
        String command = "\\\\" + settings.pg_host + " -u " + settings.pg_user + " -p " + settings.pg_password
                + " \"C:\\Program Files (x86)\\xStarter\\xStarter.exe\" /run PG /taskname SetColorFalseSimplexMode";
        executeCommand(command);
    }

    @И("на компьютере пользователя принтер настроен с параметрами {string} {string}")
    public void setParamsOfPrinter(String color, String duplex) {
        if (color.equals("цветная")) {
            if (duplex.equals("двухсторонняя")) {
                setColorPrintingWithDoubleSideMode();
            } else {
                setColorPrintingWithSimplexMode();
            }
        } else {
            if (duplex.equals("двухсторонняя")) {
                setBWPrintingWithDoubleSideMode();
            } else {
                setBWPrintingWithSimplexMode();
            }
        }
    }
}
