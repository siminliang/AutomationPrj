package com.revature.selenium.entity.step;

import com.revature.SeleniumTestRunner;
import com.revature.selenium.entity.UserEntity;
import com.revature.selenium.entity.repositories.UserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginSteps {

    private static final Logger log = LoggerFactory.getLogger(LoginSteps.class);

    @Given("The User is on the Login Page")
    public void the_User_is_on_the_Login_Page() {
        SeleniumTestRunner.planetariumLoginHome.goToPlanetariumLoginHome();
        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Planetarium Login"));

    }
    @Given("Account with username Lisan and password al-gaib already registered")
    public void accountWithUsernameLisanAndPasswordAlGaibAlreadyRegistered() {
        // add the user with username/password
        UserEntity userEntity = new UserEntity("Lisan", "al-gaib");
        UserRepository.addUser(userEntity);
    }

    @When("The User enters {string} into username input bar")
    public void the_User_enters_into_username_input_bar(String string) {
        SeleniumTestRunner.planetariumLoginHome.sendToUsernameInput(string);
    }
    @When("The User enters {string} into password input bar")
    public void the_User_enters_into_password_input_bar(String string) {
        SeleniumTestRunner.planetariumLoginHome.sendToPasswordInput(string);
    }
    @When("The User clicks on the Login Button")
    public void the_User_clicks_on_the_Login_Button() {
        // Write code here that turns the phrase above into concrete actions
        SeleniumTestRunner.planetariumLoginHome.clickLoginButton();
    }
    @Then("The User is redirected to the Planetarium")
    public void the_User_is_redirected_to_the_Planetarium() {
        // Write code here that turns the phrase above into concrete actions
        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Home"));
        Assert.assertEquals("Home", SeleniumTestRunner.driver.getTitle());
    }

    @Then("The User is kept at the login page")
    public void the_User_is_kept_at_the_login_page() {
        // Write code here that turns the phrase above into concrete actions
        SeleniumTestRunner.wait.until(ExpectedConditions.alertIsPresent());
        SeleniumTestRunner.driver.switchTo().alert().accept();
        Assert.assertEquals("Planetarium Login", SeleniumTestRunner.driver.getTitle());

    }

    @When("The User clicks on the Logout Button")
    public void theUserClicksOnTheLogoutButton() {
        SeleniumTestRunner.planetariumHome.clickLogoutButton();
    }

    @Then("The User is redirected back to the Login page")
    public void theUserIsRedirectedBackToTheLoginPage() {
        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Planetarium Login"));
        Assert.assertEquals("Planetarium Login", SeleniumTestRunner.driver.getTitle());
    }

    @When("The User enters the Planetarium Main Page URL into the browser URL")
    public void theUserEntersThePlanetariumMainPageURLIntoTheBrowserURL() {
        SeleniumTestRunner.planetariumHome.goToPlanetariumHomePage();
        SeleniumTestRunner.wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Planetarium Login")));
    }

    @Then("The User is not redirected to the Planetarium")
    public void theUserIsNotRedirectedToThePlanetarium() {
        Assert.assertNotEquals("Home", SeleniumTestRunner.driver.getTitle());
    }
}
