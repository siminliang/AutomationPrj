package com.revature.step;

import com.revature.TestRunner;
import com.revature.entity.PlanetEntity;
import com.revature.repositories.PlanetRepository;
import com.revature.services.LoginService;
import com.revature.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class DeletePlanetSteps {
    private static boolean alertPresent = false;

    public static boolean getAlertPresent(){
        return alertPresent;
    }
    @Given("The User is already logged in")
    public void the_User_is_already_logged_in() {
        Assert.assertTrue(LoginService.loginBatman());
    }

    @Given("Given Planet names {string} exists")
    public void given_Planet_names_exists(String string) {
        PlanetEntity planetEntity = new PlanetEntity(string, "1");
        planetEntity.setDefaultImage();
        PlanetRepository.addPlanet(planetEntity);
        TestRunner.refresh();
        TestRunner.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("celestialTable")));
        TestRunner.wait.until(driver -> TestRunner.planetariumHome.getCelestialTableAsText().contains(string));
        Assert.assertTrue(TestRunner.planetariumHome.getCelestialTableAsText().contains(string));
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
        try {
            TestRunner.wait.until(ExpectedConditions.alertIsPresent());
            TestRunner.driver.switchTo().alert().accept();
            alertPresent = true;
        } catch (TimeoutException ignored){
        }
    }

    @Then("The planet {string} should be deleted from the Planetarium")
    public void the_planet_should_be_deleted_from_the_Planetarium(String string) {
        boolean existID = true;
        List<PlanetEntity> planetEntityList = DatabaseScriptRunnerUtility.getAllPlanetInfo();
        for(PlanetEntity planetEntity : planetEntityList){
            if(planetEntity.getName().equals(string))
                existID = false;
        }
        Assert.assertTrue(existID);
    }

    @Given("No planet with name {string} exist")
    public void no_planet_with_name_exist(String string) {
        PlanetEntity planetEntity = new PlanetEntity(string);
        PlanetRepository.deletePlanetWithString(planetEntity);
    }

    @When("User enters invalid {string} for celestial body to be deleted")
    public void user_enters_invalid_for_celestial_body_to_be_deleted(String string) {
        TestRunner.planetariumHome.sendToDeleteInput(string);
    }

    @Then("The user should see error message pop-up")
    public void the_user_should_see_error_message_pop_up() {
        Assert.assertTrue(alertPresent);
    }

    @Given("Planet with ID {string} exists")
    public void planet_with_ID_exists(String string) {
        PlanetEntity planetEntity = new PlanetEntity("planetDoesExist2", "1");
        planetEntity.setDefaultImage();
        planetEntity.setId(string);
        PlanetRepository.addPlanetWithId(planetEntity);

        boolean existID = false;
        List<PlanetEntity> planetEntityList = DatabaseScriptRunnerUtility.getAllPlanetInfo();
        for(PlanetEntity planetEntity1 : planetEntityList){
            if(planetEntity1.getId().equals(string))
                existID = true;
        }
        Assert.assertTrue(existID);
    }

    @When("User enters planet ID {string} for celestial body to be deleted")
    public void user_enters_planet_for_celestial_body_to_be_deleted(String string) {
        TestRunner.planetariumHome.sendToDeleteInput(string);
    }



    @And("The planet with ID {string} should not be deleted")
    public void thePlanetWithIDShouldNotBeDeleted(String string) {
        boolean existID = false;
        List<PlanetEntity> planetEntityList = DatabaseScriptRunnerUtility.getAllPlanetInfo();
        for(PlanetEntity planetEntity : planetEntityList){
            if(planetEntity.getId().equals(string))
                existID = true;
        }
        Assert.assertTrue(existID);
    }
}
