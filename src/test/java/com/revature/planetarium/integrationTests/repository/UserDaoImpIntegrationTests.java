package com.revature.planetarium.integrationTests.repository;

import com.revature.planetarium.Utility;
import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.UserFail;
import com.revature.planetarium.repository.user.UserDaoImp;
import com.revature.planetarium.utility.DatabaseConnector;
import org.junit.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserDaoImpIntegrationTests {
    private User user;

    private Planet planet;

    private Moon moon;

    private UserDaoImp userDaoImp;


    @BeforeClass
    public static void setup(){


    }

    @Before
    public void beforeEach(){
        // Initialize the UserDaoImp instance
        userDaoImp = new UserDaoImp();

        // Create a connection and setup the database schema
        Utility.resetTestDatabase();

    }

    @After
    public void afterEach(){

    }

    @AfterClass
    public static void tearDown(){

    }

    @Test
    public void createUserIntegrationTest_Positive(){
        // Prepare the User object
        User newUser = new User();
        newUser.setUsername("testuserPositive");
        newUser.setPassword("testpassPositive");

        // Call the method to test
        Optional<User> result = userDaoImp.createUser(newUser);

        // Verify the result
        Assert.assertTrue(result.isPresent());
        Assert.assertNotNull(result.get().getId());
        Assert.assertEquals("testuserPositive", result.get().getUsername());
    }

    @Test
    public void createUserIntegrationTest_Negative_NonUniqueUsername(){
        // Prepare the User objects
        User user1 = new User();
        user1.setUsername("AlreadyRegisteredUsername!!!!!");
        user1.setPassword("testpass");

        User user2 = new User();
        user2.setUsername("AlreadyRegisteredUsername!!!!!");  // Same username
        user2.setPassword("testpass2");

        // Insert the first user
        userDaoImp.createUser(user1);

        // Attempt to insert the second user with a duplicate username
        Assert.assertThrows(UserFail.class, () -> {
            userDaoImp.createUser(user2);
        });
    }

    @Test
    public void createUserIntegrationTest_Negative_UsernameTooLong(){
        // Prepare the User objects
        User user = new User();
        user.setUsername("UserIsNotThirtyCharactersLong!!");
        user.setPassword("PasswordIsThirtyCharactersLong");

        // Attempt to insert user with too long username
        Assert.assertThrows(UserFail.class, () -> {
            userDaoImp.createUser(user);
        });
    }

    @Test
    public void createUserIntegrationTest_Negative_PasswordTooLong(){
        // Prepare the User objects
        User user = new User();
        user.setUsername("UsernameIsThirtyCharactersLong");
        user.setPassword("PassIsNotThirtyCharactersLong!!");

        // Attempt to insert the user with too long password
        Assert.assertThrows(UserFail.class, () -> {
            userDaoImp.createUser(user);
        });
    }

    @Test
    public void createUserIntegrationTest_Negative_PasswordNull(){
        // Prepare the User objects
        User user = new User();
        user.setUsername("UsernameIsThirtyCharactersLong");
        // dont set password
        //user.setPassword("PasswordIsThirtyCharactersLong");

        // Attempt to insert the user with too long password
        Assert.assertThrows(UserFail.class, () -> {
            userDaoImp.createUser(user);
        });
    }

    @Test
    public void createUserIntegrationTest_Negative_UsernameNull(){
        // Prepare the User objects
        User user = new User();
        // dont set username
        //user.setUsername("UsernameIsThirtyCharactersLong");
        user.setPassword("PasswordIsThirtyCharactersLong");

        // Attempt to insert the user with too long password
        Assert.assertThrows(UserFail.class, () -> {
            userDaoImp.createUser(user);
        });
    }

    @Test
    public void createUserIntegrationTest_Negative_UsernameAndPasswordNull(){
        // Prepare the User objects
        User user = new User();
        // dont set username or password
        //user.setUsername("UsernameIsThirtyCharactersLong");
        //user.setPassword("PasswordIsThirtyCharactersLong");

        // Attempt to insert the user with too long password
        Assert.assertThrows(UserFail.class, () -> {
            userDaoImp.createUser(user);
        });
    }

    @Test
    public void findUserByUsernameIntegrationTest_Positive(){
        // Prepare the User object
        User newUser = new User();
        newUser.setUsername("testuserSearchPositive");
        newUser.setPassword("testpassSearchPositive");

        // Insert the user first
        userDaoImp.createUser(newUser);

        // Call the method to test
        Optional<User> result = userDaoImp.findUserByUsername("testuserSearchPositive");

        // Verify the result
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("testuserSearchPositive", result.get().getUsername());
    }

    @Test
    public void findUserByUsernameIntegrationTest_Negative_UserNotFound(){
        // Prepare the User object
        User newUser = new User();
        newUser.setUsername("testuserSearchNegative");
        newUser.setPassword("testpassSearchNegative");

        // Do not insert user, we want to find a user that does not exist
        // !userDaoImp.createUser(newUser);

        // Call the method to test and asset
        Optional<User> result = userDaoImp.findUserByUsername("testuserSearchNegative");

        // Verify the result
        Assert.assertFalse(result.isPresent());


    }
}
