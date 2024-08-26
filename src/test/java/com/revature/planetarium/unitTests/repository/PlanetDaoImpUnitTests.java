package com.revature.planetarium.unitTests.repository;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.utility.DatabaseConnector;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

public class PlanetDaoImpUnitTests {
    private static final String validName = "validName";
    private static final String tooLongName ="thisIsTooLongOfANameForPlanets!";

    private User user;
    private Planet planet;
    private Moon moon;

    @Mock
    private PlanetDao planetDaoImp;

    @Mock
    private Connection mockConnections;

    @Mock
    private PreparedStatement mockPrepStmt;

    @Mock
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
    public void readPlanet_id_UnitTest_Positive() throws SQLException {
        int id = 1;

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(true);
        Mockito.when(mockRstSet.getInt("id")).thenReturn(1);
        Mockito.when(mockRstSet.getString("name")).thenReturn("Earth");
        Mockito.when(mockRstSet.getInt("ownerId")).thenReturn(1);
        Optional<Planet> result = planetDaoImp.readPlanet(1);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(1, result.get().getPlanetId());
    }

    @Test
    public void readPlanet_id_UnitTest_Negative() throws SQLException {
        int id = 1;

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(false);

        Optional<Planet> result = planetDaoImp.readPlanet(id);

        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void readPlanet_name_UnitTest_Positive() throws SQLException {
        String planetName = "Earth";

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(true);
        Mockito.when(mockRstSet.getInt("id")).thenReturn(1);
        Mockito.when(mockRstSet.getString("name")).thenReturn("Earth");
        Mockito.when(mockRstSet.getInt("ownerId")).thenReturn(1);

        Optional<Planet> result = planetDaoImp.readPlanet(planetName);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("Earth", result.get().getPlanetName());
    }

    @Test
    public void readPlanet_name_UnitTest_Negative() throws SQLException {
        String name = "Sun";

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(false);

        Optional<Planet> result = planetDaoImp.readPlanet(name);

        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void readAllPlanets_UnitTest_Positive() throws SQLException {
        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(true).thenReturn(false); // Simulate a single row, then no more rows
        Mockito.when(mockRstSet.getInt("id")).thenReturn(1);
        Mockito.when(mockRstSet.getString("name")).thenReturn("Earth");
        Mockito.when(mockRstSet.getInt("ownerId")).thenReturn(1);
        //Mockito.when(mockRstSet.getBytes("image")).thenReturn(null);

        List<Planet> result = planetDaoImp.readAllPlanets();

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Planet planet = result.get(0);
        Assert.assertEquals(1, planet.getPlanetId());
        Assert.assertEquals("Earth", planet.getPlanetName());
        Assert.assertEquals(1, planet.getOwnerId());

        // Verify interactions
        Mockito.verify(mockConnections).prepareStatement(anyString());
        Mockito.verify(mockPrepStmt).executeQuery();
        Mockito.verify(mockRstSet, Mockito.atLeast(1)).next();
        Mockito.verify(mockRstSet, Mockito.atLeast(1)).getInt("id");
        Mockito.verify(mockRstSet, Mockito.atLeast(1)).getString("name");
        Mockito.verify(mockRstSet, Mockito.atLeast(1)).getInt("ownerId");
    }

    @Test
    public void readAllPlanets_UnitTest_Negative() throws SQLException {
        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenThrow(new SQLException("Database error"));

        try {
            planetDaoImp.readAllPlanets();
            Assert.fail("Expected PlanetFail exception was not thrown");
        } catch (PlanetFail e) {
            Assert.assertEquals("Database error", e.getMessage());
        }

        // Verify interactions
        Mockito.verify(mockConnections).prepareStatement(anyString());
        Mockito.verify(mockPrepStmt).executeQuery();
    }

    @Test
    public void readPlanetsByOwner_UnitTest_Positive() throws SQLException {
        int ownerId = 1;

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(true).thenReturn(false); // Simulate a single row, then no more rows
        Mockito.when(mockRstSet.getInt("id")).thenReturn(1);
        Mockito.when(mockRstSet.getString("name")).thenReturn("Earth");
        Mockito.when(mockRstSet.getInt("ownerId")).thenReturn(ownerId);

        List<Planet> planets = planetDaoImp.readPlanetsByOwner(ownerId);

        // Verify the results
        Assert.assertNotNull(planets);
        Assert.assertEquals(1, planets.size());
        Planet planet = planets.get(0);
        Assert.assertEquals(1, planet.getPlanetId());
        Assert.assertEquals("Earth", planet.getPlanetName());
        Assert.assertEquals(ownerId, planet.getOwnerId());

        // Verify interactions
        Mockito.verify(mockConnections).prepareStatement(anyString());
        Mockito.verify(mockPrepStmt).setInt(1, ownerId);
        Mockito.verify(mockPrepStmt).executeQuery();
        Mockito.verify(mockRstSet, Mockito.atLeast(1)).next();
        Mockito.verify(mockRstSet, Mockito.atLeast(1)).getInt("id");
        Mockito.verify(mockRstSet, Mockito.atLeast(1)).getString("name");
        Mockito.verify(mockRstSet, Mockito.atLeast(1)).getInt("ownerId");
    }

    @Test
    public void readPlanetsByOwner_UnitTest_Negative() throws SQLException {
        int ownerId = 999;

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(false); // No rows found

        List<Planet> result = planetDaoImp.readPlanetsByOwner(ownerId);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void updatePlanet_UnitTest_Positive() throws SQLException {
        Planet mockPlanet = new Planet();
        mockPlanet.setPlanetId(1);
        mockPlanet.setPlanetName("notEarth");
        mockPlanet.setOwnerId(1);

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeUpdate()).thenReturn(1); // 1 row updated

        Optional<Planet> result = planetDaoImp.updatePlanet(mockPlanet);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(mockPlanet, result.get());
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
