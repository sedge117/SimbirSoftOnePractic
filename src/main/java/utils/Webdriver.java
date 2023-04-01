package utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Webdriver {
    public static ChromeDriver getChromeDriver() {
        System.setProperty("webdriver.gecko.driver",
                "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
}
