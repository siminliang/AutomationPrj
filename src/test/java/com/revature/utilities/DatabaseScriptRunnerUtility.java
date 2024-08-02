package com.revature.utilities;

import com.revature.entity.MoonEntity;
import com.revature.entity.PlanetEntity;
import com.revature.entity.UserEntity;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DatabaseScriptRunnerUtility {

    public static void runSQLScript(String sqlFileName, Object entity) {
        Path sqlPath = Paths.get("src/test/resources/scripts/" + sqlFileName);
        try {
            try (
                    Connection connection = DatabaseConnectorUtility.createConnection();
                    Stream<String> lines = Files.lines(sqlPath)
            ) {
                connection.setAutoCommit(false);
                StringBuilder sqlBuilder = new StringBuilder();
                lines.forEach(line -> sqlBuilder.append(line).append("\n"));
                String sql = sqlBuilder.toString();

                // Prepare the statement
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    setParameters(preparedStatement, sqlFileName, entity);
                    preparedStatement.executeUpdate();
                }

                connection.commit();
            }
        } catch (SQLException | IOException exception) {
            System.out.println(exception.getMessage());
        }
    }



    private static void setParameters(PreparedStatement preparedStatement, String sqlFileName, Object entity) throws SQLException, FileNotFoundException {
        if( entity instanceof UserEntity) {
            UserEntity userEntity = (UserEntity) entity;
            switch(sqlFileName){
                case "AddUser.sql":
                    DatabaseRegistrationPreparedStatements.addUser(preparedStatement, userEntity);
                    break;
                case "DeleteUser.sql":
                    DatabaseRegistrationPreparedStatements.deleteUser(preparedStatement, userEntity);
                    break;
            }
        }

        if( entity instanceof PlanetEntity) {
            PlanetEntity planetEntity = (PlanetEntity) entity;
            switch(sqlFileName){
                case "AddPlanet.sql":
                    DatabasePlanetPreparedStatements.addPlanet(preparedStatement, planetEntity);
                    break;
                case "DeletePlanet.sql":
                    DatabasePlanetPreparedStatements.deletePlanet(preparedStatement, planetEntity);
                    break;
            }
        }

        if( entity instanceof MoonEntity) {
            MoonEntity moonEntity = (MoonEntity) entity;
            switch(sqlFileName){
                case "AddMoon.sql":
                    DatabaseMoonPreparedStatements.addMoon(preparedStatement, moonEntity);
                    break;
                case "DeleteMoon.sql":
                    DatabaseMoonPreparedStatements.deleteMoon(preparedStatement, moonEntity);
                    break;
            }
        }
    }
}