package com.revature.selenium.step;

import com.revature.SeleniumTestRunner;
import com.revature.selenium.entity.UserEntity;
import com.revature.selenium.repositories.UserRepository;
import com.revature.selenium.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class RegistrationSteps {

    @Given("Account with username {string} and password {string} already registered")
    public void account_with_username_and_password_already_registered(String string, String string2) {
        // add the user with username/password
        UserEntity userEntity = new UserEntity(string, string2);
        UserRepository.addUser(userEntity);
    }
    @Given("No Registered User with username {string}")
    public void no_Registered_User_with_username(String string) {
        // Write code here that turns the phrase above into concrete actions
        UserEntity userEntity = new UserEntity(string);
        UserRepository.deleteUser(userEntity);
    }


    @When("The User clicks on Create an Account Link")
    public void the_User_clicks_on_Create_an_Account_Link() {
        // Write code here that turns the phrase above into concrete actions
        SeleniumTestRunner.wait.until(ExpectedConditions.elementToBeClickable(SeleniumTestRunner.driver.findElement(By.linkText("Create an Account"))));
        SeleniumTestRunner.planetariumLoginHome.clickCreateAccountLink();
        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Account Creation"));
    }

    @When("The User enters {string} into registration username input bar")
    public void theUserEntersIntoRegistrationUsernameInputBar(String string) {
        SeleniumTestRunner.planetariumRegistrationHome.sendToUsernameInput(string);

    }

    @When("The User enters {string} into registration password input bar")
    public void theUserEntersIntoRegistrationPasswordInputBar(String string) {
        SeleniumTestRunner.planetariumRegistrationHome.sendToPasswordInput(string);

    }


    @When("The User clicks on the Create Button")
    public void the_User_clicks_on_the_Create_Button() {
        // Write code here that turns the phrase above into concrete actions
        //SeleniumTestRunner.wait.until(ExpectedConditions.elementToBeClickable(SeleniumTestRunner.driver.findElement(By.xpath("//input[@type='submit' and @value='Create']"))));
        SeleniumTestRunner.planetariumRegistrationHome.clickCreateButton();
    }
    @Then("The User is redirected into the Planetarium Login page")
    public void the_User_is_registered_and_redirected_into_the_Planetarium() {
        // Write code here that turns the phrase above into concrete actions
        SeleniumTestRunner.wait.until(ExpectedConditions.alertIsPresent());
        SeleniumTestRunner.driver.switchTo().alert().accept();
        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Planetarium Login"));
        Assert.assertEquals("Planetarium Login", SeleniumTestRunner.driver.getTitle());

    }

    @Then("The User {string} and password {string} is registered")
    public void the_User_is_registered(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        boolean existID = false;
        List<UserEntity> userEntityList = DatabaseScriptRunnerUtility.getAllUserInfo();
        for(UserEntity userEntity : userEntityList){
            if(userEntity.getUsername().equals(string) && userEntity.getPassword().equals(string2)) {
                existID = true;
            }
        }
        Assert.assertTrue(existID);

    }


    @Then("The User is kept at the Registration page")
    public void theUserIsKeptAtTheRegistrationPage() {
        SeleniumTestRunner.wait.until(ExpectedConditions.alertIsPresent());
        SeleniumTestRunner.driver.switchTo().alert().accept();
        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Account Creation"));
        Assert.assertEquals("Account Creation", SeleniumTestRunner.driver.getTitle());
    }

    @Then("The User {string} and password {string} is not registered")
    public void theUserIsNotRegistered(String string, String string2) {
        boolean existID = false;
        List<UserEntity> userEntityList = DatabaseScriptRunnerUtility.getAllUserInfo();
        for(UserEntity userEntity : userEntityList){
            if(userEntity.getUsername().equals(string) && userEntity.getPassword().equals(string2)) {
                existID = true;
            }
        }
        Assert.assertFalse(existID);
    }




}
