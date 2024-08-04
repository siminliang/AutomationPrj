package com.revature.repositories;

import com.revature.entity.MoonEntity;
import com.revature.entity.PlanetEntity;
import com.revature.entity.UserEntity;
import com.revature.utilities.DatabaseScriptRunnerUtility;

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

    public static void deletePlanet(PlanetEntity planetEntity){
        String fileName = "DeletePlanet.sql";
        DatabaseScriptRunnerUtility.runSQLScript(fileName, planetEntity);
    }
}
