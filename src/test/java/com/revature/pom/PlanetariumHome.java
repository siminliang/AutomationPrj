package com.revature.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.revature.TestRunner.wait;

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

    public void sendToPlanetNameInput(String planetName){
        planetNameInput.sendKeys(planetName);
    }

    public void clickSubmitButton(){
        submitButton.click();
    }

    public void uploadImage(){
        Path relativePath = Paths.get("src/test/resources/images/planet-1.jpg");
        String absolutePath = relativePath.toAbsolutePath().toString();
        //change to location on your local system
        planetImageInput.sendKeys(absolutePath);
    }

    public String getPlanetName(){

        return celestialTable.getText();
    }

    public void clickLogoutButton(){
        logoutButton.click();
    }
    public Map<String, List<Integer>> viewAllData(){


//      here we need to wait until all elements of "td" tag is not located no other tag can give the desired results
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("td")));

        List<WebElement> tableRows = celestialTable.findElements(By.tagName("tr"));
        Map<String, List<Integer>> tableData = new HashMap<>();

        for(WebElement row: tableRows){
            List<WebElement> cells = row.findElements(By.tagName("td"));


            if (cells.size() >= 2) {
                // Get the first and second cell data
                String firstCell = cells.get(0).getText();
                Integer secondCell = Integer.parseInt(cells.get(1).getText());

                // Add the second cell data to the map, grouped by the first cell data
                tableData.computeIfAbsent(firstCell, k -> new ArrayList<>()).add(secondCell);
            }
        }

        return tableData;
    }
}
