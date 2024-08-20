package com.revature.planetarium.integrationTests.service;

import com.revature.planetarium.Utility;
import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.UserFail;
import com.revature.planetarium.repository.user.UserDao;
import com.revature.planetarium.repository.user.UserDaoImp;
import com.revature.planetarium.service.user.UserServiceImp;
import org.junit.*;
import org.mockito.Mockito;

import java.util.Optional;

public class UserServiceImpIntegrationTests {

    private User user;

    private Planet planet;

    private Moon moon;

    private UserServiceImp userService;
    private UserDao userDao;

    @BeforeClass
    public static void setup(){

    }

    @Before
    public void beforeEach(){
        userDao = new UserDaoImp();
        userService = new UserServiceImp(userDao);
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
        // Arrange
        User newUser = new User();
        newUser.setUsername("validUserIsThirtyCharacters!!!");
        newUser.setPassword("validPassIsThirtyCharacters!!!");

        // Act
        String result = userService.createUser(newUser);

        // Assert
        Assert.assertEquals("Created user with username validUserIsThirtyCharacters!!! and password validPassIsThirtyCharacters!!!", result);

        // Verify user exists in database
        Optional<User> createdUser = userDao.findUserByUsername("validUserIsThirtyCharacters!!!");
        Assert.assertTrue(createdUser.isPresent());
        Assert.assertEquals("validUserIsThirtyCharacters!!!", createdUser.get().getUsername());
    }


    @Test
    public void createUserIntegrationTest_Negative_NonUniqueUsername(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("validUserIsNonUnique");
        newUser.setPassword("validPassIsNonUnique");

        // create first
        userService.createUser(newUser);

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.createUser(newUser);
        });
    }

    @Test
    public void createUserIntegrationTest_Negative_UsernameTooLong(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("InvalidUserIsThirtyOneCharacter");
        newUser.setPassword("validPassIsThirtyCharacters!!!");

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.createUser(newUser);
        });
    }

    @Test
    public void createUserIntegrationTest_Negative_UsernameTooShort(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("");
        newUser.setPassword("validPassIsThirtyCharacters!!!");

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.createUser(newUser);
        });
    }

    @Test
    public void createUserIntegrationTest_Negative_PasswordTooLong(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("validUserIsThirtyCharacters!!!");
        newUser.setPassword("InvalidPassIsThirtyOneCharacter");

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.createUser(newUser);
        });
    }

    @Test
    public void createUserIntegrationTest_Negative_PasswordTooShort(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("validUserIsThirtyCharacters!!!");
        newUser.setPassword("");

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.createUser(newUser);
        });
    }

    @Test
    public void authenticateIntegrationTest_Positive(){
        // Arrange
        User existingUser = new User();
        existingUser.setUsername("validUserExists");
        existingUser.setPassword("validPassExists");

        User credentials = new User();
        credentials.setUsername("validUserExists");
        credentials.setPassword("validPassExists");

        // Ensure user is created
        userDao.createUser(existingUser);

        // Act
        User result = userService.authenticate(credentials);

        // Assert
        Assert.assertEquals(existingUser, result);

        /*
        // verify
        Mockito.verify(mockUserDao).findUserByUsername("validUser");
         */
    }

    @Test
    public void authenticateIntegrationTest_Negative_UserNotFound(){
        // Arrange
        User existingUser = new User();
        existingUser.setUsername("validUserNotFound");
        existingUser.setPassword("validPassNotFound");

        User credentials = new User();
        credentials.setUsername("invalidUserNotFound");
        credentials.setPassword("validPassNotFound");

        // Ensure existing user exists
        userDao.createUser(existingUser);

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.authenticate(credentials);
        });
    }

    @Test
    public void authenticateIntegrationTest_Negative_CredentialsSentAndPasswordStoredDoNotMatch(){
        // Arrange
        User existingUser = new User();
        existingUser.setUsername("validUserExists");
        existingUser.setPassword("validPassExists");

        User credentials = new User();
        credentials.setUsername("validUserExists");
        credentials.setPassword("invalidPassExists");

        // Ensure existing user exists
        userDao.createUser(existingUser);

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.authenticate(credentials);
        });
    }
}
