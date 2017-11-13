package stepDefinition;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.HomePage;
import pages.LoginPage;

public class LoginSteps {

	WebDriver driver;
	LoginPage loginPage = null;
	HomePage homePage = null;

	public LoginSteps(SharedDriver driver) {
		this.driver = driver;
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
	}

	@Given("^I am on Eureka Website$")
	public void i_am_on_Eureka_Website() throws Throwable {
		loginPage.openEureka();
		loginPage.goToLoginPage();
	}

	@When("^I enter username as \"([^\"]*)\"$")
	public void i_enter_username_as(String username) throws Throwable {
		loginPage.enterUsername(username);
	}

	@When("^enter password as \"([^\"]*)\"$")
	public void enter_password_as(String password) throws Throwable {
		loginPage.enterPassword(password);
	}

	@When("^click login button$")
	public void click_login_button() throws Throwable {
		loginPage.clickLoginButton();
	}

	@Then("^I should be able to login successfully and see my username as \"([^\"]*)\"$")
	public void i_should_be_able_to_login_successfully_and_see_my_username_as(String username) throws Throwable {
		Assert.assertEquals(username, homePage.getUsername());
	}

	@Then("^Application should be closed$")
	public void application_should_be_closed() throws Throwable {
		driver.quit();
	}

}
