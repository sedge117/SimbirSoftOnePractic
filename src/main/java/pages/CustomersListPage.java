package pages;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomersListPage {
    WebDriver driver;
    @FindBy(xpath = "//button[@ng-click ='showCust()']")
    private WebElement headerCustomersButton;
    @FindBy(xpath = "//input[@placeholder = 'Search Customer']")
    private WebElement searchInput;
    @FindBy(xpath = "//a[contains(@ng-click, 'fName')]")
    private WebElement firstNameLink;
    @FindBy(xpath = "//tr[contains(@ng-repeat, 'cust in Customers')]//td[1]")
    private WebElement customerFirstName;
    @FindBy(xpath = "//tr[contains(@ng-repeat, 'cust in Customers')]//td[2]")
    private WebElement customerLastName;
    @FindBy(xpath = "//tr[contains(@ng-repeat, 'cust in Customers')]//td[3]")
    private WebElement customerPostCode;


    public CustomersListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
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
    @Step("Нажать на ссылку First name, проверить, что список сортируется в обратном порядке, от Z до A")
    public boolean checkSortReverse() {

        List<WebElement> listElements = //запросила коллекцию элементов
                driver.findElements(By.xpath("//tr[contains(@ng-repeat, 'cust in Customers')]//td[1]"));
        String[] listElementsText = new String[listElements.size()];
        for(int i =0; i< listElementsText.length;i++){
            listElementsText[i]=listElements.get(i).getText(); //записала их имена в массив
        }
        Arrays.sort(listElementsText, Collections.reverseOrder()); //отсортировала в обратном порядке

        clickFirstName();

        List<WebElement> sortElements = //новая коллекция после сортировки на сайте
                driver.findElements(By.xpath("//tr[contains(@ng-repeat, 'cust in Customers')]//td[1]"));

        String[] sortElementsText = new String[sortElements.size()];
        for(int i =0; i< sortElementsText.length;i++){
            sortElementsText[i]=sortElements.get(i).getText();
        }

        return Arrays.equals(sortElementsText,listElementsText);
    }
    @Step("Нажать на ссылку First name еще раз, проверить, что список сортируется от A до Z")
    public boolean checkSort (){
        List<WebElement> listElements =
                driver.findElements(By.xpath("//tr[contains(@ng-repeat, 'cust in Customers')]//td[1]"));
        String[] listElementsText = new String[listElements.size()];
        for(int i =0; i< listElementsText.length;i++){
            listElementsText[i]=listElements.get(i).getText();
        }
        Arrays.sort(listElementsText);

        clickFirstName();

        List<WebElement> sortElements =
                driver.findElements(By.xpath("//tr[contains(@ng-repeat, 'cust in Customers')]//td[1]"));
        String[] sortElementsText = new String[sortElements.size()];
        for(int i =0; i< sortElementsText.length;i++){
            sortElementsText[i]=sortElements.get(i).getText();
        }
        return Arrays.equals(sortElementsText, listElementsText);


    }
    @Step("Проверить наличие созданного клиента в конце списка")
    public boolean checkNewCustomer(String testFname, String testLname, String testPostCode) {
        List<WebElement> customers
                = driver.findElements(By.xpath("//tr[contains(@ng-repeat, 'cust in Customers')]"));
        WebElement lastCustomer = customers.get(customers.size()-1);
        String fname = lastCustomer.findElement(By.xpath("./td[1]")).getText();
        String lname = lastCustomer.findElement(By.xpath("./td[2]")).getText();
        String pcode = lastCustomer.findElement(By.xpath("./td[3]")).getText();

        return testFname.equals(fname) && testLname.equals(lname) && testPostCode.equals(pcode);

    }
    @Step("Проверить отсутствие создаваемого клиента в конце списка")
    public boolean checkAbsence(String testFname, String testLname, String testPostCode) {
        List<WebElement> customers
                = driver.findElements(By.xpath("//tr[contains(@ng-repeat, 'cust in Customers')]"));
        WebElement lastCustomer = customers.get(customers.size()-1);
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
        List<WebElement> customers
                = driver.findElements(By.xpath("//tr[contains(@ng-repeat, 'cust in Customers')]"));
        WebElement searchCustomer = customers.get(0);
        String fname = searchCustomer.findElement(By.xpath("./td[1]")).getText();
        String lname = searchCustomer.findElement(By.xpath("./td[2]")).getText();
        String pcode = searchCustomer.findElement(By.xpath("./td[3]")).getText();
        String number = searchCustomer.findElement(By.xpath("./td[4]")).getAttribute("textContent");
        clearSearchInput();

        return text.equalsIgnoreCase(fname) || text.equalsIgnoreCase(lname) || text.equalsIgnoreCase(pcode)
                || number.contains(text);
    }
}

