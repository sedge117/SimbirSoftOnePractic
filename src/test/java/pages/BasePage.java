package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Родителький класс, содержащий в себе наследуемый конструктор
 */
public class BasePage {
    WebDriver driver;
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
