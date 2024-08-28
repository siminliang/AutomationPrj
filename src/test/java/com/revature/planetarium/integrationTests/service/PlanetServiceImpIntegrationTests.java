package com.revature.planetarium.integrationTests.service;

import com.revature.planetarium.Utility;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.service.planet.PlanetServiceImp;
import org.junit.*;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.*;

public class PlanetServiceImpIntegrationTests {

    private PlanetDaoImp planetDao;
    private PlanetServiceImp<Serializable> planetService;

    @BeforeClass
    public static void setup(){

    }

    @Before
    public void beforeEach(){
        planetDao = new PlanetDaoImp();
        planetService = new PlanetServiceImp<>(planetDao);
        Utility.resetTestDatabase();
    }

    @After
    public void afterEach(){

    }

    @AfterClass
    public static void tearDown(){
        Utility.resetTestDatabase();
    }

    @Test
    public void testCreatePlanet_Positive(){
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        planet.setOwnerId(1);

        Planet test = planetService.createPlanet(planet);

        assertEquals("Terra", test.getPlanetName());
    }

    @Test
    public void testCreatePlanet_NonUniqueName_Negative() {
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        planet.setOwnerId(1);

        Planet planet2 = new Planet();
        planet2.setPlanetName("Terra");
        planet2.setOwnerId(1);

        planetService.createPlanet(planet);

        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.createPlanet(planet));
        assertEquals("Planet name must be unique", planetFail.getMessage());
    }

    @Test
    public void testSelectPlanet_ById_Positive() {
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        planet.setOwnerId(1);

        Planet createdPlanet = planetService.createPlanet(planet);

        Planet test = planetService.selectPlanet(3);

        assertEquals(createdPlanet.getPlanetId(),test.getPlanetId());
    }

    @Test
    public void testSelectPlanet_ByName_Positive() {
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        planet.setOwnerId(1);
        planetDao.createPlanet(planet);

        Planet selectedPlanet = planetService.selectPlanet("Terra");

        assertEquals("Terra", selectedPlanet.getPlanetName());
    }

    @Test
    public void testSelectPlanet_Negative(){
        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.selectPlanet("This planet does not exists"));

        assertEquals("Planet not found",planetFail.getMessage());
    }

    @Test
    public void testSelectAllPlanets_Success() {
        List<Planet> planets = planetService.selectAllPlanets();

        assertFalse(planets.isEmpty());
    }

    @Test
    public void testSelectAllPlanets_Negative() {
        planetDao.deletePlanet(1);
        planetDao.deletePlanet(2);

        List<Planet> planets = planetService.selectAllPlanets();

        assertTrue(planets.isEmpty());
    }

    @Test
    public void testSelectByOwner_Positive() {
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        planet.setOwnerId(1);
        planetDao.createPlanet(planet);

        List<Planet> planets = planetService.selectByOwner(1);

        assertEquals(3,planets.size());
    }

    @Test
    public void testSelectByOwner_Negative() {
        List<Planet> planets = planetService.selectByOwner(99);

        assertTrue(planets.isEmpty());
    }

    @Test
    public void testUpdatePlanet_Positive() {
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        planet.setOwnerId(1);
        planetDao.createPlanet(planet);

        planet.setPlanetName("Cadia");
        Planet updatedPlanet = planetService.updatePlanet(planet);

        assertEquals("Cadia", updatedPlanet.getPlanetName());
    }

    @Test
    public void testUpdatePlanet_PlanetNotFound_Negative() {
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        planet.setPlanetId(9999);
        planet.setOwnerId(1);

        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.updatePlanet(planet));

        assertEquals("Planet not found, could not update",planetFail.getMessage());
    }

    @Test
    public void testUpdatePlanet_NameTooShort_Negative(){
        Planet planet = new Planet();
        planet.setPlanetId(1);
        planet.setPlanetName("Terra");
        planet.setOwnerId(1);

        Planet updatedPlanet = new Planet();
        updatedPlanet.setPlanetId(1);
        updatedPlanet.setPlanetName("");
        planet.setOwnerId(1);

        PlanetFail exception = assertThrows(PlanetFail.class, () -> planetService.updatePlanet(updatedPlanet));
        assertEquals("Planet name must be between 1 and 30 characters, could not update", exception.getMessage());
    }

    @Test
    public void testUpdatePlanet_NameTooLong_Negative(){
        Planet planet = new Planet();
        planet.setPlanetId(1);
        planet.setPlanetName("Terra");
        planet.setOwnerId(1);

        Planet updatedPlanet = new Planet();
        updatedPlanet.setPlanetId(1);
        updatedPlanet.setPlanetName("this planet name is 31 chars!!!");
        planet.setOwnerId(1);

        PlanetFail exception = assertThrows(PlanetFail.class, () -> planetService.updatePlanet(updatedPlanet));
        assertEquals("Planet name must be between 1 and 30 characters, could not update", exception.getMessage());
    }

    @Test
    public void testDeletePlanet_ById_Positive(){
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        planet.setOwnerId(1);

        Planet createdPlanet = planetService.createPlanet(planet);

        String result = planetService.deletePlanet(createdPlanet.getPlanetId());
        assertEquals("Planet deleted successfully", result);
    }

    @Test
    public void testDeletePlanet_ByName_Positive(){
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        planet.setOwnerId(1);

        planetService.createPlanet(planet);

        String test = planetService.deletePlanet("Terra");

        assertEquals("Planet deleted successfully", test);
    }

    @Test
    public void testDeletePlanet_Negative(){
        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.deletePlanet(999));
        assertEquals("Planet delete failed, please try again", planetFail.getMessage());
    }
}
