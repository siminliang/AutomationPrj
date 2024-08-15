package com.revature.selenium.repositories;

import com.revature.selenium.entity.MoonEntity;
import com.revature.selenium.utilities.DatabaseScriptRunnerUtility;

import java.util.List;

public class MoonRepository {

    public static List<Integer> getMoons(){
        String fileName = "GetMoons.sql";
        return DatabaseScriptRunnerUtility.runSQLScript(fileName);
    }
    public static void addMoon(MoonEntity moonEntity){
        String fileName = "AddMoon.sql";
        DatabaseScriptRunnerUtility.runSQLScript(fileName, moonEntity);
    }

    public static void addMoonWithId(MoonEntity moonEntity){
        String fileName = "AddMoonWithId.sql";
        DatabaseScriptRunnerUtility.runSQLScript(fileName, moonEntity);
    }

    public static void deleteMoonWithString(MoonEntity moonEntity){
        String fileName = "DeleteMoonWithString.sql";
        DatabaseScriptRunnerUtility.runSQLScript(fileName, moonEntity);
    }
}
