package pages;

import org.openqa.selenium.By;
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

	@FindBy(css = "input[ng-reflect-name=\"searchString\"]")
	private WebElement searchButton;

	@FindBy(css = "div[class=\"col-md-8\"]")
	private WebElement usernameArea;

	@FindBy(css = "a[title=\"Sign out\"]")
	private WebElement signOut;

	@FindBy(css = "a[href=\"/all\"] i")
	private WebElement goToListoryItems;

	public String getPageSource() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(searchButton));
		String pageSource = driver.getPageSource();
		return pageSource;
	}

	public WebElement getUsernamePlaceInHomePage(String username){
		WebElement element = null;
		try{ element = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("                        Welcome, "+username+"                    ")));
		}
		catch(Exception ex){
			return element;
		}
		return element;
	}

	public ListoryPage goToListoryItemsPage() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(goToListoryItems)).click();
		return new ListoryPage(driver);
	}

	public LoginPage clickSignOutButton() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(signOut)).click();
		return new LoginPage(driver);
	}

	public WebElement getSignOutButton() {
		WebElement element = null;
		try{
			element = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[title=\"Sign out\"]")));}
		finally{}
		return element;
	}

}
