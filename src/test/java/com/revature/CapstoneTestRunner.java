package com.revature;

import com.revature.planetarium.integrationTests.repository.UserDaoImpIntegrationTests;
import com.revature.planetarium.integrationTests.service.UserServiceImpIntegrationTests;
import com.revature.planetarium.unitTests.controller.UserControllerUnitTests;
import com.revature.planetarium.unitTests.repository.UserDaoImpUnitTests;
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
                UserControllerUnitTests.class,
                UserServiceImpUnitTests.class,
                UserServiceImpIntegrationTests.class,
                UserDaoImpUnitTests.class,
                UserDaoImpIntegrationTests.class
        }
)
public class CapstoneTestRunner {

    @BeforeClass
    public static void runnerSetup(){
        System.out.println("This method will run before any tests in the suite");
    }

}
