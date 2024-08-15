package com.revature.selenium.entity.utilities;

import com.revature.selenium.entity.UserEntity;

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

}
