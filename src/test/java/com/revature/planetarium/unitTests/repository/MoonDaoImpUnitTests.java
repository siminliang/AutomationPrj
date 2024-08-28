package com.revature.planetarium.unitTests.repository;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.repository.moon.MoonDaoImp;
import com.revature.planetarium.utility.DatabaseConnector;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.swing.text.html.Option;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

public class MoonDaoImpUnitTests {
    private Moon moon;
    private static String moonName = "Moon";
    private static final String tooLongName ="thisIsTooLongOfANameForMoons!!!!";
    private static String moonImage;

    private MoonDao moonDaoImp;
    private Connection mockConnections;
    private PreparedStatement mockPrepStmt;
    private ResultSet mockRstSet;
    private static MockedStatic<DatabaseConnector> mockedStatic;

    @BeforeClass
    public static void setup() throws IOException {
        mockedStatic = Mockito.<DatabaseConnector>mockStatic(DatabaseConnector.class);

        String filepath = "src/test/resources/Celestial-Images/moon-1.jpg";
        try(FileInputStream fis = new FileInputStream(filepath)){
            byte[] fileBytes = fis.readAllBytes();
            moonImage = Base64.getEncoder().encodeToString(fileBytes);
        }
    }

    @Before
    public void beforeEach() throws SQLException {
        moonDaoImp = new MoonDaoImp();

        mockConnections = Mockito.mock(Connection.class);
        mockPrepStmt = Mockito.mock(PreparedStatement.class);
        mockRstSet = Mockito.mock(ResultSet.class);

        Mockito.when(DatabaseConnector.getConnection()).thenReturn(mockConnections);

        moon = new Moon();
        moon.setOwnerId(1);
        moon.setMoonName(moonName);
        moon.setImageData(moonImage);
    }

    @After
    public void afterEach(){

    }

    @AfterClass
    public static void tearDown(){
        mockedStatic.close();
    }

    @Test
    public void createMoon_unitTest_positive() throws SQLException{
        Mockito.when(mockConnections.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.getGeneratedKeys()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(true);
        Mockito.when(mockRstSet.getInt(1)).thenReturn(1);

        Optional<Moon> result = moonDaoImp.createMoon(moon);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(moonName, result.get().getMoonName());
        Assert.assertEquals(1, result.get().getOwnerId());
    }

    @Test
    public void createMoon_unitTest_negative() throws SQLException {
        Moon invalidMoon = new Moon();
        invalidMoon.setMoonName(tooLongName);
        invalidMoon.setOwnerId(1);
        invalidMoon.setImageData(moonImage);

        Mockito.when(mockConnections.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.getGeneratedKeys()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(false);

        Optional<Moon> result = moonDaoImp.createMoon(moon);

        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void readMoon_byId_unitTest_positive() throws SQLException {
        int id = 1;

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(true);
        Mockito.when(mockRstSet.getInt("id")).thenReturn(1);
        Mockito.when(mockRstSet.getString("name")).thenReturn("Moon");
        Mockito.when(mockRstSet.getInt("myPlanetId")).thenReturn(1);

        Optional<Moon> result = moonDaoImp.readMoon(id);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(1, result.get().getMoonId());
    }

    @Test
    public void readMoon_byId_unitTest_negative() throws SQLException {
        int id = 1;

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(false);

        Optional<Moon> result = moonDaoImp.readMoon(id);

        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void readMoon_byName_unitTest_positive() throws SQLException {

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(true);
        Mockito.when(mockRstSet.getInt("id")).thenReturn(1);
        Mockito.when(mockRstSet.getString("name")).thenReturn("Moon");
        Mockito.when(mockRstSet.getInt("myPlanetId")).thenReturn(1);

        Optional<Moon> result = moonDaoImp.readMoon(moonName);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("Moon", result.get().getMoonName());
    }

    @Test
    public void readMoon_byName_unitTest_negative() throws SQLException {
        String invalidName = "Earth";

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(false);

        Optional<Moon> result = moonDaoImp.readMoon(invalidName);

        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void readAllMoons_unitTest_positive() throws SQLException {

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(true).thenReturn(false); // Simulate a single row, then no more rows
        Mockito.when(mockRstSet.getInt("id")).thenReturn(1);
        Mockito.when(mockRstSet.getString("name")).thenReturn("Luna");
        Mockito.when(mockRstSet.getInt("myPlanetId")).thenReturn(1);

        List<Moon> result = moonDaoImp.readAllMoons();

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Moon thisMoon = result.get(0);
        Assert.assertEquals(1, thisMoon.getMoonId());
        Assert.assertEquals("Luna", thisMoon.getMoonName());
        Assert.assertEquals(1, thisMoon.getOwnerId());
    }

    @Test
    public void readMoonsByPlanet_unitTest_positive() throws SQLException {
        int id = 1;
        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(true).thenReturn(false); // Simulate a single row, then no more rows
        Mockito.when(mockRstSet.getInt("id")).thenReturn(1);
        Mockito.when(mockRstSet.getString("name")).thenReturn("Luna");
        Mockito.when(mockRstSet.getInt("myPlanetId")).thenReturn(1);

        List<Moon> result = moonDaoImp.readMoonsByPlanet(id);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Moon thisMoon = result.get(0);
        Assert.assertEquals(1, thisMoon.getMoonId());
        Assert.assertEquals("Luna", thisMoon.getMoonName());
        Assert.assertEquals(1, thisMoon.getOwnerId());
    }

    @Test
    public void readMoonsByPlanet_unitTest_negative() throws SQLException {
        int planetId = 999;

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeQuery()).thenReturn(mockRstSet);
        Mockito.when(mockRstSet.next()).thenReturn(false);

        List<Moon> result = moonDaoImp.readMoonsByPlanet(planetId);

        List<Moon> emptyList =  new ArrayList<>();
        Assert.assertEquals(emptyList, result);
    }

    @Test
    public void updateMoon_unitTest_positive() throws SQLException {
        Moon mockMoon = new Moon();
        mockMoon.setMoonId(1);
        mockMoon.setMoonName("notLuna");
        mockMoon.setOwnerId(1);

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeUpdate()).thenReturn(1);

        Optional<Moon> result = moonDaoImp.updateMoon(mockMoon);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(mockMoon, result.get());
    }

    @Test
    public void updateMoon_unitTest_negative() throws SQLException {
        Moon mockMoon = new Moon();
        mockMoon.setOwnerId(999);
        mockMoon.setMoonName("nonExistent");

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeUpdate()).thenReturn(0);

        Optional<Moon> result = moonDaoImp.updateMoon(mockMoon);

        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void deleteMoon_byId_unitTest_positive() throws SQLException {
        int moonId = 1;

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeUpdate()).thenReturn(1); // 1 row deleted

        boolean deleted = moonDaoImp.deleteMoon(moonId);

        Assert.assertTrue(deleted);
    }

    @Test
    public void deleteMoon_byId_unitTest_negative() throws SQLException {
        int moonId = 999;

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeUpdate()).thenReturn(0); // No rows deleted

        boolean deleted = moonDaoImp.deleteMoon(moonId);

        Assert.assertFalse(deleted);
    }

    @Test
    public void deleteMoon_byName_unitTest_positive() throws SQLException {
        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeUpdate()).thenReturn(1);

        boolean deleted = moonDaoImp.deleteMoon(moonName);

        Assert.assertTrue(deleted);
    }

    @Test
    public void deleteMoon_byName_unitTest_negative() throws SQLException {
        String badName = "NonExistent Moon";

        Mockito.when(mockConnections.prepareStatement(anyString())).thenReturn(mockPrepStmt);
        Mockito.when(mockPrepStmt.executeUpdate()).thenReturn(0); // No rows deleted

        boolean deleted = moonDaoImp.deleteMoon(badName);

        Assert.assertFalse(deleted);
    }
}
