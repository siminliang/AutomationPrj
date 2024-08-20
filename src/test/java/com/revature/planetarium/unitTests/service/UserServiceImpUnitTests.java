package com.revature.planetarium.unitTests.service;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.UserFail;
import com.revature.planetarium.repository.user.UserDao;
import com.revature.planetarium.service.user.UserServiceImp;
import org.junit.*;
import org.mockito.Mockito;

import java.util.Optional;

public class UserServiceImpUnitTests {

    private User user;

    private Planet planet;

    private Moon moon;

    private UserServiceImp userService;
    private UserDao mockUserDao;

    @BeforeClass
    public static void setup(){

    }

    @Before
    public void beforeEach(){
        mockUserDao = Mockito.mock(UserDao.class);
        userService = new UserServiceImp(mockUserDao);
    }

    @After
    public void afterEach(){

    }

    @AfterClass
    public static void tearDown(){

    }

    @Test
    public void createUserUnitTest_Positive(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("validUserIsThirtyCharacters!!!");
        newUser.setPassword("validPassIsThirtyCharacters!!!");

        // Mock
        Mockito.when(mockUserDao.findUserByUsername("validUserIsThirtyCharacters!!!")).thenReturn(Optional.empty());
        Mockito.when(mockUserDao.createUser(newUser)).thenReturn(Optional.of(newUser));

        // Act
        String result = userService.createUser(newUser);

        // Assert
        Assert.assertEquals("Created user with username validUserIsThirtyCharacters!!! and password validPassIsThirtyCharacters!!!", result);

        /*
        // Verify
        Mockito.verify(mockUserDao).findUserByUsername("validUser");
        Mockito.verify(mockUserDao).createUser(newUser);
         */
    }
    @Test
    public void createUserUnitTest_Negative_CreateUserRepositoryFail(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("validUserIsThirtyCharacters!!!");
        newUser.setPassword("validPassIsThirtyCharacters!!!");

        // Mock
        Mockito.when(mockUserDao.findUserByUsername("validUserIsThirtyCharacters!!!")).thenReturn(Optional.empty());
        Mockito.when(mockUserDao.createUser(newUser)).thenReturn(Optional.empty());

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.createUser(newUser);
        });
    }

    @Test
    public void createUserUnitTest_Negative_NonUniqueUsername(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("validUserIsThirtyCharacters!!!");
        newUser.setPassword("validPassIsThirtyCharacters!!!");

        // Mock
        Mockito.when(mockUserDao.findUserByUsername("validUserIsThirtyCharacters!!!")).thenReturn(Optional.of(newUser));

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.createUser(newUser);
        });
    }

    @Test
    public void createUserUnitTest_Negative_UsernameTooLong(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("InvalidUserIsThirtyOneCharacter");
        newUser.setPassword("validPassIsThirtyCharacters!!!");

        // Mock
        Mockito.when(mockUserDao.findUserByUsername("InvalidUserIsThirtyOneCharacter")).thenReturn(Optional.empty());

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.createUser(newUser);
        });
    }

    @Test
    public void createUserUnitTest_Negative_UsernameTooShort(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("");
        newUser.setPassword("validPassIsThirtyCharacters!!!");

        // Mock
        Mockito.when(mockUserDao.findUserByUsername("")).thenReturn(Optional.empty());

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.createUser(newUser);
        });
    }

    @Test
    public void createUserUnitTest_Negative_PasswordTooLong(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("validUserIsThirtyCharacters!!!");
        newUser.setPassword("InvalidPassIsThirtyOneCharacter");

        // Mock
        Mockito.when(mockUserDao.findUserByUsername("validUserIsThirtyCharacters!!!")).thenReturn(Optional.empty());

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.createUser(newUser);
        });
    }

    @Test
    public void createUserUnitTest_Negative_PasswordTooShort(){
        // Arrange
        User newUser = new User();
        newUser.setUsername("validUserIsThirtyCharacters!!!");
        newUser.setPassword("");

        // Mock
        Mockito.when(mockUserDao.findUserByUsername("validUserIsThirtyCharacters!!!")).thenReturn(Optional.empty());

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.createUser(newUser);
        });
    }

    @Test
    public void authenticateUnitTest_Positive(){
        // Arrange
        User existingUser = new User();
        existingUser.setUsername("validUserExists");
        existingUser.setPassword("validPassExists");

        User credentials = new User();
        credentials.setUsername("validUserExists");
        credentials.setPassword("validPassExists");

        // Mock
        Mockito.when(mockUserDao.findUserByUsername("validUserExists")).thenReturn(Optional.of(existingUser));

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
    public void authenticateUnitTest_Negative_UserNotFound(){
        // Arrange
        User existingUser = new User();
        existingUser.setUsername("validUserExists");
        existingUser.setPassword("validPassExists");

        User credentials = new User();
        credentials.setUsername("invalidUserExists");
        credentials.setPassword("validPassExists");

        // Mock
        Mockito.when(mockUserDao.findUserByUsername("validUserExists")).thenReturn(Optional.of(existingUser));
        Mockito.when(mockUserDao.findUserByUsername("invalidUserExists")).thenReturn(Optional.empty());

        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.authenticate(credentials);
        });
    }

    @Test
    public void authenticateUnitTest_Negative_CredentialsSentAndPasswordStoredDoNotMatch(){
        // Arrange
        User existingUser = new User();
        existingUser.setUsername("validUserExists");
        existingUser.setPassword("validPassExists");

        User credentials = new User();
        credentials.setUsername("validUserExists");
        credentials.setPassword("invalidPassExists");

        // Mock
        Mockito.when(mockUserDao.findUserByUsername("validUserExists")).thenReturn(Optional.of(existingUser));


        // Act and Assert
        Assert.assertThrows(UserFail.class,()->{
            userService.authenticate(credentials);
        });
    }

}
