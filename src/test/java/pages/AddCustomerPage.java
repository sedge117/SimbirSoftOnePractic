package pages;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Класс, для добавления новых клиентов
 */
public class AddCustomerPage extends BasePage {
    /**
     * Сообщение на алерте, об успешном добавлении клиента
     */
    public static final String addCustomSuccess = "Customer added successfully with customer id :";

    /**
     * Сообщение на алерте, о том что клиент, возможно, дублируется
     */
    public static final String errorCustomDuplicate = "Please check the details. Customer may be duplicate";

    /**
     * Кнопка Add Customer, открывает форму для добавления нового клиента
     */
    @FindBy(xpath = "//button[@ng-class = 'btnClass1']")
    private WebElement headerAddCustomerButton;

    /**
     * Поле для ввода имени клиента
     */
    @FindBy(xpath = "//input[@placeholder = 'First Name']")
    private WebElement firstNameInput;

    /**
     * Поле для ввода фамилии клиента
     */
    @FindBy(xpath = "//input[@placeholder ='Last Name']")
    private WebElement lastNameInput;

    /**
     * поле для ввода кода клиента
     */
    @FindBy(xpath = "//input[@placeholder = 'Post Code']")
    private WebElement postCodeInput;

    /**
     * Кнопка Add Customer для создания клиента, после заполнения всех обязательных полей
     */
    @FindBy(xpath = "//button[text()='Add Customer']")
    private WebElement addCustomerButton;


    /**
     * Конструктор класса
     */
    public AddCustomerPage(WebDriver driver) {
        super(driver);
    }


    @Step("Нажать на кнопку «Add customer»")
    public AddCustomerPage clickAddCustomerInHeader() {
        headerAddCustomerButton.click();
        return this;
    }

    @Step("Заполнить поле «First name» значением {text}")
    public AddCustomerPage sendFirstName(String text) {
        firstNameInput.sendKeys(text);
        return this;
    }

    @Step("Заполнить поле «Last name» значением {text}")
    public AddCustomerPage sendLastName(String text) {
        lastNameInput.sendKeys(text);
        return this;
    }

    @Step("Заполнить поле «Post Code» значением {text}")
    public AddCustomerPage sendPostCode(String text) {
        postCodeInput.sendKeys(text);
        return this;
    }

    @Step("Нажать на кнопку «Add customer» в нижней части формы")
    public AddCustomerPage clickAddCustomer() {
        addCustomerButton.click();
        return this;
    }

    @Step("Проверить текст уведомления и закрыть всплывающее окно")
    public String getAlertTextAndClose() {
        String text = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        return text;
    }
}
