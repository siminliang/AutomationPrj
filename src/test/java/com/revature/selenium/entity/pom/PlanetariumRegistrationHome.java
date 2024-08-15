package com.revature.selenium.entity.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PlanetariumRegistrationHome {
    private WebDriver driver;

    private String url = "http://localhost:8080/register";

    // Locate the username input field
    @FindBy(id = "usernameInput")
    private WebElement usernameInput;

    // Locate the password input field
    @FindBy(id = "passwordInput")
    private WebElement passwordInput;

    // Locate the create button
    @FindBy(xpath = "//input[@type='submit' and @value='Create']")
    private WebElement createButton;

    public PlanetariumRegistrationHome(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goToPlanetariumRegistrationHome(){
        driver.get(url);
    }

    public void sendToUsernameInput(String username){
        usernameInput.sendKeys(username);
    }

    public void sendToPasswordInput(String password){
        passwordInput.sendKeys(password);
    }

    public void clickCreateButton(){
        createButton.click();
    }
}
