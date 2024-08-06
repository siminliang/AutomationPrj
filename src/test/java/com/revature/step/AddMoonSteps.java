package com.revature.step;

import com.revature.TestRunner;
import com.revature.entity.MoonEntity;
import com.revature.entity.PlanetEntity;
import com.revature.entity.UserEntity;
import com.revature.repositories.MoonRepository;
import com.revature.repositories.PlanetRepository;
import com.revature.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.revature.pom.PlanetariumHome;

public class AddMoonSteps {

    @Given("The User is already log on.")
    public void the_User_is_already_log_on() {
        // All the steps required to log the user in
        TestRunner.planetariumLoginHome.goToPlanetariumLoginHome();
        TestRunner.planetariumLoginHome.sendToUsernameInput("thanh");
        TestRunner.planetariumLoginHome.sendToPasswordInput("123");
        TestRunner.planetariumLoginHome.clickLoginButton();
        TestRunner.wait.until(ExpectedConditions.titleIs("Home"));
    }

    @Given("No Planet or Moon with name {string} in planetarium")
    public void no_planet_with_name_in_planetarium(String string) {
        // Deletes planet/moon from the planetarium if it already exists
        PlanetEntity planetEntity = new PlanetEntity(string);
        MoonEntity moonEntity = new MoonEntity(string);
        PlanetRepository.deletePlanet(planetEntity);
        MoonRepository.deleteMoon(moonEntity);

    }
//    @Given("The {string} Planet that the moon orbiting in Planetarium")
//    public void moon_orbiting_planet_exists(String string) {
//
//    }

    @When("The User selects planets from the drop-down menu")
    public void the_User_selects_planets_from_the_drop_down_menu() {
        TestRunner.planetariumHome.selectPlanet();
    }

    @When("The User selects moon from the drop-down menu")
    public void the_User_selects_planets_from_the_drop_down_menu() {
        TestRunner.planetariumHome.selectMoon();
    }

    @When("The User enters {string} for moon name")
    public void the_User_enters_for_moon_name(String string) {
        TestRunner.planetariumHome.sendToMoonNameInput(string);
    }

    @When("The User enters {string} for the planet that the moon is orbiting")
    public void the_User_enters_orbited_planet_id(String string) {
        TestRunner.planetariumHome.sendToOrbitedPlanetInput(string);
    }

    @When("{string} The User selects an image from filesystem for moon image")
    public void the_User_selects_an_image_from_filesystem_for_moon_image(String string) {
        if(string.equals("true")){
            TestRunner.planetariumHome.uploadImage();
        }
    }



    @When("The User clicks on the submit button")
    public void the_User_clicks_on_the_submit_button() {
        TestRunner.planetariumHome.clickSubmitButton();
    }

    @Then("The Moon {string} should be added to planetarium ")
    public void the_moon_should_be_added_to_the_planetarium(String string) {
        TestRunner.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("celestialTable")));
        Assert.assertTrue(TestRunner.planetariumHome.getMoonName().contains(string));
    }

    @Then("The Moon {string} should not be added to planetarium ")
    public void the_moon_should_not_be_added_to_the_planetarium(String string) {
        TestRunner.wait.until(ExpectedConditions.alertIsPresent());
        TestRunner.driver.switchTo().alert().accept();
        TestRunner.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("celestialTable")));
        Assert.assertFalse(TestRunner.planetariumHome.getMoonName().contains(string));
    }
}
