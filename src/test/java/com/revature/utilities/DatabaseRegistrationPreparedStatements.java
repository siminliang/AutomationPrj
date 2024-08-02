package com.revature.utilities;

import com.revature.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseRegistrationPreparedStatements {


    public static void addUser(PreparedStatement preparedStatement, UserEntity userEntity) throws SQLException {
        preparedStatement.setString(1, userEntity.username);
        preparedStatement.setString(2, userEntity.password);
    }


    public static void deleteUser(PreparedStatement preparedStatement, UserEntity userEntity) throws SQLException {
        preparedStatement.setString(1, userEntity.username);
    }

    public static PreparedStatement deletePlanet(PreparedStatement preparedStatement, String planetName) throws SQLException {
        preparedStatement.setString(1, planetName);
        return preparedStatement;
    }

}
