package com.revature.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// This is the pom (page object model) for the Planetarium Login page, our scenarios will all
// make use of this pom
public class PlanetariumLoginHome {

    private WebDriver driver;

    private String url = "http://localhost:8080/";

    private String planetariumUrl = "http://localhost:8080/planetarium";

    // Locate the username input field
    @FindBy(id = "usernameInput")
    private WebElement usernameInput;

    // Locate the password input field
    @FindBy(id = "passwordInput")
    private WebElement passwordInput;

    // Locate the login button
    @FindBy(xpath = "//input[@type='submit' and @value='Login']")
    private WebElement loginButton;

    // Locate the create an account link
    @FindBy(linkText = "Create an Account")
    private WebElement createAccountLink;


    public PlanetariumLoginHome(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void goToPlanetariumLoginHome(){
        driver.get(url);
    }

    public void goToPlanetariumMainPage(){
        driver.get(planetariumUrl);
    }

    public void sendToUsernameInput(String username){
        usernameInput.sendKeys(username);
    }

    public void sendToPasswordInput(String password){
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton(){
        loginButton.click();
    }

    public void clickCreateAccountLink(){
        createAccountLink.click();
    }



    /*
        In this example we find the link by providing an id to the method, and then the driver
        finds the element when the method is called, but you could instead make a field to
        represent each of the links, and then based on the string value provided call the click
        method on the appropriate link

    public void clickLanguageLink(String id){
        WebElement link =  driver.findElement(By.id(id));
        link.click();
    }

    */

}
