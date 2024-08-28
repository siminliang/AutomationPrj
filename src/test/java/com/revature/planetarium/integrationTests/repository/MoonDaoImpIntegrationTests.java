package com.revature.planetarium.integrationTests.repository;

import com.revature.Setup;
import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.exceptions.MoonFail;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.repository.moon.MoonDaoImp;
import org.junit.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

public class MoonDaoImpIntegrationTests {
    private Moon moon;

    private MoonDao moonDaoImp;
    private static String moonImage;

    @BeforeClass
    public static void setup() throws IOException {
        String filepath = "src/test/resources/Celestial-Images/moon-1.jpg";
        try(FileInputStream fis = new FileInputStream(filepath)){
            byte[] fileBytes = fis.readAllBytes();
            moonImage = Base64.getEncoder().encodeToString(fileBytes);
        }
    }

    @Before
    public void beforeEach(){
        moonDaoImp = new MoonDaoImp();

        moon = new Moon();
        moon.setMoonName("Lunar");
        moon.setOwnerId(1);
        moon.setImageData(moonImage);
    }

    @After
    public void afterEach(){
    }

    @AfterClass
    public static void tearDown(){
        Setup.resetTestDatabase();
    }

    //createMoon
    @Test
    public void createMoon_integrationTest_positive(){
        Optional<Moon> newMoon = moonDaoImp.createMoon(moon);
        Assert.assertTrue(newMoon.isPresent());
        Assert.assertEquals("Lunar", newMoon.get().getMoonName());
    }

    @Test
    public void createMoon_noImage_integrationTest_positive(){
        Moon moon = new Moon();
        moon.setMoonName("Moon");
        moon.setOwnerId(1);
        //moon.setImageData(moonImage);

        Optional<Moon> newMoon = moonDaoImp.createMoon(moon);
        Assert.assertTrue(newMoon.isPresent());
        Assert.assertEquals("Moon", newMoon.get().getMoonName());
    }

    @Test
    public void createMoon_integrationTest_negative(){
        Moon newMoon = new Moon();
        Assert.assertThrows(MoonFail.class, () -> moonDaoImp.createMoon(newMoon));
    }

    //readMoonId
    @Test
    public void readMoon_byId_integrationTest_positive(){
        Optional<Moon> readMoon = moonDaoImp.readMoon(1);
        Assert.assertTrue(readMoon.isPresent());
        Assert.assertEquals(1, readMoon.get().getMoonId());
        Assert.assertEquals("Luna", readMoon.get().getMoonName());
    }

    @Test
    public void readMoon_byId_integrationTest_negative(){
        Optional<Moon> readMoon = moonDaoImp.readMoon(999);
        Assert.assertFalse(readMoon.isPresent());
    }

    @Test
    public void readMoon_byName_integrationTest_positive(){
        Optional<Moon> readMoon = moonDaoImp.readMoon("Luna");
        Assert.assertTrue(readMoon.isPresent());
        Assert.assertEquals(1, readMoon.get().getMoonId());
        Assert.assertEquals("Luna", readMoon.get().getMoonName());
    }

    @Test
    public void readMoon_byName_integrationTest_negative(){
        Optional<Moon> readMoon = moonDaoImp.readMoon("non-existing moon");
        Assert.assertFalse(readMoon.isPresent());
    }

    @Test
    public void readAllMoons_integrationTest_positive() {
        Assert.assertFalse(moonDaoImp.readAllMoons().isEmpty());
    }

    //readMoonsByPlanet
    @Test
    public void readMoonsByPlanet_integrationTest_positive(){
        int size = moonDaoImp.readMoonsByPlanet(1).size();
        Assert.assertTrue(size > 0);
    }

    @Test
    public void readMoonByPlanet_integrationTest_negative(){
        int size = moonDaoImp.readMoonsByPlanet(999).size();
        Assert.assertEquals(0, size);
    }

    @Test
    public void updateMoon_integrationTest_positive(){
        Optional<Moon> testMoon = moonDaoImp.createMoon(moon);
        Assert.assertTrue(testMoon.isPresent());

        Moon updatedMoon = testMoon.get();
        updatedMoon.setMoonName("New Lunar");

        Optional<Moon> updatedResult = moonDaoImp.updateMoon(updatedMoon);
        Assert.assertTrue(updatedResult.isPresent());
        Assert.assertEquals("New Lunar", updatedResult.get().getMoonName());
    }

    @Test
    public void updateMoon_integrationTest_negative(){
        Moon updateMoon = new Moon();
        updateMoon.setMoonName("validMoon");
        updateMoon.setOwnerId(1);

        Optional<Moon> validMoonName = moonDaoImp.createMoon(updateMoon);
        Assert.assertTrue(validMoonName.isPresent());
        updateMoon.setMoonName("invalidNameThatIsTooLongToBeAMoonName");

        Assert.assertThrows(MoonFail.class, () -> moonDaoImp.updateMoon(updateMoon));
    }

    @Test
    public void deleteMoon_byId_integrationTest_positive(){
        Optional<Moon> lunar = moonDaoImp.createMoon(moon);
        Assert.assertTrue(lunar.isPresent());
        int lunarId = lunar.get().getMoonId();

        boolean deleted = moonDaoImp.deleteMoon(lunarId);
        Assert.assertTrue(deleted);

        lunar = moonDaoImp.readMoon(lunarId);
        Assert.assertFalse(lunar.isPresent());
    }

    @Test
    public void deleteMoon_byId_integrationTest_negative(){
        boolean deleted = moonDaoImp.deleteMoon(999);
        Assert.assertFalse(deleted);
    }

    @Test
    public void deleteMoon_byName_integrationTest_positive(){
        Optional<Moon> lunar = moonDaoImp.createMoon(moon);
        Assert.assertTrue(lunar.isPresent());
        String moonName = lunar.get().getMoonName();

        boolean deleted = moonDaoImp.deleteMoon(moonName);
        Assert.assertTrue(deleted);

        lunar = moonDaoImp.readMoon(moonName);
        Assert.assertFalse(lunar.isPresent());
    }

    @Test
    public void deleteMoon_byName_integrationTest_negative(){
        boolean deleted = moonDaoImp.deleteMoon("not a moon");
        Assert.assertFalse(deleted);
    }
}
