package stepDefinition;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.HomePage;
import pages.ListoryPage;
import pages.LoginPage;

public class ListorySteps {

	WebDriver driver;
	LoginPage loginPage = null;
	HomePage homePage = null;
	ListoryPage listoryPage = null;

	public ListorySteps(SharedDriver driver) {
		this.driver = driver;
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		listoryPage = new ListoryPage(driver);
	}

	@When("^I login with valid credentials using \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_login_with_valid_credentials_using_and(String username, String password) throws Throwable {
		loginPage.openEureka()
				.goToAlreadyHaveAndAnAccount();
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();
		assertThat(homePage.getSignOutButton(), is(notNullValue()));
	}

	@When("^I click to see history items list")
	public void i_click_to_see_history_items_list() throws Throwable {
		homePage.goToListoryItemsPage();
		assertThat(listoryPage.getCardView(), is(notNullValue()));
	}

	@Then("^I should be able to list the history items$")
	public void i_should_be_able_to_list_the_history_items() throws Throwable {
		Dimension cardColumns = listoryPage.getCardColumns().getSize();
		assertThat(cardColumns.getHeight(), is(notNullValue()));
	}


}
