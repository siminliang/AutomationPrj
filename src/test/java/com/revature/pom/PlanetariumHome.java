package com.revature.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PlanetariumHome {

    private WebDriver driver;

    private String url = "http://localhost:8080/planetarium";

    // Locate the logout button
    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    // Locate the delete celestial body input field
    @FindBy(id = "deleteInput")
    private WebElement deleteCelestialInput;

    // Locate the button to delete celestial bodies
    @FindBy(id = "deleteButton")
    private WebElement deleteCelestialButton;

    // Locate the dropdown for selecting planet and moons
    @FindBy(id = "locationSelect")
    private WebElement dropDown;

    // Locate the table for viewing planets and moons
    @FindBy(id = "celestialTable")
    private WebElement celestialTable;







    public PlanetariumHome(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goToPlanetariumHomePage(){
        driver.get(url);
    }


    public void sendCelestialDeleteInput(String username){
        deleteCelestialInput.sendKeys(username);
    }


    public void clickCelestialDeleteButton(){
        deleteCelestialButton.click();
    }

    public void clickLogoutButton(){
        logoutButton.click();
    }
}
