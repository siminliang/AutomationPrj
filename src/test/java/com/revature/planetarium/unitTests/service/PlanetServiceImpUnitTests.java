package com.revature.planetarium.unitTests.service;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.service.planet.PlanetServiceImp;
import org.junit.*;
import org.mockito.Mockito;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class PlanetServiceImpUnitTests {

    private PlanetDao planetDao;
    private PlanetServiceImp<Serializable> planetService;

    @BeforeClass
    public static void setup(){

    }

    @Before
    public void beforeEach(){
        planetDao = Mockito.mock(PlanetDao.class);
        planetService = new PlanetServiceImp<>(planetDao);
    }

    @After
    public void afterEach(){

    }

    @AfterClass
    public static void tearDown(){

    }

    @Test
    public void testCreatePlanet_Positive(){
        Planet planet = new Planet();
        planet.setPlanetName("Terra");

        when(planetDao.readPlanet("Terra")).thenReturn(Optional.empty());
        when(planetDao.createPlanet(planet)).thenReturn(Optional.of(planet));

        Planet test = planetService.createPlanet(planet);

        assertEquals("Terra", test.getPlanetName());
    }

    @Test
    public void testCreatePlanet_NameTooShort_Negative(){
        Planet planet = new Planet();
        planet.setPlanetName("");

        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.createPlanet(planet));
        assertEquals("Planet name must be between 1 and 30 characters", planetFail.getMessage());
    }

    @Test
    public void testCreatePlanet_NameTooLong_Negative(){
        Planet planet = new Planet();
        planet.setPlanetName("this planet name is 31 chars!!!");

        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.createPlanet(planet));
        assertEquals("Planet name must be between 1 and 30 characters", planetFail.getMessage());
    }

    @Test
    public void testCreatePlanet_NonUniqueName_Negative() {
        Planet planet = new Planet();
        planet.setPlanetName("Terra");

        when(planetDao.readPlanet("Terra")).thenReturn(Optional.of(planet));

        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.createPlanet(planet));
        assertEquals("Planet name must be unique", planetFail.getMessage());
    }



    @Test
    public void testCreatePlanet_CreationFailed_Negative(){
        Planet planet = new Planet();
        planet.setPlanetName("Terra");

        when(planetDao.readPlanet("Terra")).thenReturn(Optional.empty());
        when(planetDao.createPlanet(planet)).thenReturn(Optional.empty());

        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.createPlanet(planet));
        assertEquals("Planet creation failed, please try again", planetFail.getMessage());
    }

    @Test
    public void testSelectPlanet_ById_Positive(){
        Planet planet = new Planet();
        planet.setPlanetId(1);
        planet.setPlanetName("Terra");

        when(planetDao.readPlanet(1)).thenReturn(Optional.of(planet));

        Planet test = planetService.selectPlanet(1);

        assertEquals(1,test.getPlanetId());
    }

    @Test
    public void testSelectPlanet_ByName_Positive(){
        Planet planet = new Planet();
        planet.setPlanetName("Terra");

        when(planetDao.readPlanet("Terra")).thenReturn(Optional.of(planet));

        Planet test = planetService.selectPlanet("Terra");

        assertEquals("Terra",test.getPlanetName());
    }

    @Test
    public void testSelectPlanet_Negative(){
        when(planetDao.readPlanet("This planet does not exists")).thenReturn(Optional.empty());

        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.selectPlanet("This planet does not exists"));

        assertEquals("Planet not found",planetFail.getMessage());
    }

    @Test
    public void testSelectAllPlanets_Positive(){
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        Planet planet2 = new Planet();
        planet2.setPlanetName("Cadia");

        when(planetDao.readAllPlanets()).thenReturn(Arrays.asList(planet,planet2));

        List<Planet> test = planetService.selectAllPlanets();

        assertEquals(2,test.size());
    }

    @Test
    public void testSelectAllPlanets_Negative(){
        when(planetDao.readAllPlanets()).thenReturn(Collections.emptyList());

        List<Planet> test = planetService.selectAllPlanets();

        assertTrue(test.isEmpty());
    }

    @Test
    public void testSelectByOwner_Positive(){
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        Planet planet2 = new Planet();
        planet2.setPlanetName("Cadia");

        when(planetDao.readPlanetsByOwner(1)).thenReturn(Arrays.asList(planet,planet2));

        List<Planet> test = planetService.selectByOwner(1);

        assertEquals(2,test.size());
    }

    @Test
    public void testSelectByOwner_Negative(){
        when(planetDao.readPlanetsByOwner(1)).thenReturn(Collections.emptyList());

        List<Planet> test = planetService.selectByOwner(1);

        assertTrue(test.isEmpty());
    }

    @Test
    public void testUpdatePlanet_Positive(){
        Planet planet = new Planet();
        planet.setPlanetId(1);
        planet.setPlanetName("Terra");

        Planet updatedPlanet = new Planet();
        updatedPlanet.setPlanetId(1);
        updatedPlanet.setPlanetName("Cadia");

        when(planetDao.readPlanet(1)).thenReturn(Optional.of(planet));
        when(planetDao.readPlanet("Cadia")).thenReturn(Optional.empty());
        when(planetDao.updatePlanet(updatedPlanet)).thenReturn(Optional.of(updatedPlanet));

        Planet test = planetService.updatePlanet(updatedPlanet);

        assertEquals(1, test.getPlanetId());
        assertEquals("Cadia", test.getPlanetName());
    }

    @Test
    public void testUpdatePlanet_PlanetNotFound_Negative(){
        Planet planet = new Planet();
        planet.setPlanetId(1);
        planet.setPlanetName("Cadia");

        when(planetDao.readPlanet(1)).thenReturn(Optional.empty());

        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.updatePlanet(planet));
        assertEquals("Planet not found, could not update", planetFail.getMessage());
    }

    @Test
    public void testUpdatePlanet_NameTooShort_Negative(){
        Planet planet = new Planet();
        planet.setPlanetId(1);
        planet.setPlanetName("Terra");

        Planet updatedPlanet = new Planet();
        updatedPlanet.setPlanetId(1);
        updatedPlanet.setPlanetName("");

        when(planetDao.readPlanet(1)).thenReturn(Optional.of(planet));

        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.updatePlanet(updatedPlanet));
        assertEquals("Planet name must be between 1 and 30 characters, could not update", planetFail.getMessage());
    }

    @Test
    public void testUpdatePlanet_NameTooLong_Negative(){
        Planet planet = new Planet();
        planet.setPlanetId(1);
        planet.setPlanetName("Terra");

        Planet updatedPlanet = new Planet();
        updatedPlanet.setPlanetId(1);
        updatedPlanet.setPlanetName("this planet name is 31 chars!!!");

        when(planetDao.readPlanet(1)).thenReturn(Optional.of(planet));

        PlanetFail exception = assertThrows(PlanetFail.class, () -> planetService.updatePlanet(updatedPlanet));
        assertEquals("Planet name must be between 1 and 30 characters, could not update", exception.getMessage());
    }

    @Test
    public void testDeletePlanet_ById_Positive(){
        when(planetDao.deletePlanet(1)).thenReturn(true);

        String test = planetService.deletePlanet(1);

        assertEquals("Planet deleted successfully", test);
    }

    @Test
    public void testDeletePlanet_ByName_Positive(){
        when(planetDao.deletePlanet("Terra")).thenReturn(true);

        String test = planetService.deletePlanet("Terra");

        assertEquals("Planet deleted successfully", test);
    }

    @Test
    public void testDeletePlanet_Negative(){
        when(planetDao.deletePlanet(1)).thenReturn(false);

        PlanetFail planetFail = assertThrows(PlanetFail.class, () -> planetService.deletePlanet(1));
        assertEquals("Planet delete failed, please try again", planetFail.getMessage());
    }
}
