package com.revature.planetarium.unitTests.repository;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.entities.User;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.utility.DatabaseConnector;
import org.junit.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanetDaoImpUnitTests {
    private User user;
    private Planet planet;
    private Moon moon;

    private PlanetDaoImp planetDaoImp;
    private Connection mockConnections;
    private PreparedStatement mockPrepStmt;
    private ResultSet mockRstSet;
    private static MockedStatic<DatabaseConnector> mockedStatic;


    @BeforeClass
    public static void setup(){
        mockedStatic = Mockito.<DatabaseConnector>mockStatic(DatabaseConnector.class);
    }

    @Before
    public void beforeEach() throws SQLException {
        planetDaoImp = new PlanetDaoImp();

        mockConnections = Mockito.mock(Connection.class);
        mockPrepStmt = Mockito.mock(PreparedStatement.class);
        mockRstSet = Mockito.mock(ResultSet.class);

        Mockito.when(DatabaseConnector.getConnection()).thenReturn(mockConnections);
    }

    @After
    public void afterEach(){

    }

    @AfterClass
    public static void tearDown(){
        mockedStatic.close();
    }

    @Test
    public void createPlanet_UnitTest_Positive(){

    }

    @Test
    public void createPlanet_UnitTest_Negative(){

    }

    @Test
    public void readPlanet_id_UnitTest_Positive(){

    }

    @Test
    public void readPlanet_id_UnitTest_Negative(){

    }

    @Test
    public void readPlanet_name_UnitTest_Positive(){

    }

    @Test
    public void readPlanet_name_UnitTest_Negative(){

    }

    @Test
    public void readAllPlanets_UnitTest_Positive(){

    }

    @Test
    public void readAllPlanets_UnitTest_Negative(){

    }

    @Test
    public void readPlanetsByOwner_UnitTest_Positive(){

    }

    @Test
    public void readPlanetsByOwner_UnitTest_Negative(){

    }

    @Test
    public void updatePlanet_UnitTest_Positive(){

    }

    @Test
    public void updatePlanet_UnitTest_Negative(){

    }

    @Test
    public void deletePlanet_id_UnitTest_Positive(){

    }

    @Test
    public void deletePlanet_id_UnitTest_Negative(){

    }

    @Test
    public void deletePlanet_name_UnitTest_Positive(){

    }

    @Test
    public void deletePlanet_name_UnitTest_Negative(){

    }

}
