package com.revature.planetarium.unitTests.repository;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.utility.DatabaseConnector;
import org.junit.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

public class PlanetDaoImpUnitTests {
    private static final String validName = "validName";
    private static final String tooLongName ="thisIsTooLongOfANameForPlanets!";

    private User user;
    private Planet planet;
    private Moon moon;

    private PlanetDao planetDaoImp;
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
    public void createPlanet_UnitTest_Positive() throws SQLException{
        Planet planet = new Planet();
        planet.setPlanetName(validName);
        planet.setOwnerId(1);

        Mockito.when(mockConnections.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.getGeneratedKeys()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(true);
        Mockito.when(mockRstSet.getInt(1)).thenReturn(1);

        Optional<Planet> result = planetDaoImp.createPlanet(planet);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(validName, result.get().getPlanetName());
        Assert.assertEquals(1, result.get().getOwnerId());
    }

    @Test
    public void createPlanet_UnitTest_Negative() throws SQLException{
        Planet planet = new Planet();
        planet.setPlanetName(tooLongName);
        planet.setOwnerId(1);

        Mockito.when(mockConnections.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.getGeneratedKeys()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(false);

        Optional<Planet> result = planetDaoImp.createPlanet(planet);

        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void readPlanet_id_UnitTest_Positive(){
        Optional<Planet> planet = planetDaoImp.readPlanet(1);
        Assert.assertNotNull(planet);
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
