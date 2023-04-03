package tests;
import io.qameta.allure.Epic;
import org.testng.Assert;
import org.testng.annotations.*;

@Epic("tests.CustomerTests")
public class CustomersTests extends BaseTests {
    @Test(description = "Сортировка клиентов по имени(First Name)")
    public void checkSortFirstNameTest() {
        customersListPage
                .clickCustomers();
        boolean isCheckSortReverse = customersListPage.checkSort(true);
        boolean isCheckSort = customersListPage.checkSort(false);
        Assert.assertTrue(isCheckSortReverse, "Сортировка не выполнена");
        Assert.assertTrue(isCheckSort, "Сортировка не выполнена");
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
    public void searchCustomerTest(String data) {
        customersListPage
                .clickCustomers();
        boolean isSearch = customersListPage.checkSearch(data);
        Assert.assertTrue(isSearch, "Клиент не найден");
    }
}

