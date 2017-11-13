package stepDefinition;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.ChromeDriverManager;

public class SharedDriver extends EventFiringWebDriver {
	private static WebDriver REAL_DRIVER = null;
	private static final Thread CLOSE_THREAD = new Thread() {
		@Override
		public void run() {
			REAL_DRIVER.close();
		}
	};

	static {
		Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
		ChromeDriverManager.getInstance().setup();
		REAL_DRIVER = new ChromeDriver();
	}

	public SharedDriver() {
		super(REAL_DRIVER);
	}

	@Override
	public void close() {
		if (Thread.currentThread() != CLOSE_THREAD) {
			throw new UnsupportedOperationException(
					"You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
		}
		super.close();
	}

	@Before
	public void deleteAllCookies() {
		manage().deleteAllCookies();
	}

	@After
	public void embedScreenshot(Scenario scenario) {
		try {
			byte[] screenshot = getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		} catch (WebDriverException somePlatformsDontSupportScreenshots) {
			System.err.println(somePlatformsDontSupportScreenshots.getMessage());
		}
	}

}
