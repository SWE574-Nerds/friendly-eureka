package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ListoryPage {

	protected WebDriver driver;

	public ListoryPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(className = "card-title")
	private WebElement cardTitle;

	@FindBy(css = "div[class=\"card-columns\"] div")
	private WebElement cardColumns;

	@FindBy(css = "cardview")
	private WebElement cardView;

	public WebElement getCardColumns(){
		WebElement element = null;
		try{
			element = new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(cardColumns));
		}
		catch(Exception ex){
			return element;
		}
		return element;
	}

	public WebElement getCardView()
	{
		WebElement element = null;
		try{
			element = new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(cardView));
		}
		catch(Exception ex){
			return element;
		}
		return element;
	}
}
