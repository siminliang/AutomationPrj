package com.revature.selenium.step;

import com.revature.SeleniumTestRunner;
import com.revature.selenium.entity.PlanetEntity;
import com.revature.selenium.entity.UserEntity;
import com.revature.selenium.repositories.MoonRepository;
import com.revature.selenium.repositories.PlanetRepository;
import com.revature.selenium.repositories.UserRepository;
import com.revature.selenium.services.LoginService;
import com.revature.selenium.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ViewCeletialBodiesSteps {
    private static boolean alertPresent = false;
    private LoginService loginService;
    private UserEntity testUser;
    private PlanetEntity planetEntity;
    public ViewCeletialBodiesSteps(){
        this.loginService = new LoginService();
    }

    @Given("The User is already logged in with {string} and  {string}")
    public void the_User_is_already_logged_in_with_and(String username, String password) {
        testUser = new UserEntity(username, password);
        UserRepository.addUser(testUser);

        boolean checkLog = loginService.login(username, password);
        Assert.assertTrue(username + "is logged in", checkLog);
    }

    @Given("The planet {string} is already there")
    public  void the_planet_is_alreay_there(String planetname){
        planetEntity = new PlanetEntity(planetname, "1");
        planetEntity.setDefaultImage();
        PlanetRepository.deletePlanetWithString(planetEntity);
        PlanetRepository.addPlanet(planetEntity);
        SeleniumTestRunner.refresh();
        SeleniumTestRunner.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("celestialTable")));
        SeleniumTestRunner.wait.until(driver -> SeleniumTestRunner.planetariumHome.getCelestialTableAsText().contains(planetname));
        Assert.assertTrue(SeleniumTestRunner.planetariumHome.getCelestialTableAsText().contains(planetname));
    }

    @When("User is redirected to the Planetarium")
    public void the_User_is_redirected_to_the_Planetarium() {
        // Write code here that turns the phrase above into concrete actions
        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Home"));
        Assert.assertEquals("Home", SeleniumTestRunner.driver.getTitle());
    }

    @When("User add new planet {string}")
    public  void user_add_new_planet(String planetname){
        planetEntity = new PlanetEntity(planetname);
        SeleniumTestRunner.planetariumHome.selectPlanet();
        SeleniumTestRunner.planetariumHome.sendToPlanetNameInput(planetname);
        SeleniumTestRunner.planetariumHome.uploadImage();
        SeleniumTestRunner.planetariumHome.clickSubmitButton();
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
                if(planetEntity.getName().equals(planetname)) {
                    existID = true;
                }
            }
            Assert.assertTrue(existID);
        }
    }
    @When("User delete new planet {string}")
    public void user_delete_new_planet(String planet){
        SeleniumTestRunner.planetariumHome.sendToDeleteInput(planet);
        SeleniumTestRunner.planetariumHome.clickDeleteButton();
        try {
            SeleniumTestRunner.wait.until(ExpectedConditions.alertIsPresent());
            SeleniumTestRunner.driver.switchTo().alert().accept();
            alertPresent = true;
        } catch (TimeoutException ignored){
        }
    }
    @Then("User see all the available celestial Bodies.")
    public void user_sees_all_the_bodies(){
        Map<String, List<Integer>> tableData  = SeleniumTestRunner.planetariumHome.viewAllData();

//        Select id from planets and moons and compare them with table with the data from table

        List<Integer> planetIds = PlanetRepository.getPlanets();
        List<Integer> moonIds = MoonRepository.getMoons();
        boolean isMoonSame = moonIds.equals(tableData.getOrDefault("moon", new ArrayList<>()));
        boolean isPlanetSame = planetIds.equals(tableData.getOrDefault("planet",new ArrayList<>()));

        Assert.assertTrue(isMoonSame && isPlanetSame);
    }

    @After
    public void tearDown(){

        if(testUser != null){
            UserRepository.deleteUser(testUser);
        }
        PlanetRepository.deletePlanetWithString(planetEntity);
    }
}
