package com.revature.planetarium.unitTests.repository;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.entities.User;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.repository.moon.MoonDaoImp;
import com.revature.planetarium.utility.DatabaseConnector;
import org.junit.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;

public class MoonDaoImpUnitTests {
    private User user;
    private Planet planet;
    private Moon moon;

    private MoonDao moonDaoImp;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private static MockedStatic<DatabaseConnector> mockedStatic;

    @BeforeClass
    public static void setup(){
        mockedStatic = Mockito.<DatabaseConnector>mockStatic(DatabaseConnector.class);
    }

    @Before
    public void beforeEach() throws SQLException {
        moonDaoImp = new MoonDaoImp();

        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);

        Mockito.when(DatabaseConnector.getConnection()).thenReturn(mockConnection);
    }

    @After
    public void afterEach(){

    }

    @AfterClass
    public static void tearDown(){
        mockedStatic.close();
    }

    @Test
    public void testExamplePlaceHolder(){

    }
}
