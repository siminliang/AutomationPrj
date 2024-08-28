package com.revature.planetarium.integrationTests.repository;

import com.revature.Setup;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import org.junit.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

public class PlanetDaoImpIntegrationTests {

    private Planet planet;

    private PlanetDao planetDaoImp;
    private static String planetImage;

    @BeforeClass
    public static void setup() throws IOException{
        String filepath = "src/test/resources/Celestial-Images/planet-1.jpg";
        try(FileInputStream fis = new FileInputStream(filepath)){
            byte[] fileBytes = fis.readAllBytes();
            planetImage = Base64.getEncoder().encodeToString(fileBytes);
        }
    }

    @Before
    public void beforeEach(){
        planetDaoImp = new PlanetDaoImp();

        planet = new Planet();
        planet.setPlanetName("Jupiter");
        planet.setOwnerId(1);
        planet.setImageData(planetImage);
    }

    @After
    public void afterEach(){

    }

    @AfterClass
    public static void tearDown(){
        Setup.resetTestDatabase();
    }

    @Test
    public void createPlanet_integrationTest_positive(){
        Optional<Planet> newPlanet = planetDaoImp.createPlanet(planet);
        Assert.assertTrue(newPlanet.isPresent());
        Assert.assertEquals("Jupiter", newPlanet.get().getPlanetName());
    }

    @Test
    public void createPlanet_noImage_integrationTest_positive(){
        Planet noImagePlanet = new Planet();
        noImagePlanet.setPlanetName("Planet");
        noImagePlanet.setOwnerId(1);

        Optional<Planet> newPlanet = planetDaoImp.createPlanet(noImagePlanet);
        Assert.assertTrue(newPlanet.isPresent());
        Assert.assertEquals("Planet", newPlanet.get().getPlanetName());
    }

    @Test
    public void createPlanet_integrationTest_negative(){
        Planet newPlanet = new Planet();
        Assert.assertThrows(PlanetFail.class, () -> planetDaoImp.createPlanet(newPlanet));
    }

    @Test
    public void readPlanet_byId_integrationTest_positive(){
        Optional<Planet> readPlanet = planetDaoImp.readPlanet(1);
        Assert.assertTrue(readPlanet.isPresent());
        Assert.assertEquals(1, readPlanet.get().getPlanetId());
        Assert.assertEquals("Earth", readPlanet.get().getPlanetName());
    }

    @Test
    public void readPlanet_byId_integrationTest_negative(){
        Optional<Planet> readPlanet = planetDaoImp.readPlanet(999);
        Assert.assertFalse(readPlanet.isPresent());
    }

    @Test
    public void readPlanet_byName_integrationTest_positive(){
        Optional<Planet> readPlanet = planetDaoImp.readPlanet("Earth");
        Assert.assertTrue(readPlanet.isPresent());
        Assert.assertEquals(1, readPlanet.get().getPlanetId());
        Assert.assertEquals("Earth", readPlanet.get().getPlanetName());
    }

    @Test
    public void readPlanet_byName_integrationTest_negative(){
        Optional<Planet> readPlanet = planetDaoImp.readPlanet("non-existing planet");
        Assert.assertFalse(readPlanet.isPresent());
    }

    @Test
    public void readAllPlanets_integrationsTest_positive(){
        Assert.assertFalse(planetDaoImp.readAllPlanets().isEmpty());
    }

    @Test
    public void readPlanetByOwner_integrationTest_positive(){
        int size = planetDaoImp.readPlanetsByOwner(1).size();
        Assert.assertTrue(size > 0);
    }

    @Test
    public void readPlanetByOwner_integrationTest_negative(){
        int size = planetDaoImp.readPlanetsByOwner(999).size();
        Assert.assertEquals(0, size);
    }

    @Test
    public void updatePlanet_integrationTest_positive(){
        Optional<Planet> testPlanet = planetDaoImp.createPlanet(planet);
        Assert.assertTrue(testPlanet.isPresent());

        Planet updatePlanet = testPlanet.get();
        updatePlanet.setPlanetName("New Earth");

        Optional<Planet> updateResult = planetDaoImp.updatePlanet(updatePlanet);
        Assert.assertTrue(updateResult.isPresent());
        Assert.assertEquals("New Earth", updateResult.get().getPlanetName());
    }

    @Test
    public void updatePlanet_integrationTest_negative(){
        Planet updatePlanet = new Planet();
        updatePlanet.setOwnerId(1);
        updatePlanet.setPlanetName("validName");

        Optional<Planet> validPlanetName = planetDaoImp.createPlanet(updatePlanet);
        Assert.assertTrue(validPlanetName.isPresent());
        updatePlanet.setPlanetName("invalidNameThatIsTooLongToBeAPlanetName");

        Assert.assertThrows(PlanetFail.class, () -> planetDaoImp.updatePlanet(updatePlanet));
    }

    @Test
    public void deletePlanet_byId_integrationTest_positive(){
        Optional<Planet> jupiter = planetDaoImp.createPlanet(planet);
        Assert.assertTrue(jupiter.isPresent());
        int jupiterId = jupiter.get().getPlanetId();

        boolean deleted = planetDaoImp.deletePlanet(jupiterId);
        Assert.assertTrue(deleted);

        jupiter = planetDaoImp.readPlanet(jupiterId);
        Assert.assertFalse(jupiter.isPresent());
    }

    @Test
    public void deletePlanet_byId_integrationTest_negative(){
        boolean deleted = planetDaoImp.deletePlanet(999);
        Assert.assertFalse(deleted);
    }

    @Test
    public void deletePlanet_byName_integrationTest_positive(){
        Optional<Planet> jupiter = planetDaoImp.createPlanet(planet);
        Assert.assertTrue(jupiter.isPresent());
        String planetName = jupiter.get().getPlanetName();

        boolean deleted = planetDaoImp.deletePlanet(planetName);
        Assert.assertTrue(deleted);

        jupiter = planetDaoImp.readPlanet(planetName);
        Assert.assertFalse(jupiter.isPresent());
    }

    @Test
    public void deletePlanet_byName_integrationTest_negative(){
        boolean deleted = planetDaoImp.deletePlanet("not a planet");
        Assert.assertFalse(deleted);
    }
}
