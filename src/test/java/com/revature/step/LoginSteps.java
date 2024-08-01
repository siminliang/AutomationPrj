package com.revature.step;

import com.revature.TestRunner;
import com.revature.entity.UserEntity;
import com.revature.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import com.revature.TestRunner;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginSteps {

    @Given("The User is on the Login Page")
    public void the_User_is_on_the_Login_Page() {
        TestRunner.planetariumLoginHome.goToPlanetariumLoginHome();
    }
    @Given("Account with username Lisan and password al-gaib already registered")
    public void accountWithUsernameLisanAndPasswordAlGaibAlreadyRegistered() {
        // add the user with username/password
        String fileName = "AddUser.sql";
        UserEntity userEntity = new UserEntity("Lisan", "al-gaib");
        DatabaseScriptRunnerUtility.runSQLScript(fileName, userEntity);
    }

    @When("The User enters {string} into username input bar")
    public void the_User_enters_into_username_input_bar(String string) {
        TestRunner.planetariumLoginHome.sendToUsernameInput(string);
    }
    @When("The User enters {string} into password input bar")
    public void the_User_enters_into_password_input_bar(String string) {
        TestRunner.planetariumLoginHome.sendToPasswordInput(string);
    }
    @When("The User clicks on the Login Button")
    public void the_User_clicks_on_the_Login_Button() {
        // Write code here that turns the phrase above into concrete actions
        TestRunner.planetariumLoginHome.clickLoginButton();
    }
    @Then("The User is redirected to the Planetarium")
    public void the_User_is_redirected_to_the_Planetarium() {
        // Write code here that turns the phrase above into concrete actions
        TestRunner.wait.until(ExpectedConditions.titleIs("Home"));
        Assert.assertEquals("Home", TestRunner.driver.getTitle());
    }

    @Then("The User is kept at the login page")
    public void the_User_is_kept_at_the_login_page() {
        // Write code here that turns the phrase above into concrete actions
        TestRunner.wait.until(ExpectedConditions.alertIsPresent());
        TestRunner.driver.switchTo().alert().accept();
        Assert.assertEquals("Planetarium Login", TestRunner.driver.getTitle());

    }
}
