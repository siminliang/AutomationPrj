package com.revature.planetarium.unitTests.repository;

import java.sql.*;
import java.util.Optional;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.UserFail;
import com.revature.planetarium.utility.DatabaseConnector;
import org.junit.*;
import com.revature.planetarium.repository.user.UserDaoImp;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

public class UserDaoImpUnitTests {
    private User user;
    private Planet planet;
    private Moon moon;

    private UserDaoImp userDaoImp;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;




    @BeforeClass
    public static void setup(){
        Mockito.<DatabaseConnector>mockStatic(DatabaseConnector.class);
    }

    @Before
    public void beforeEach() throws SQLException {
        // instance of the class we are testing
        userDaoImp = new UserDaoImp();

        // mock objects that the class and its methods are dependent on
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);

        // mock the database connector connection

        Mockito.when(DatabaseConnector.getConnection()).thenReturn(mockConnection);


    }

    @After
    public void afterEach(){

    }

    @AfterClass
    public static void tearDown(){

    }

    @Test
    public void createUserUnitTest_Positive() throws SQLException {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("testpass");

        // stub the data
        // Mock the behavior of the PreparedStatement and ResultSet
        Mockito.when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(1);

        // call the method we are testing, and return the result
        Optional<User> result = userDaoImp.createUser(user);

        // assert
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("testuser", result.get().getUsername());
        Assert.assertEquals(1, result.get().getId());
        //Assert.assertNotNull(result);

        /* // need to understand verify more
        // Verify interactions
        verify(mockPreparedStatement).executeUpdate();
        verify(mockResultSet).next();
        verify(mockResultSet).getInt(1);
         */

    }

    @Test
    public void createUserUnitTest_Negative() throws SQLException {
        // Prepare the User object
        User newUser = new User();
        newUser.setUsername("testuser");
        newUser.setPassword("testpass");

        // Mock the behavior to throw SQLException
        Mockito.when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.getGeneratedKeys()).thenThrow(new SQLException("Database error"));

        // Call the method to test and Assert thrown exception
        Assert.assertThrows(UserFail.class, () ->{
            userDaoImp.createUser(newUser);
        });

    }


    @Test
    public void findUserByUsernameUnitTest_Positive() throws SQLException {
        // Prepare the User object
        String username = "testuser";

        // Mock the behavior of the PreparedStatement and ResultSet
        Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getInt("id")).thenReturn(1);
        Mockito.when(mockResultSet.getString("username")).thenReturn(username);
        Mockito.when(mockResultSet.getString("password")).thenReturn("testpass");

        // Call the method to test
        Optional<User> result = userDaoImp.findUserByUsername(username);


        // Assert
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(1, result.get().getId());
        Assert.assertEquals("testuser", result.get().getUsername());


        /* // need to understand verify more
        // Verify interactions
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();

        */

    }

    @Test
    public void findUserByUsernameUnitTest_Negative() throws SQLException {
        // Prepare the username
        String username = "testuser";

        // Mock the behavior to throw SQLException
        Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Database error"));

        // Call the method to test and Assert thrown exception
        Assert.assertThrows(UserFail.class, () -> {
            userDaoImp.findUserByUsername(username);
        });

    }

}
