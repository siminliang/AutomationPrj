package com.revature.selenium.entity.step;

import com.revature.SeleniumTestRunner;
import com.revature.selenium.entity.MoonEntity;
import com.revature.selenium.entity.PlanetEntity;
import com.revature.selenium.entity.repositories.MoonRepository;
import com.revature.selenium.entity.repositories.PlanetRepository;
import com.revature.selenium.entity.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Objects;

public class AddMoonSteps {


    @Given("No Planet or Moon with name {string} in planetarium")
    public void no_planet_or_moon_with_name_in_planetarium(String string) {
        // Deletes planet/moon from the planetarium if it already exists
        PlanetEntity planetEntity = new PlanetEntity(string);
        MoonEntity moonEntity = new MoonEntity(string);
        PlanetRepository.deletePlanetWithString(planetEntity);
        MoonRepository.deleteMoonWithString(moonEntity);
        SeleniumTestRunner.refresh();

    }
    @Given("The ID of the Planet {string} that the moon orbiting does exist in Planetarium")
    public void moon_orbiting_planet_exists(String string) {
        PlanetEntity planetEntity = new PlanetEntity("defaultPlanet", "1");
        planetEntity.setDefaultImage();
        planetEntity.setId(string);
        PlanetRepository.addPlanetWithId(planetEntity);
        SeleniumTestRunner.refresh();
        //boolean exists = SeleniumTestRunner.planetariumHome.doesPlanetExist(string);
        //Assert.assertTrue("Planet with ID " + string + " should exist.", exists);
    }

    @Given("The ID of the Planet {string} does not exist in the Planetarium")
    public void id_of_orbited_planet_does_not_exist(String string) {
        PlanetEntity planetEntity = new PlanetEntity("defaultPlanet", "1");
        planetEntity.setDefaultImage();
        planetEntity.setId(string);
        PlanetRepository.deletePlanetWithId(planetEntity);
        SeleniumTestRunner.refresh();
        //boolean exists = SeleniumTestRunner.planetariumHome.doesPlanetExist(string);
        //Assert.assertFalse("Planet with ID " + string + " should exist.", exists);
    }


    @When("The User selects moon from the drop-down menu")
    public void the_User_selects_moon_from_the_drop_down_menu() {
        SeleniumTestRunner.planetariumHome.selectMoon();
    }

    @When("The User enters {string} for moon name")
    public void the_User_enters_for_moon_name(String string) {
        SeleniumTestRunner.planetariumHome.sendToMoonNameInput(string);
    }

    @When("The User enters {string} for the planet that the moon is orbiting")
    public void the_User_enters_orbited_planet_id(String string) {
        SeleniumTestRunner.planetariumHome.sendToOrbitedPlanetInput(string);
    }

    @When("{string} The User selects an image from file explorer for moon image")
    public void the_User_selects_an_image_from_filesystem_for_moon_image(String image) {
        if (!image.isEmpty()) {
            SeleniumTestRunner.planetariumHome.uploadMoonImage();
        }
    }


    @Then("The Moon {string} should be added to planetarium")
    public void the_moon_should_be_added_to_the_planetarium(String string) {
        //SeleniumTestRunner.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("celestialTable")));
        //SeleniumTestRunner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("celestialTable")));
        //SeleniumTestRunner.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("celestialTable")));
        //getPlanetName returns all text in celestialTable, so it should contain moon name as well
        //SeleniumTestRunner.wait.until(driver -> SeleniumTestRunner.planetariumHome.getCelestialTableAsText().contains(string));
        try{
            SeleniumTestRunner.wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertFalse(SeleniumTestRunner.planetariumHome.isAlertPresent());
            SeleniumTestRunner.driver.switchTo().alert().accept();
        }
        catch (TimeoutException e){
            //Assert.fail();
            //SeleniumTestRunner.wait.withTimeout(Duration.ofSeconds(1));
            //Assert.assertTrue(SeleniumTestRunner.planetariumHome.getCelestialTableAsText().contains(string));
            boolean existID = false;
            List<MoonEntity> moonEntityList = DatabaseScriptRunnerUtility.getAllMoonInfo();
            for(MoonEntity moonEntity : moonEntityList){
                if(moonEntity.getName().equals(string)) {
                    existID = true;
                }
            }
            Assert.assertTrue(existID);
        }
    }

    @Then("The Moon {string} should not be added to planetarium")
    public void the_moon_should_not_be_added_to_the_planetarium(String string) {
        try{
            SeleniumTestRunner.wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertTrue(SeleniumTestRunner.planetariumHome.isAlertPresent());
            SeleniumTestRunner.driver.switchTo().alert().accept();
        }
        catch (TimeoutException e){
            //Assert.fail();
            //SeleniumTestRunner.wait.withTimeout(Duration.ofSeconds(1));
            //Assert.assertFalse(SeleniumTestRunner.planetariumHome.getCelestialTableAsText().contains(string));
            int count = 0;
            boolean existID = true;
            List<MoonEntity> moonEntityList = DatabaseScriptRunnerUtility.getAllMoonInfo();
            for(MoonEntity moonEntity : moonEntityList) {
                if (moonEntity.getName().equals(string)) {
                    count++;
                    existID = false;
                }
            }
            if (Objects.equals(string, "AlreadyAddedMoonInTheDatabase!")){
                Assert.assertEquals(1, count);
            } else{
                Assert.assertTrue(existID);
            }
        }
    }

    @Then("The Moon {string} should be owned by {string}")
    public void the_Moon_should_be_owned_by(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        try{
            SeleniumTestRunner.wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertFalse(SeleniumTestRunner.planetariumHome.isAlertPresent());
            SeleniumTestRunner.driver.switchTo().alert().accept();
        }
        catch (TimeoutException e){
            boolean existID = false;
            List<MoonEntity> moonEntityList = DatabaseScriptRunnerUtility.getAllMoonInfo();
            for(MoonEntity moonEntity : moonEntityList){
                if(moonEntity.getName().equals(string)) {
                    if(moonEntity.getOwner().equals(string2)){
                        existID = true;
                    }
                }
            }
            Assert.assertTrue(existID);
        }
    }
}
