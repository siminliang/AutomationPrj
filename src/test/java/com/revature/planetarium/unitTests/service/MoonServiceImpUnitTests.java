package com.revature.planetarium.unitTests.service;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.exceptions.MoonFail;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.service.moon.MoonServiceImp;
import org.junit.*;
import org.mockito.Mockito;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class MoonServiceImpUnitTests {

    private MoonDao moonDao;
    private MoonServiceImp<Serializable> moonService;

    @BeforeClass
    public static void setup(){

    }

    @Before
    public void beforeEach(){
        moonDao = Mockito.mock(MoonDao.class);
        moonService = new MoonServiceImp<>(moonDao);
    }

    @After
    public void afterEach(){

    }

    @AfterClass
    public static void tearDown(){

    }

    @Test
    public void testCreateMoon_Positive(){
        Moon moon = new Moon();
        moon.setMoonName("Phobos");

        when(moonDao.readMoon("Phobos")).thenReturn(Optional.empty());
        when(moonDao.createMoon(moon)).thenReturn(Optional.of(moon));

        Moon test = moonService.createMoon(moon);

        assertEquals("Phobos", test.getMoonName());
    }

    @Test
    public void testCreateMoon_NameTooShort_Negative(){
        Moon moon = new Moon();
        moon.setMoonName("");

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.createMoon(moon));
        assertEquals("Moon name must be between 1 and 30 characters", moonFail.getMessage());
    }

    @Test
    public void testCreateMoon_NameTooLong_Negative(){
        Moon moon = new Moon();
        moon.setMoonName("this moon name is 31 chars!!!!!");

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.createMoon(moon));
        assertEquals("Moon name must be between 1 and 30 characters", moonFail.getMessage());
    }

    @Test
    public void testCreateMoon_NonUniqueName_Negative(){
        Moon moon = new Moon();
        moon.setMoonName("Phobos");

        when(moonDao.readMoon("Phobos")).thenReturn(Optional.of(moon));

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.createMoon(moon));
        assertEquals("Moon name must be unique", moonFail.getMessage());
    }

    @Test
    public void testSelectMoon_ByName_Positive() {
        Moon moon = new Moon();
        moon.setMoonName("Phobos");

        when(moonDao.readMoon("Phobos")).thenReturn(Optional.of(moon));

        Moon selectedMoon = moonService.selectMoon("Phobos");

        assertEquals("Phobos", selectedMoon.getMoonName());
    }

    @Test
    public void testSelectMoon_ById_Positive(){
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setMoonId(1);

        when(moonDao.readMoon(1)).thenReturn(Optional.of(moon));

        Moon test = moonService.selectMoon(1);

        assertEquals(1,test.getMoonId());
    }

    @Test
    public void testSelectMoon_Negative() {
        when(moonDao.readMoon("This moon does not exists")).thenReturn(Optional.empty());

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.selectMoon("This moon does not exists"));
        assertEquals("Moon not found", moonFail.getMessage());
    }

    @Test
    public void testSelectAllMoons_Positive() {
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        Moon moon2 = new Moon();
        moon2.setMoonName("Titan");

        when(moonDao.readAllMoons()).thenReturn(Arrays.asList(moon, moon2));

        List<Moon> moons = moonService.selectAllMoons();

        assertEquals(2, moons.size());
    }

    @Test
    public void testSelectAllMoons_Negative(){
        when(moonDao.readAllMoons()).thenReturn(Collections.emptyList());

        List<Moon> test = moonService.selectAllMoons();

        assertTrue(test.isEmpty());
    }

    @Test
    public void testSelectByPlanet_Positive() {
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setOwnerId(1);

        when(moonDao.readMoonsByPlanet(1)).thenReturn(Arrays.asList(moon));

        List<Moon> moons = moonService.selectByPlanet(1);

        assertEquals("Phobos", moons.get(0).getMoonName());
    }

    @Test
    public void testSelectByPlanet_Negative(){
        when(moonDao.readMoonsByPlanet(1)).thenReturn(Collections.emptyList());

        List<Moon> test = moonService.selectByPlanet(1);

        assertTrue(test.isEmpty());
    }

    @Test
    public void testUpdateMoon_Positive() {
        Moon moon = new Moon();
        moon.setMoonName("Phobos");
        moon.setMoonId(1);

        Moon updatedMoon = new Moon();
        updatedMoon.setMoonName("Titan");
        updatedMoon.setMoonId(1);

        when(moonDao.readMoon(1)).thenReturn(Optional.of(moon));
        when(moonDao.readMoon("Titan")).thenReturn(Optional.empty());
        when(moonDao.updateMoon(updatedMoon)).thenReturn(Optional.of(updatedMoon));

        Moon test = moonService.updateMoon(updatedMoon);

        assertEquals("Titan", test.getMoonName());
    }

    @Test
    public void testUpdateMoon_MoonNotFound_Negative() {
        Moon moon = new Moon();
        moon.setMoonId(1);
        moon.setMoonName("Phobos");

        when(moonDao.readMoon(1)).thenReturn(Optional.empty());

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.updateMoon(moon));
        assertEquals("Moon not found, could not update", moonFail.getMessage());
    }

    @Test
    public void testUpdatePlanet_NameTooShort_Negative(){
        Moon moon = new Moon();
        moon.setMoonId(1);
        moon.setMoonName("Phobos");

        Moon updatedMoon = new Moon();
        updatedMoon.setMoonName("");
        updatedMoon.setMoonId(1);

        when(moonDao.readMoon(1)).thenReturn(Optional.of(moon));

        MoonFail planetFail = assertThrows(MoonFail.class, () -> moonService.updateMoon(updatedMoon));
        assertEquals("Moon name must be between 1 and 30 characters, could not update", planetFail.getMessage());
    }

    @Test
    public void testUpdatePlanet_NameTooLong_Negative(){
        Moon moon = new Moon();
        moon.setMoonId(1);
        moon.setMoonName("Phobos");

        Moon updatedMoon = new Moon();
        updatedMoon.setMoonName("this moon name is 31 chars!!!!!");
        updatedMoon.setMoonId(1);

        when(moonDao.readMoon(1)).thenReturn(Optional.of(moon));

        MoonFail planetFail = assertThrows(MoonFail.class, () -> moonService.updateMoon(updatedMoon));
        assertEquals("Moon name must be between 1 and 30 characters, could not update", planetFail.getMessage());
    }

    @Test
    public void testDeleteMoon_ById_Positive() {
        when(moonDao.deleteMoon(1)).thenReturn(true);

        String test = moonService.deleteMoon(1);

        assertEquals("Moon deleted successfully", test);
    }

    @Test
    public void testDeleteMoon_ByName_Positive() {
        when(moonDao.deleteMoon("Phobos")).thenReturn(true);

        String test = moonService.deleteMoon("Phobos");

        assertEquals("Moon deleted successfully", test);
    }

    @Test
    public void testDeleteMoon_Negative(){
        when(moonDao.deleteMoon(1)).thenReturn(false);

        MoonFail moonFail = assertThrows(MoonFail.class, () -> moonService.deleteMoon(1));
        assertEquals("Moon delete failed, please try again", moonFail.getMessage());
    }
}
