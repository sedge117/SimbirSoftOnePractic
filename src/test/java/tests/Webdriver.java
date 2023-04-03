package tests;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Класс, соддержащий в себе настройки веб драйвера
 */
public class Webdriver {
    public static ChromeDriver getChromeDriver() {
        System.setProperty("webdriver.gecko.driver",
                "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
}
