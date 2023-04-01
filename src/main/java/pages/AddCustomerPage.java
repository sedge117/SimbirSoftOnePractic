package pages;
import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCustomerPage {
    WebDriver driver;
    public static String addCustomSuccess = "Customer added successfully with customer id :";
    public static String errorCustomDuplicate = "Please check the details. Customer may be duplicate";
    @FindBy(xpath = "//button[@ng-class = 'btnClass1']")
    private WebElement headerAddCustomerButton;

    @FindBy(xpath = "//input[@placeholder = 'First Name']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@placeholder ='Last Name']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@placeholder = 'Post Code']")
    private WebElement postCodeInput;

    @FindBy(xpath = "//button[text()='Add Customer']")
    private WebElement addCustomerButton;

    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
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
