package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	protected WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "input[value=\"Search\"]")
	private WebElement searchButton;

	@FindBy(css = ".navbar-brand")
	private WebElement usernameOnHomePage;

	public String getUsername() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(searchButton));
		return usernameOnHomePage.getText();
	}
}