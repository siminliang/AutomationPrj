package com.revature.selenium.services;

import com.revature.SeleniumTestRunner;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginService {
    public boolean login(String username, String password){



        SeleniumTestRunner.planetariumLoginHome.goToPlanetariumLoginHome();
        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Planetarium Login"));

        SeleniumTestRunner.planetariumLoginHome.sendToUsernameInput(username);
        SeleniumTestRunner.planetariumLoginHome.sendToPasswordInput(password);

        SeleniumTestRunner.planetariumLoginHome.clickLoginButton();

        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Home"));

        String planetariumTitle = SeleniumTestRunner.driver.getTitle();
        return planetariumTitle.equals("Home");
    }

    //logs in as Batman
    public static boolean loginBatman(){
        SeleniumTestRunner.planetariumLoginHome.goToPlanetariumLoginHome();
        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Planetarium Login"));

        SeleniumTestRunner.planetariumLoginHome.sendToUsernameInput("Batman");
        SeleniumTestRunner.planetariumLoginHome.sendToPasswordInput("I am the night");

        SeleniumTestRunner.planetariumLoginHome.clickLoginButton();

        SeleniumTestRunner.wait.until(ExpectedConditions.titleIs("Home"));
        String planetariumTitle = SeleniumTestRunner.driver.getTitle();
        return planetariumTitle.equals("Home");
    }
}
