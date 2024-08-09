package com.revature.utilities;

import com.revature.entity.PlanetEntity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabasePlanetPreparedStatements {


    public static void addPlanet(PreparedStatement preparedStatement, PlanetEntity planetEntity) throws SQLException, FileNotFoundException {
        FileInputStream fis = new FileInputStream(planetEntity.image);
        preparedStatement.setString(1, planetEntity.name);
        preparedStatement.setString(2, planetEntity.owner);
        preparedStatement.setBinaryStream(3, fis, (int) planetEntity.image.length());
    }

    public static void addPlanetWithId(PreparedStatement preparedStatement, PlanetEntity planetEntity) throws SQLException, FileNotFoundException {
        FileInputStream fis = new FileInputStream(planetEntity.image);
        preparedStatement.setString(1, planetEntity.id);
        preparedStatement.setString(2, planetEntity.name);
        preparedStatement.setString(3, planetEntity.owner);
        preparedStatement.setBinaryStream(4, fis, (int) planetEntity.image.length());
    }


    public static void deletePlanetWithString(PreparedStatement preparedStatement, PlanetEntity planetEntity) throws SQLException {
        preparedStatement.setString(1, planetEntity.name);
    }

    public static void deletePlanetWithId(PreparedStatement preparedStatement, PlanetEntity planetEntity) throws SQLException {
        preparedStatement.setString(1, planetEntity.name);
    }

}
