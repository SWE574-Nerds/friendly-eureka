package stepDefinition;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class AbstractStepDefinition {

	protected static WebDriver driver;

	protected WebDriver getDriver() {
		if (driver == null) {
			ChromeDriverManager.getInstance().setup();
			driver = new ChromeDriver();
		}
		return driver;
	}

}
