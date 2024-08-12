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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseScriptRunnerUtility {

    public static List<Integer> runSQLScript(String sqlFileName){
        Path sqlPath = Paths.get("src/test/resources/scripts/" + sqlFileName);
        List<Integer> resultIds = new ArrayList<>();
        try {

            try (
                    Connection connection = DatabaseConnectorUtility.createConnection();
                    Stream<String> lines = Files.lines(sqlPath)
            ) {
                String sql = lines.collect(Collectors.joining("\n"));
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(sql);

                while(resultSet.next()){
                        resultIds.add(Integer.parseInt(resultSet.getString("id")));

                }


                connection.commit();
            }

        } catch (SQLException | IOException exception) {
            System.out.println(exception.getMessage());
        }
        return resultIds;
    }


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
                case "AddPlanetWithId.sql":
                    DatabasePlanetPreparedStatements.addPlanetWithId(preparedStatement, planetEntity);
                    break;
                case "DeletePlanetWithString.sql":
                    DatabasePlanetPreparedStatements.deletePlanetWithString(preparedStatement, planetEntity);
                    break;
                case "DeletePlanetWithId.sql":
                    DatabasePlanetPreparedStatements.deletePlanetWithId(preparedStatement, planetEntity);
                    break;
            }
        }

        if( entity instanceof MoonEntity) {
            MoonEntity moonEntity = (MoonEntity) entity;
            switch(sqlFileName){
                case "AddMoon.sql":
                    DatabaseMoonPreparedStatements.addMoon(preparedStatement, moonEntity);
                    break;
                case "AddMoonWithId.sql":
                    DatabaseMoonPreparedStatements.addMoonWithId(preparedStatement, moonEntity);
                    break;
                case "DeleteMoonWithString.sql":
                    DatabaseMoonPreparedStatements.deleteMoonWithString(preparedStatement, moonEntity);
                    break;
            }
        }
    }

    public static void addTempPlanet(PlanetEntity planetEntity) {
        String sql = "INSERT INTO planets (name, ownerId) VALUES (?,?)";
        try(Connection connection = DatabaseConnectorUtility.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, planetEntity.getName());
            preparedStatement.setInt(2, 1);

            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    public static List<PlanetEntity> getAllPlanetInfo(){
        String sql = "SELECT * FROM planets";
        try(Connection connection = DatabaseConnectorUtility.createConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<PlanetEntity> entities = new ArrayList<>();
            while(resultSet.next()){
                PlanetEntity planetEntity = new PlanetEntity();
                planetEntity.setId(resultSet.getString("id"));
                planetEntity.setName(resultSet.getString("name"));
                planetEntity.setOwner(resultSet.getString("ownerId"));
                entities.add(planetEntity);
            }
            return entities;
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }


    //add temp moon and returns its id
    public static int addTempMoon(MoonEntity moonEntity){
        String sql = "INSERT INTO moons (name, myPlanetId) VALUES (?,?)";
        int generatedId = -1;
        try(Connection connection = DatabaseConnectorUtility.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, moonEntity.getName());
            preparedStatement.setInt(2, 0);

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return generatedId;
    }

    public static List<MoonEntity> getAllMoonInfo() {
        String sql = "SELECT * FROM moons";
        try (Connection connection = DatabaseConnectorUtility.createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<MoonEntity> entities = new ArrayList<>();
            while (resultSet.next()) {
                MoonEntity moonEntity = new MoonEntity();
                //getString(column number) or (column name)
                moonEntity.setId(resultSet.getString("id"));
                moonEntity.setName(resultSet.getString("name"));
                moonEntity.setOwner(resultSet.getString("myPlanetId"));
                entities.add(moonEntity);
            }
            return entities;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static int getUserId(UserEntity userEntity){
        String sql = "SELECT id FROM users WHERE username = ?";
        int generatedId = -1;
        try (Connection connection = DatabaseConnectorUtility.createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userEntity.getUsername());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    generatedId = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return generatedId;
    }

    public static List<PlanetEntity> getAllPlanetId(){
        String sql = "SELECT id FROM planets";
        try(Connection connection = DatabaseConnectorUtility.createConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<PlanetEntity> entities = new ArrayList<>();
            while(resultSet.next()){
                PlanetEntity planetEntity = new PlanetEntity();
                //getString(column number) or (column name)
                planetEntity.setId(resultSet.getString("id"));

                entities.add(planetEntity);
            }
            return entities;
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }
}