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

public class RegistrationSteps {

    @Given("Account with username {string} and password {string} already registered")
    public void account_with_username_and_password_already_registered(String string, String string2) {
        // add the user with username/password
        String fileName = "AddUser.sql";
        UserEntity userEntity = new UserEntity(string, string2);
        DatabaseScriptRunnerUtility.runSQLScript(fileName, userEntity);
    }
    @Given("No Registered User with username {string}")
    public void no_Registered_User_with_username(String string) {
        // Write code here that turns the phrase above into concrete actions
        String fileName = "DeleteUser.sql";
        UserEntity userEntity = new UserEntity(string);
        DatabaseScriptRunnerUtility.runSQLScript(fileName, userEntity);
    }


    @When("The User clicks on Create an Account Button")
    public void the_User_clicks_on_Create_an_Account_Button() {
        // Write code here that turns the phrase above into concrete actions
        TestRunner.planetariumLoginHome.clickCreateAccountLink();
    }

    @When("The User enters {string} into registration username input bar")
    public void theUserEntersIntoRegistrationUsernameInputBar(String string) {
        TestRunner.planetariumRegistrationHome.sendToUsernameInput(string);

    }

    @When("The User enters {string} into registration password input bar")
    public void theUserEntersIntoRegistrationPasswordInputBar(String string) {
        TestRunner.planetariumRegistrationHome.sendToPasswordInput(string);

    }


    @When("The User clicks on the Create Button")
    public void the_User_clicks_on_the_Create_Button() {
        // Write code here that turns the phrase above into concrete actions
        TestRunner.planetariumRegistrationHome.clickCreateButton();
    }
    @Then("The User is registered and redirected into the Planetarium Login page")
    public void the_User_is_registered_and_redirected_into_the_Planetarium() {
        // Write code here that turns the phrase above into concrete actions
        TestRunner.wait.until(ExpectedConditions.alertIsPresent());
        TestRunner.driver.switchTo().alert().accept();
        TestRunner.wait.until(ExpectedConditions.titleIs("Planetarium Login"));
        Assert.assertEquals("Planetarium Login", TestRunner.driver.getTitle());

    }


    @Then("The User is kept at the Registration page")
    public void theUserIsKeptAtTheRegistrationPage() {
        TestRunner.wait.until(ExpectedConditions.alertIsPresent());
        TestRunner.driver.switchTo().alert().accept();
        TestRunner.wait.until(ExpectedConditions.titleIs("Account Creation"));
        Assert.assertEquals("Account Creation", TestRunner.driver.getTitle());
    }
}
