package com.revature.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.revature.TestRunner.wait;


public class ViewCelestialBodies {
    private WebDriver driver;

    private String url = "http://localhost:8080/planetarium";


    @FindBy(id = "celestialTable")
    private WebElement table;


    public ViewCelestialBodies(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public  Map<String, List<Integer>> viewAllData(){


//      here we need to wait until all elements of "td" tag is not located no other tag can give the desired results
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("td")));

        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
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
