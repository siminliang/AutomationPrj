package com.revature.step;

import com.revature.TestRunner;
import com.revature.entity.PlanetEntity;
import com.revature.pom.PlanetariumHome;
import com.revature.repositories.PlanetRepository;
import com.revature.services.LoginService;
import com.revature.utilities.DatabaseConnectorUtility;
import com.revature.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class DeletePlanetSteps {

    @Given("The User is already logged in")
    public void the_User_is_already_logged_in() {
        Assert.assertTrue(LoginService.loginBatman());
    }

    @Given("Given Planet names {string} exists")
    public void given_Planet_names_exists(String string) {
        PlanetEntity planetEntity = new PlanetEntity(string);
        DatabaseScriptRunnerUtility.addTempPlanet(planetEntity);
        TestRunner.refresh();
        TestRunner.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("celestialTable")));
        TestRunner.wait.until(driver -> TestRunner.planetariumHome.getPlanetName().contains(string));
        Assert.assertTrue(TestRunner.planetariumHome.getPlanetName().contains(string));
    }

    @When("The User selects planets from drop-down menu")
    public void the_User_selects_planets_from_drop_down_menu() {
        TestRunner.planetariumHome.selectPlanet();
    }

    @When("User enters valid {string} for celestial body to be deleted")
    public void user_enters_valid_for_celestial_body_to_be_deleted(String string) {
        TestRunner.planetariumHome.sendToDeleteInput(string);
    }

    @When("User clicks on the Delete Button")
    public void user_clicks_on_the_Delete_Button() {
        TestRunner.planetariumHome.clickDeleteButton();
    }

    @Then("The planet {string} should be deleted from the Planetarium")
    public void thePlanetPlanetNameShouldBeDeletedFromThePlanetarium(String string) throws InterruptedException {
        TestRunner.wait.withTimeout(Duration.ofSeconds(1));
        Assert.assertFalse(TestRunner.planetariumHome.getPlanetName().contains(string));
    }

    @Given("No planet with name {string} exist")
    public void no_planet_with_name_exist(String string) {
        PlanetEntity planetEntity = new PlanetEntity(string);
        PlanetRepository.deletePlanet(planetEntity);
    }

    @When("User enters invalid {string} for celestial body to be deleted")
    public void user_enters_invalid_for_celestial_body_to_be_deleted(String string) {
        TestRunner.planetariumHome.sendToDeleteInput(string);
    }

    @Then("The user should see error message pop-up")
    public void the_user_should_see_error_message_pop_up() {
        TestRunner.wait.until(ExpectedConditions.alertIsPresent());
        TestRunner.driver.switchTo().alert().accept();
    }

    @Given("Planet with {string} exists")
    public void planet_with_exists(String string) {
        boolean existID = false;
        List<PlanetEntity> planetEntityList = DatabaseScriptRunnerUtility.getAllPlanetInfo();
        for(PlanetEntity planetEntity : planetEntityList){
            if(planetEntity.getId().equals(string))
                existID = true;
        }
        Assert.assertTrue(existID);
    }

    @When("User enters planet {string} for celestial body to be deleted")
    public void user_enters_planet_for_celestial_body_to_be_deleted(String string) {
        TestRunner.planetariumHome.sendToDeleteInput(string);
    }

    @Then("The user should see error, and the planet with ID {string} should not be deleted")
    public void the_user_should_see_error_and_the_planet_with_ID_should_not_be_deleted(String string) {
        boolean existID = false;
        List<PlanetEntity> planetEntityList = DatabaseScriptRunnerUtility.getAllPlanetInfo();
        for(PlanetEntity planetEntity : planetEntityList){
            if(planetEntity.getId().equals(string))
                existID = true;
        }
        Assert.assertTrue(existID);
    }
}
