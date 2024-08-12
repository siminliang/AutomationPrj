package com.revature.step;

import com.revature.TestRunner;
import com.revature.entity.MoonEntity;
import com.revature.entity.PlanetEntity;
import com.revature.repositories.MoonRepository;
import com.revature.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class DeleteMoonSteps {

    @Given("Moon name {string} exist")
    public void moon_name_exist(String string) {
        MoonEntity moonEntity = new MoonEntity(string, "1");
        moonEntity.setDefaultImage();
        MoonRepository.addMoon(moonEntity);
    }

    @When("The User selects moon from drop-down menu")
    public void the_User_selects_moon_from_drop_down_menu() {
        TestRunner.planetariumHome.selectMoon();
    }

    @When("User enters valid moon name {string} for celestial body to be deleted")
    public void user_enters_valid_moon_name_for_celestial_body_to_be_deleted(String string) {
        TestRunner.planetariumHome.sendToDeleteInput(string);
    }

    @Then("The moon {string} should be deleted from the planetarium")
    public void the_moon_should_be_deleted_from_the_planetarium(String string) {
        boolean existID = true;
        List<MoonEntity> moonEntityList = DatabaseScriptRunnerUtility.getAllMoonInfo();
        for(MoonEntity moonEntity : moonEntityList){
            if(moonEntity.getName().equals(string))
                existID = false;
        }
        Assert.assertTrue(existID);
    }

    @Given("There is no Moon named {string} in planetarium")
    public void there_is_no_Moon_named_in_planetarium(String string) {
        MoonEntity moonEntity = new MoonEntity(string);
        MoonRepository.deleteMoonWithString(moonEntity);
    }

    @When("User enters invalid {string}")
    public void user_enters_invalid(String string) {
        TestRunner.planetariumHome.sendToDeleteInput(string);
    }

    @Given("Moon with ID {string} exists")
    public void moon_with_ID_exists(String string) {
        MoonEntity moonEntity = new MoonEntity("checkingIdDeletionMoon", "1");
        moonEntity.setDefaultImage();
        moonEntity.setId(string);
        MoonRepository.addMoon(moonEntity);
        TestRunner.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("celestialTable")));
        //getPlanetName returns all text in celestialTable, so it should contain moon name as well
        TestRunner.wait.until(driver -> TestRunner.planetariumHome.getCelestialTableAsText().contains(string));
        Assert.assertTrue(TestRunner.planetariumHome.getCelestialTableAsText().contains(string));
    }

    @When("User enters moon id {string} for celestial body to be deleted")
    public void user_enters_moon_id_for_celestial_body_to_be_deleted(String string) {
        TestRunner.planetariumHome.sendToDeleteInput(string);
    }

    @Then("The user should see error")
    public void the_user_should_see_error_and_the_moon_with_ID_should_not_be_deleted() {
        Assert.assertTrue(DeletePlanetSteps.getAlertPresent());
    }

    @And("The moon with ID {string} should not be deleted")
    public void theMoonWithIDShouldNotBeDeleted(String string) {
        boolean existID = false;
        List<MoonEntity> moonEntityList = DatabaseScriptRunnerUtility.getAllMoonInfo();
        for(MoonEntity moonEntity : moonEntityList){
            if(moonEntity.getId().equals(string))
                existID = true;
        }
        Assert.assertTrue(existID);
    }
}
