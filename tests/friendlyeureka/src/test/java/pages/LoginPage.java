package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	protected WebDriver driver;

	@FindBy(css = ".glyphicon-log-in")
	private WebElement goToLoginPage;

	@FindBy(css = "input[name=\"username\"]")
	private WebElement usernameElement;

	@FindBy(css = "input[name=\"password\"]")
	private WebElement passwordElement;

	@FindBy(css = "input[value=\"Login\"]")
	private WebElement loginButton;

	@FindBy(css = "a[href=\"/accounts/register/\"]")
	private WebElement createAnAccount;

	public LoginPage openEureka() {
		driver.get("http://127.0.0.1:8000/");
		return this;
	}

	public void createAnAccount() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(createAnAccount)).click();
	}

	public void goToLoginPage() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(goToLoginPage)).click();
	}

	public void enterUsername(String username) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(usernameElement)).sendKeys(username);
	}

	public void enterPassword(String password) {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(passwordElement)).sendKeys(password);
	}

	public HomePage clickLoginButton() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(loginButton)).click();
		return new HomePage(driver);
	}
}
