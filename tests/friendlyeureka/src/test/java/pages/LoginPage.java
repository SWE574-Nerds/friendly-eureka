package pages;

import org.openqa.selenium.By;
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

	@FindBy(css = "input[type=\"username\"]")
	private WebElement usernameElement;

	@FindBy(css = "input[type=\"password\"]")
	private WebElement passwordElement;

	@FindBy(css = "button[class=\"btn btn-primary\"]")
	private WebElement loginButton;

	@FindBy(css = "a[href=\"/accounts/register/\"]")
	private WebElement createAnAccount;

	@FindBy(css = "button[class=\"btn btn-link\"]")
	private WebElement alreadyHaveAnAccount;

	public LoginPage openEureka() {
		driver.get("http://18.217.133.147:3000/welcome");
		return this;
	}

	public void goToAlreadyHaveAndAnAccount() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(alreadyHaveAnAccount)).click();
	}

	public void createAnAccount() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(createAnAccount)).click();
	}

	public void goToLoginPage() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(goToLoginPage)).click();
	}

	public WebElement getUsernameTextBox() {
		WebElement element = null;
		try{
			element = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type=\"username\"]")));}
		catch(Exception ex){
			return element;
		}
		return element;
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
