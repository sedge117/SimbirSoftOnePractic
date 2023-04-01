import io.qameta.allure.Epic;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CustomersListPage;

import java.time.Duration;
import pages.AddCustomerPage;
import utils.Webdriver;


@Epic("CustomerTests")
public class CustomerTests {

    WebDriver driver;
    String mainPage = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager";
    AddCustomerPage addCustomerPage;
    CustomersListPage customersListPage;

    @BeforeMethod
    public void beforeTest() {
        driver = Webdriver.getChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(mainPage);
        addCustomerPage = new AddCustomerPage(driver);
        customersListPage = new CustomersListPage(driver);
    }


    @DataProvider(name = "createCustomerData")
    public Object[][] createCustomerData() {
        return new Object[][]{
                {"Brienne", "Tarth", "E12345", AddCustomerPage.addCustomSuccess}};
    }
    @Test(  description = "Создание нового клиента",
            dataProvider = "createCustomerData")
    public void createCustomer(String testFirstName,
                               String testLastName,
                               String testPostCode,
                               String testAlertText) {
        addCustomerPage
                .clickAddCustomerInHeader()
                .sendFirstName(testFirstName)
                .sendLastName(testLastName)
                .sendPostCode(testPostCode)
                .clickAddCustomer();
        String alertText = addCustomerPage.getAlertTextAndClose();
        boolean isAdded = customersListPage
                .clickCustomers()
                .checkNewCustomer(testFirstName, testLastName, testPostCode);
        Assert.assertTrue(alertText.contains(testAlertText));
        Assert.assertTrue(isAdded);
    }


    @DataProvider(name = "customerDuplicateData")
    public Object[][] customerDuplicateData() {
        return new Object[][]{
                {"Hermoine", "Granger", "E859AB", AddCustomerPage.errorCustomDuplicate}};
    }
    @Test(  description = "Повторное создание уже существующего клиента",
            dataProvider = "customerDuplicateData")
    public void createCustomerDuplicate(String testFirstName,
                               String testLastName,
                               String testPostCode,
                               String testAlertText) {
        addCustomerPage
                .clickAddCustomerInHeader()
                .sendFirstName(testFirstName)
                .sendLastName(testLastName)
                .sendPostCode(testPostCode)
                .clickAddCustomer();
        String alertText = addCustomerPage.getAlertTextAndClose();
        boolean isAdded = customersListPage
                .clickCustomers()
                .checkAbsence(testFirstName, testLastName, testPostCode);
        Assert.assertTrue(alertText.contains(testAlertText));
        Assert.assertFalse(isAdded);
    }



    @DataProvider(name = "errorCustomerData")
    public Object[][] errorCustomerData() {
        return new Object[][]{
                {"", "Tarth", "E12345"},
                {"Brienne", "", "E12345"},
                {"Brienne", "Tarth", ""}
        };
    }

    @Test(  description = "Проверка заполнения обязательных полей",
            dataProvider = "errorCustomerData")
    public void errorCustomer(String testFirstName,
                              String testLastName,
                              String testPostCode) {
        addCustomerPage
                .clickAddCustomerInHeader()
                .sendFirstName(testFirstName)
                .sendLastName(testLastName)
                .sendPostCode(testPostCode)
                .clickAddCustomer();

        boolean isAdded = customersListPage
                .clickCustomers()
                .checkAbsence(testFirstName, testLastName, testPostCode);
        Assert.assertFalse(isAdded);
    }

    @Test(description = "Сортировка клиентов по имени(First Name)")
    public void checkSortFirstName() {
        customersListPage
                .clickCustomers();
        boolean is_checkSortReverse = customersListPage.checkSortReverse();
        boolean is_checkSort = customersListPage.checkSort();
        Assert.assertTrue(is_checkSortReverse);
        Assert.assertTrue(is_checkSort);
    }


    @DataProvider(name = "searchData")
    public Object[][] searchData() {
        return new Object[][]{
                {"Hermoine"},
                {"Granger"},
                {"E859AB"},
                {"1001"},
                {"1002"},
                {"1003"},
        };
    }

    @Test(  description = "Поиск клиента по атрибутам",
            dataProvider = "searchData")
    public void searchCustomer(String data) {
        customersListPage
                .clickCustomers();
        boolean is_search = customersListPage.checkSearch(data);
        Assert.assertTrue(is_search);
    }

    @AfterMethod()
    public void afterTest() {
        driver.quit();
    }

}

