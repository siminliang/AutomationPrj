package com.revature.utilities;

import com.revature.entity.MoonEntity;
import com.revature.entity.PlanetEntity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseMoonPreparedStatements {


    public static void addMoon(PreparedStatement preparedStatement, MoonEntity moonEntity) throws SQLException, FileNotFoundException {
        FileInputStream fis = new FileInputStream(moonEntity.image);
        preparedStatement.setString(1, moonEntity.name);
        preparedStatement.setString(2, moonEntity.owner);
        preparedStatement.setBinaryStream(3, fis, (int) moonEntity.image.length());
    }


    public static void deleteMoon(PreparedStatement preparedStatement, MoonEntity moonEntity) throws SQLException {
        preparedStatement.setString(1, moonEntity.name);
    }

}
