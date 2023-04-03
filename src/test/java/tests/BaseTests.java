package tests;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.AddCustomerPage;
import pages.CustomersListPage;
import java.time.Duration;

/**
 * Родительский класс, содержащий в себе предусловия и постусловия для проводимых тестов
 */
public class BaseTests {
    WebDriver driver;
    final String mainPage = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager";
    AddCustomerPage addCustomerPage;
    CustomersListPage customersListPage;

    @BeforeMethod
    public void setUp() {
        driver = Webdriver.getChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(mainPage);
        addCustomerPage = new AddCustomerPage(driver);
        customersListPage = new CustomersListPage(driver);
    }
    @AfterMethod()
    public void tearDown() {
        driver.quit();
    }
}
