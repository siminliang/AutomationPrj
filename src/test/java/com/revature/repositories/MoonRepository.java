package com.revature.repositories;

import com.revature.entity.MoonEntity;
import com.revature.entity.UserEntity;
import com.revature.utilities.DatabaseScriptRunnerUtility;

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

    public static void deleteMoon(MoonEntity moonEntity){
        String fileName = "DeleteMoon.sql";
        DatabaseScriptRunnerUtility.runSQLScript(fileName, moonEntity);
    }
}
