package com.revature;

import com.revature.planetarium.Utility;
import com.revature.planetarium.integrationTests.repository.MoonDaoImpIntegrationTests;
import com.revature.planetarium.integrationTests.repository.PlanetDaoImpIntegrationTests;
import com.revature.planetarium.integrationTests.repository.UserDaoImpIntegrationTests;
import com.revature.planetarium.integrationTests.service.MoonServiceImpIntegrationTests;
import com.revature.planetarium.integrationTests.service.PlanetServiceImpIntegrationTests;
import com.revature.planetarium.integrationTests.service.UserServiceImpIntegrationTests;
import com.revature.planetarium.unitTests.controller.UserControllerUnitTests;
import com.revature.planetarium.unitTests.repository.MoonDaoImpUnitTests;
import com.revature.planetarium.unitTests.repository.PlanetDaoImpUnitTests;
import com.revature.planetarium.unitTests.repository.UserDaoImpUnitTests;
import com.revature.planetarium.unitTests.service.MoonServiceImpUnitTests;
import com.revature.planetarium.unitTests.service.PlanetServiceImpUnitTests;
import com.revature.planetarium.unitTests.service.UserServiceImpUnitTests;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
    A runner class can be used to facilitate executing test suite in your code. Any test
    classes you associate with your runner can be executed all together. This gives you
    greater control over what tests you execute and when you execute them.
 */
@RunWith(Suite.class)
@SuiteClasses(
        {
                //UserControllerUnitTests.class,
                UserServiceImpUnitTests.class,
                UserServiceImpIntegrationTests.class,
                UserDaoImpUnitTests.class,
                UserDaoImpIntegrationTests.class,
                MoonDaoImpUnitTests.class,
                MoonDaoImpIntegrationTests.class,
                MoonServiceImpUnitTests.class,
                MoonServiceImpIntegrationTests.class,
                PlanetDaoImpUnitTests.class,
                PlanetDaoImpIntegrationTests.class,
                PlanetServiceImpUnitTests.class,
                PlanetServiceImpIntegrationTests.class
        }
)
public class CapstoneTestRunner {

    // TO RUN TESTS / CREATE MAVEN RESULTS TO UPLOAD TO JIRA:
    // mvn clean test
    @BeforeClass
    public static void runnerSetup(){
        System.out.println("This method will run before any tests in the suite");

        // reset the database everytime we run our tests
        Utility.resetTestDatabase();
    }

}
