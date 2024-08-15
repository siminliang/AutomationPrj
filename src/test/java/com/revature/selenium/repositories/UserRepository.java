package com.revature.selenium.repositories;

import com.revature.selenium.entity.UserEntity;
import com.revature.selenium.utilities.DatabaseScriptRunnerUtility;

public class UserRepository {

    public static void addUser(UserEntity userEntity){
        String fileName = "AddUser.sql";
        DatabaseScriptRunnerUtility.runSQLScript(fileName, userEntity);
    }

    public static void deleteUser(UserEntity userEntity){
        String fileName = "DeleteUser.sql";
        DatabaseScriptRunnerUtility.runSQLScript(fileName, userEntity);
    }

}
