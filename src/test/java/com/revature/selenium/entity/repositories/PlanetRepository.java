package com.revature.selenium.entity.repositories;

import com.revature.selenium.entity.PlanetEntity;
import com.revature.selenium.entity.utilities.DatabaseScriptRunnerUtility;

import java.util.List;

public class PlanetRepository {

    public static List<Integer> getPlanets(){
        String fileName = "GetPlanets.sql";
        return  DatabaseScriptRunnerUtility.runSQLScript(fileName);
    }

    public static void addPlanet(PlanetEntity planetEntity){
        String fileName = "AddPlanet.sql";
        DatabaseScriptRunnerUtility.runSQLScript(fileName, planetEntity);
    }

    public static void addPlanetWithId(PlanetEntity planetEntity){
        String fileName = "AddPlanetWithId.sql";
        DatabaseScriptRunnerUtility.runSQLScript(fileName, planetEntity);
    }

    public static void deletePlanetWithString(PlanetEntity planetEntity){
        String fileName = "DeletePlanetWithString.sql";
        DatabaseScriptRunnerUtility.runSQLScript(fileName, planetEntity);
    }

    public static void deletePlanetWithId(PlanetEntity planetEntity){
        String fileName = "DeletePlanetWithId.sql";
        DatabaseScriptRunnerUtility.runSQLScript(fileName, planetEntity);
    }
}
