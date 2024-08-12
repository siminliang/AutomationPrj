@ViewCelestialBodies
Feature:  View Celestial Bodies
  Scenario Outline: User wants to see all the available celestial bodies in planetarium
    Given The User is already logged in with "<username>" and  "<password>"
    When  User is redirected to the Planetarium
    Then User see all the available celestial Bodies.

    Examples:
      | username | password |
      | UsernameIsThirtyCharactersLong   | PasswordIsThirtyCharactersLong |

  Scenario Outline: User should be able to see new added celestial bodies.
    Given The User is already logged in with "<username>" and  "<password>"
    Given No planet with name "<planetname>" in planetarium
    When  User is redirected to the Planetarium
    When User add new planet "<planetname>"
    Then User see all the available celestial Bodies.
    Examples:
      | username | password | planetname |
      | UsernameIsThirtyCharactersLong   | PasswordIsThirtyCharactersLong | PlanetIsThirtyCharactersLong!! |


  Scenario Outline: User should be not able to see newly deleted celestial bodies.
    Given The User is already logged in with "<username>" and  "<password>"
    Given The planet "<planetname>" is already there
    When  User is redirected to the Planetarium
    When User delete new planet "<planetname>"
    Then User see all the available celestial Bodies.
    Examples:
      | username | password | planetname |
      | UsernameIsThirtyCharactersLong   | PasswordIsThirtyCharactersLong | PlanetIsThirtyCharactersLong!! |