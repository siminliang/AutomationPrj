package com.revature.step;

import com.revature.TestRunner;
import com.revature.entity.MoonEntity;
import com.revature.entity.PlanetEntity;
import com.revature.entity.UserEntity;
import com.revature.repositories.MoonRepository;
import com.revature.repositories.PlanetRepository;
import com.revature.services.LoginService;
import com.revature.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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
        TestRunner.refresh();

    }
    @Given("The ID of the Planet {string} that the moon orbiting does exist in Planetarium")
    public void moon_orbiting_planet_exists(String string) {
        PlanetEntity planetEntity = new PlanetEntity("defaultPlanet", "1");
        planetEntity.setDefaultImage();
        planetEntity.setId(string);
        PlanetRepository.addPlanetWithId(planetEntity);
        TestRunner.refresh();
        //boolean exists = TestRunner.planetariumHome.doesPlanetExist(string);
        //Assert.assertTrue("Planet with ID " + string + " should exist.", exists);
    }

    @Given("The ID of the Planet {string} does not exist in the Planetarium")
    public void id_of_orbited_planet_does_not_exist(String string) {
        PlanetEntity planetEntity = new PlanetEntity("defaultPlanet", "1");
        planetEntity.setDefaultImage();
        planetEntity.setId(string);
        PlanetRepository.deletePlanetWithId(planetEntity);
        TestRunner.refresh();
        //boolean exists = TestRunner.planetariumHome.doesPlanetExist(string);
        //Assert.assertFalse("Planet with ID " + string + " should exist.", exists);
    }


    @When("The User selects moon from the drop-down menu")
    public void the_User_selects_moon_from_the_drop_down_menu() {
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

    @When("{string} The User selects an image from file explorer for moon image")
    public void the_User_selects_an_image_from_filesystem_for_moon_image(String image) {
        if (!image.isEmpty()) {
            TestRunner.planetariumHome.uploadMoonImage();
        }
    }


    @Then("The Moon {string} should be added to planetarium")
    public void the_moon_should_be_added_to_the_planetarium(String string) {
        //TestRunner.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("celestialTable")));
        //TestRunner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("celestialTable")));
        //TestRunner.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("celestialTable")));
        //getPlanetName returns all text in celestialTable, so it should contain moon name as well
        //TestRunner.wait.until(driver -> TestRunner.planetariumHome.getCelestialTableAsText().contains(string));
        try{
            TestRunner.wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertFalse(TestRunner.planetariumHome.isAlertPresent());
            TestRunner.driver.switchTo().alert().accept();
        }
        catch (TimeoutException e){
            //Assert.fail();
            //TestRunner.wait.withTimeout(Duration.ofSeconds(1));
            //Assert.assertTrue(TestRunner.planetariumHome.getCelestialTableAsText().contains(string));
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
            TestRunner.wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertTrue(TestRunner.planetariumHome.isAlertPresent());
            TestRunner.driver.switchTo().alert().accept();
        }
        catch (TimeoutException e){
            //Assert.fail();
            //TestRunner.wait.withTimeout(Duration.ofSeconds(1));
            //Assert.assertFalse(TestRunner.planetariumHome.getCelestialTableAsText().contains(string));
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
            TestRunner.wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertFalse(TestRunner.planetariumHome.isAlertPresent());
            TestRunner.driver.switchTo().alert().accept();
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
