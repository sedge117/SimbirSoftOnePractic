package pages;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Класс для работы со списком Клиентов
 */
public class CustomersListPage extends BasePage{
    /**
     * Кнопка Customers, открывает список клиентов
     */
    @FindBy(xpath = "//button[@ng-click ='showCust()']")
    private WebElement headerCustomersButton;

    /**
     * Поле ввода для поиска клиентов
     */
    @FindBy(xpath = "//input[@placeholder = 'Search Customer']")
    private WebElement searchInput;

    /**
     * Ссылка First Name, для сортировки клиентов в алфавитном порядке
     */
    @FindBy(xpath = "//a[contains(@ng-click, 'fName')]")
    private WebElement firstNameLink;

    /**
     * Веб элемент, содержащий имя клиента
     */
    @FindBy(xpath = "//tr[contains(@ng-repeat, 'cust in Customers')]//td[1]")
    private WebElement customerFirstName;

    /**
     * Веб элемент, содержащий фамилию клиента
     */
    @FindBy(xpath = "//tr[contains(@ng-repeat, 'cust in Customers')]//td[2]")
    private WebElement customerLastName;

    /**
     * Веб элемент, содержащий код клиента
     */
    @FindBy(xpath = "//tr[contains(@ng-repeat, 'cust in Customers')]//td[3]")
    private WebElement customerPostCode;

    /**
     * Коллекция элементов, содержащая имена всех клиентов
     */
    @FindBy(xpath = "//tr[contains(@ng-repeat, 'cust in Customers')]//td[1]")
    private List<WebElement> customersFirstNameList;

    /**
     * Коллекция элементов, содержащая всех клиентов
     */
    @FindBy(xpath = "//tr[contains(@ng-repeat, 'cust in Customers')]")
    private List<WebElement> customersList;


    /**
     * Конструктор класса
     */
    public CustomersListPage(WebDriver driver) {
        super(driver);
    }


    @Step("Нажать на кнопку «Customers»")
    public CustomersListPage clickCustomers() {
        headerCustomersButton.click();
        return this;
    }

    @Step("Нажать на ссылку «First name»")
    public CustomersListPage clickFirstName() {
        firstNameLink.click();
        return this;
    }

    @Step("Нажать на ссылку First name, проверить, что список сортируется в порядке reverseOrder = {isReverseOrder}")
    public boolean checkSort(boolean isReverseOrder) {
        String[] listElementsText = new String[customersFirstNameList.size()];
        for(int i =0; i< listElementsText.length; i++){
            listElementsText[i]= customersFirstNameList.get(i).getText();
        }
        if(isReverseOrder)
            Arrays.sort(listElementsText, Collections.reverseOrder());
        else
            Arrays.sort(listElementsText);

        clickFirstName();

        String[] sortElementsText = new String[customersFirstNameList.size()];
        for(int i =0; i< sortElementsText.length;i++){
            sortElementsText[i]= customersFirstNameList.get(i).getText();
        }
        return Arrays.equals(sortElementsText,listElementsText);
    }

    @Step("Проверить факт создания клиента в конце списка")
    public boolean checkNewCustomer(String testFname, String testLname, String testPostCode) {
        WebElement lastCustomer = customersList.get(customersList.size()-1);
        String fname = lastCustomer.findElement(By.xpath("./td[1]")).getText();
        String lname = lastCustomer.findElement(By.xpath("./td[2]")).getText();
        String pcode = lastCustomer.findElement(By.xpath("./td[3]")).getText();
        return testFname.equals(fname) && testLname.equals(lname) && testPostCode.equals(pcode);
    }

    @Step("Ввести данные в поле Поиск")
    public CustomersListPage sendSearch(String text){
        searchInput.sendKeys(text);
        return this;
    }

    @Step("Очистить поле Поиск")
    public CustomersListPage clearSearchInput (){
        searchInput.clear();
        return this;
    }

    @Step("Поиск клиента по атрибуту {text}")
    public boolean checkSearch(String text){
        sendSearch(text);
        WebElement searchCustomer = customersList.get(0);
        String fname = searchCustomer.findElement(By.xpath("./td[1]")).getText();
        String lname = searchCustomer.findElement(By.xpath("./td[2]")).getText();
        String pcode = searchCustomer.findElement(By.xpath("./td[3]")).getText();
        String number = searchCustomer.findElement(By.xpath("./td[4]")).getAttribute("textContent");
        clearSearchInput();
        return text.equalsIgnoreCase(fname) || text.equalsIgnoreCase(lname) || text.equalsIgnoreCase(pcode)
                || number.contains(text);
    }
}

