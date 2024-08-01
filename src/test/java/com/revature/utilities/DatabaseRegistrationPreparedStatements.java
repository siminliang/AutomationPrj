package com.revature.utilities;

import com.revature.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseRegistrationPreparedStatements {


    public static PreparedStatement CheckAlreadyRegisteredUserAddIfNot(PreparedStatement preparedStatement, UserEntity userEntity) throws SQLException {
        preparedStatement.setString(1, userEntity.username);
        preparedStatement.setString(2, userEntity.password);
        return preparedStatement;
    }


    public static PreparedStatement CheckNoRegisteredUserDeleteIfSo(PreparedStatement preparedStatement, UserEntity userEntity) throws SQLException {
        preparedStatement.setString(1, userEntity.username);
        return preparedStatement;
    }

}
