package com.revature.utilities;

import com.revature.entity.UserEntity;


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



    private static void setParameters(PreparedStatement preparedStatement, String sqlFileName, Object entity) throws SQLException {
            if( entity instanceof UserEntity) {
                UserEntity userEntity = (UserEntity) entity;
                switch(sqlFileName){
                    case "CheckAlreadyRegisteredUserAddIfNot.sql":
                        DatabaseRegistrationPreparedStatements.CheckAlreadyRegisteredUserAddIfNot(preparedStatement, userEntity);
                        break;
                    case "CheckNoRegisteredUserDeleteIfSo.sql":
                        DatabaseRegistrationPreparedStatements.CheckNoRegisteredUserDeleteIfSo(preparedStatement, userEntity);
                        break;
                }
            }
    }
}
