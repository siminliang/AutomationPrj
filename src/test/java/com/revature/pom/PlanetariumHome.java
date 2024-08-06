package com.revature.pom;

import io.cucumber.java.eo.Se;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PlanetariumHome {
    private WebDriver driver;

    private String url = "http://localhost:8080/planetarium";

    // Locate the logout button
    @FindBy(id = "logoutButton")
    private WebElement logoutButton;


    //locate the drop-down menu
    @FindBy(id = "locationSelect")
    private WebElement dropDownMenu;

    //locate planet name input field
    @FindBy(id = "planetNameInput")
    private WebElement planetNameInput;

    //locate choose file button
    @FindBy(id = "planetImageInput")
    private WebElement planetImageInput;

    //locate moon name input field
    @FindBy(id = "moonNameInput")
    private WebElement moonNameInput;

    //located the orbited planet id field
    @FindBy(id = "orbitedPlanetInput")
    private  WebElement orbitedPlanetInput;



    //locate submit button
    @FindBy(className = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "celestialTable")
    private WebElement celestialTable;

    @FindBy(xpath = "//*[@id=\"celestialTable\"]/tbody/tr[2]/td[3]")
    private WebElement planetName;

    public PlanetariumHome(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goToPlanetariumHomePage(){
        driver.get(url);
    }

    public void selectPlanet(){
        Select select = new Select(dropDownMenu);
        select.selectByValue("planet");
    }

    public void selectMoon() {
        Select select = new Select(dropDownMenu);
        select.selectByValue("moon");
    }

    public void sendToPlanetNameInput(String planetName){
        planetNameInput.sendKeys(planetName);
    }

    public void sendToMoonNameInput(String moonName) {
        moonNameInput.sendKeys(moonName);
    }

    public void sendToOrbitedPlanetInput(String orbitedPlanet) {
        orbitedPlanetInput.sendKeys(orbitedPlanet);
    }


    public void clickSubmitButton(){
        submitButton.click();
    }

    public void uploadImage(){
        //change to location on your local system
        planetImageInput.sendKeys("C:\\Users\\Thanh\\Downloads\\Saturn.jpg");
    }

    public String getPlanetName(){
        System.out.println(celestialTable.getText());
        return celestialTable.getText();
    }

    public String getMoonName() {
        System.out.println((celestialTable.getText()));
        return celestialTable.getText();
    }
    public void clickLogoutButton(){
        logoutButton.click();
    }

}
