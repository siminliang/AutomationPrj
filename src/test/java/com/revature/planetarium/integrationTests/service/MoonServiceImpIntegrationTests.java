package com.revature.planetarium.integrationTests.service;

import com.revature.planetarium.Utility;
import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.exceptions.MoonFail;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.repository.moon.MoonDaoImp;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.service.moon.MoonServiceImp;
import com.revature.planetarium.service.planet.PlanetServiceImp;
import org.junit.*;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.*;

public class MoonServiceImpIntegrationTests {
    private MoonDaoImp moonDao;
    private MoonServiceImp<Serializable> moonService;

    @Before
    public void beforeEach(){
        moonDao = new MoonDaoImp();
        moonService = new MoonServiceImp<>(moonDao);
        Utility.resetTestDatabase();
    }

    @AfterClass
    public static void tearDown(){ Utility.resetTestDatabase(); }

    @Test
    public void testCreateMoon_Positive(){
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setOwnerId(1);

        Moon test = moonService.createMoon(moon);

        assertEquals("Phobos", test.getMoonName());
    }

    @Test
    public void testCreateMoon_NameTooShort_Negative(){
        Moon moon = new Moon();
        moon.setMoonName("");
        moon.setOwnerId(1);

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.createMoon(moon));
        assertEquals("Moon name must be between 1 and 30 characters", moonFail.getMessage());
    }

    @Test
    public void testCreateMoon_NameTooLong_Negative(){
        Moon moon = new Moon();
        moon.setMoonName("this moon name is 31 chars!!!!!");
        moon.setOwnerId(1);

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.createMoon(moon));
        assertEquals("Moon name must be between 1 and 30 characters", moonFail.getMessage());
    }

    @Test
    public void testCreateMoon_NonUniqueName_Negative(){
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setOwnerId(1);

        moonService.createMoon(moon);

        Moon moon2 = new Moon();
        moon2.setMoonName("Phobos");
        moon2.setOwnerId(2);

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.createMoon(moon2));
        assertEquals("Moon name must be unique", moonFail.getMessage());
    }

    @Test
    public void testCreateMoon_NonUniquePlanetName_Negative() {
        Planet planet = new Planet();
        planet.setPlanetName("Terra");
        planet.setOwnerId(1);

        Moon moon2 = new Moon();
        moon2.setMoonName("Terra");
        moon2.setOwnerId(1);


        PlanetDaoImp planetDaoImp = new PlanetDaoImp();
        PlanetServiceImp<Serializable> planetServiceImp = new PlanetServiceImp<>(planetDaoImp);
        planetServiceImp.createPlanet(planet);

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.createMoon(moon2));
        assertEquals("Moon name must be unique", moonFail.getMessage());
    }

    @Test
    public void testSelectMoon_ByName_Positive() {
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setOwnerId(1);

        moonService.createMoon(moon);

        Moon test = moonService.selectMoon("Phobos");

        assertEquals("Phobos", test.getMoonName());
    }

    @Test
    public void testSelectMoon_ById_Positive(){
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setOwnerId(1);

        Moon createdMoon = moonService.createMoon(moon);

        Moon test = moonService.selectMoon(createdMoon.getMoonId());

        assertEquals(createdMoon.getMoonId(), test.getMoonId());
    }

    @Test
    public void testSelectMoon_Negative() {
        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.selectMoon("This moon does not exists"));
        assertEquals("Moon not found", moonFail.getMessage());
    }

    @Test
    public void testSelectAllMoons_Positive(){
        List<Moon> moons = moonService.selectAllMoons();
        assertFalse(moons.isEmpty());
    }

    @Test
    public void testSelectAllMoons_Negative(){
        moonDao.deleteMoon(1);
        moonDao.deleteMoon(2);

        List<Moon> moons = moonService.selectAllMoons();
        assertTrue(moons.isEmpty());
    }

    @Test
    public void testSelectByPlanet_Positive() {
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setOwnerId(1);

        moonService.createMoon(moon);

        List<Moon> moons = moonService.selectByPlanet(1);

        assertEquals(2, moons.size());
    }

    @Test
    public void testSelectByPlanet_Negative(){
        List<Moon> moons = moonService.selectByPlanet(99);

        assertTrue(moons.isEmpty());
    }

    @Test
    public void testUpdateMoon_Positive() {
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setOwnerId(1);

        Moon moon2 = moonService.createMoon(moon);

        moon2.setMoonName("Callisto");

        Moon test = moonService.updateMoon(moon2);

        assertEquals("Callisto", test.getMoonName());
    }

    @Test
    public void testUpdateMoon_MoonNotFound_Negative() {
        Moon moon = new Moon();
        moon.setMoonId(9999);
        moon.setMoonName("Phobos");

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.updateMoon(moon));
        assertEquals("Moon not found, could not update", moonFail.getMessage());
    }

    @Test
    public void testUpdatePlanet_NameTooShort_Negative(){
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setOwnerId(1);

        Moon updatedMoon = moonService.createMoon(moon);

        updatedMoon.setMoonName("");

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.updateMoon(updatedMoon));
        assertEquals("Moon name must be between 1 and 30 characters, could not update", moonFail.getMessage());
    }

    @Test
    public void testUpdatePlanet_NameTooLong_Negative(){
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setOwnerId(1);

        Moon updatedMoon = moonService.createMoon(moon);

        updatedMoon.setMoonName("this moon name is 31 chars!!!!!");

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.updateMoon(updatedMoon));
        assertEquals("Moon name must be between 1 and 30 characters, could not update", moonFail.getMessage());
    }

    @Test
    public void testDeleteMoon_ById_Positive() {
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setOwnerId(1);

        Moon createdMoon = moonService.createMoon(moon);

        String result = moonService.deleteMoon(createdMoon.getMoonId());
        assertEquals("Moon deleted successfully", result);
    }

    @Test
    public void testDeleteMoon_ByName_Positive() {
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setOwnerId(1);

        moonService.createMoon(moon);

        String result = moonService.deleteMoon("Phobos");

        assertEquals("Moon deleted successfully", result);
    }

    @Test
    public void testDeleteMoon_Negative(){
        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.deleteMoon(999));  // Assuming this ID does not exist
        assertEquals("Moon delete failed, please try again", moonFail.getMessage());
    }
}
