package stepDefinition;

import static org.junit.Assert.assertThat;

import org.junit.AfterClass;

import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.WebDriver;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.HomePage;
import static org.hamcrest.CoreMatchers.is;
import pages.LoginPage;

public class LoginSteps {

	WebDriver driver;
	static LoginPage loginPage = null;
	HomePage homePage = null;

	public LoginSteps(SharedDriver driver) {
		this.driver = driver;
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
	}

	@Given("^I am on Eureka Website$")
	public void i_am_on_Eureka_Website() throws Throwable {

	}

	@When("^Already have an account")
	public void i_already_have_ac_account() throws Throwable {
		loginPage.goToAlreadyHaveAndAnAccount();
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

	@Then("^I should be able to login successfully")
	public void i_should_be_able_to_login_successfully() throws Throwable {
		assertThat(homePage.getSignOutButton(), is(notNullValue()));
	}

	@Then("^I should NOT be able to login successfully")
	public void i_should_not_be_able_to_login_successfully() throws Throwable {
		assertThat(loginPage.getUsernameTextBox(), is(notNullValue()));
	}


	@Then("^I click logout")
	public void i_click_logout() throws Throwable {
		homePage.clickSignOutButton();
	}

	@Before
	public static void setUp(){
		loginPage.openEureka();
	}

	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}



}
