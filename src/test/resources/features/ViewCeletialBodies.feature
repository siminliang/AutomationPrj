@ViewCelestialBodies
Feature:  View Celestial Bodies
  Scenario Outline: User wants to see all the available celestial bodies in planetarium
    Given The User is already logged in with "<username>" and  "<password>"
    Then  User is redirected to the Planetarium
    Then User see all the available celestial Bodies.

    Examples:
      | username | password |
      | UsernameIsThirtyCharactersLong   | PasswordIsThirtyCharactersLong |
