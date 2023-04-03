package tests;
import io.qameta.allure.Epic;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AddCustomerPage;

@Epic("tests.AddCustomerTests")
public class AddCustomerTests extends BaseTests {
    @DataProvider(name = "createCustomerData")
    public Object[][] createCustomerData() {
        return new Object[][]{
                {"Brienne", "Tarth", "E12345", true, AddCustomerPage.addCustomSuccess},
                {"Hermoine", "Granger", "E859AB", false, AddCustomerPage.errorCustomDuplicate}
        };
    }

    @Test(description = "Создание нового клиента и повторное создание уже существующего клиента",
            dataProvider = "createCustomerData")
    public void createCustomerTest(String testFirstName,
                                   String testLastName,
                                   String testPostCode,
                                   boolean shouldBeAdd,
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

        Assert.assertTrue(alertText.contains(testAlertText), "Сообщение на алерте не соответсвует");
        Assert.assertEquals(isAdded, shouldBeAdd, "Ожидаемый и фактический результаты добавления клиента не совпадают");
    }

    @DataProvider(name = "requiredFieldData")
    public Object[][] requiredFieldData() {
        return new Object[][]{
                {"", "Tarth", "E12345"},
                {"Brienne", "", "E12345"},
                {"Brienne", "Tarth", ""}
        };
    }

    @Test(description = "Проверка заполнения обязательных полей",
            dataProvider = "requiredFieldData")
    public void checkRequiredFieldsTest(String testFirstName,
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
                .checkNewCustomer(testFirstName, testLastName, testPostCode);

        Assert.assertFalse(isAdded, "Клиент не должен быть добавлен");
    }
}
