package com.revature.selenium.step;

import com.revature.SeleniumTestRunner;
import com.revature.selenium.entity.PlanetEntity;
import com.revature.selenium.entity.UserEntity;
import com.revature.selenium.repositories.PlanetRepository;
import com.revature.selenium.repositories.UserRepository;
import com.revature.selenium.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Objects;

public class AddPlanetsSteps {

    @Given("The User is already log on")
    public void the_User_is_already_log_on() {
        // All the steps required to log the user in
        UserEntity userEntity = new UserEntity("Lisan", "al-gaib");
        UserRepository.addUser(userEntity);
        SeleniumTestRunner.planetariumLoginHome.goToPlanetariumLoginHome();
        SeleniumTestRunner.planetariumLoginHome.sendToUsernameInput("Lisan");
        SeleniumTestRunner.planetariumLoginHome.sendToPasswordInput("al-gaib");
        SeleniumTestRunner.planetariumLoginHome.clickLoginButton();
        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Home"));
    }

    @Given("No planet with name {string} in planetarium")
    public void no_planet_with_name_in_planetarium(String string) {
        // Deletes planet from the planetarium if it already exists
        PlanetEntity planetEntity = new PlanetEntity(string);
        PlanetRepository.deletePlanetWithString(planetEntity);
        SeleniumTestRunner.refresh();
    }

    @Given("Planet with name {string} already exists")
    public void planetWithNameAlreadyExists(String string) {
        PlanetEntity planetEntity = new PlanetEntity("checkingIdDeletionMoon", "1");
        planetEntity.setDefaultImage();;
        PlanetRepository.deletePlanetWithString(planetEntity); // make sure we are not creating duplicates
        PlanetRepository.addPlanet(planetEntity);
    }

    @When("The User selects planets from the drop-down menu")
    public void the_User_selects_planets_from_the_drop_down_menu() {
        SeleniumTestRunner.planetariumHome.selectPlanet();
    }

    @When("The User enters {string} for planet name")
    public void the_User_enters_for_planet_name(String string) {
        SeleniumTestRunner.planetariumHome.sendToPlanetNameInput(string);
    }

    @When("{string} The User selects an image from filesystem for planet image")
    public void the_User_selects_an_image_from_filesystem_for_planet_image(String string) {
        if(string.equals("true")){
            SeleniumTestRunner.planetariumHome.uploadImage();
        }
    }

    @When("The User clicks on the submit button")
    public void the_User_clicks_on_the_submit_button() {
        SeleniumTestRunner.planetariumHome.clickSubmitButton();
    }

    @Then("The planet {string} should be added to the planetarium")
    public void the_planet_should_be_added_to_the_planetarium(String string) {
        //SeleniumTestRunner.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("celestialTable")));
        //SeleniumTestRunner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("celestialTable")));
        //SeleniumTestRunner.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("celestialTable")));
        //getPlanetName returns all text in celestialTable, so it should contain moon name as well
        //SeleniumTestRunner.wait.until(driver -> SeleniumTestRunner.planetariumHome.getCelestialTableAsText().contains(string));
        //SeleniumTestRunner.wait..until(ExpectedConditions.visibilityOfElementLocated(By.tagName("td")));
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
            List<PlanetEntity> planetEntityList = DatabaseScriptRunnerUtility.getAllPlanetInfo();
            for(PlanetEntity planetEntity : planetEntityList){
                if(planetEntity.getName().equals(string)) {
                    existID = true;
                }
            }
            Assert.assertTrue(existID);
        }

    }

    @Then("The planet {string} should not be added to the planetarium")
    public void the_planet_should_not_be_added_to_the_planetarium(String string) {
        try{
            SeleniumTestRunner.wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertTrue(SeleniumTestRunner.planetariumHome.isAlertPresent());
            SeleniumTestRunner.driver.switchTo().alert().accept();
        }
        catch (TimeoutException e) {
            //Assert.fail();
            //SeleniumTestRunner.wait.withTimeout(Duration.ofSeconds(1));
            //Assert.assertFalse(SeleniumTestRunner.planetariumHome.getCelestialTableAsText().contains(string));
            int count = 0;
            boolean existID = true;
            List<PlanetEntity> planetEntityList = DatabaseScriptRunnerUtility.getAllPlanetInfo();
            for (PlanetEntity planetEntity : planetEntityList) {
                if (planetEntity.getName().equals(string)){
                    count++;
                    existID = false;
                }
            }
            if (Objects.equals(string, "AlreadyAddedPlanetInDatabase!!")){
                Assert.assertEquals(1, count);
            } else{
                Assert.assertTrue(existID);
            }
        }
    }

    @Then("The planet {string} should be owned by the User that Added it")
    public void the_planet_should_be_owned_by_the_User_that_Added_it(String string) {
        // Write code here that turns the phrase above into concrete actions
        UserEntity userEntity = new UserEntity("Lisan", "al-gaib");
        int userID = DatabaseScriptRunnerUtility.getUserId(userEntity);

        try{
            SeleniumTestRunner.wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertFalse(SeleniumTestRunner.planetariumHome.isAlertPresent());
            SeleniumTestRunner.driver.switchTo().alert().accept();
        }
        catch (TimeoutException e){
            boolean existID = false;
            List<PlanetEntity> planetEntityList = DatabaseScriptRunnerUtility.getAllPlanetInfo();
            for(PlanetEntity planetEntity : planetEntityList){
                if(planetEntity.getName().equals(string)) {
                    if(planetEntity.getOwner().equals(Integer.toString(userID))){
                        existID = true;
                    }
                }
            }
            Assert.assertTrue(existID);
        }
    }


}
