@ViewCelestialBodies
Feature:  View Celestial Bodies
  Scenario Outline: User wants to see all the available celestial bodies in planetarium
    Given The User is already logged in with "<username>" and  "<password>"
    Then  User is redirected to the Planetarium
    Then User see all the available celestial Bodies.

    Examples:
      | username | password |
      | Robin   | I am the day |


  Scenario Outline: User should be able to see new added celestial bodies.
    Given The User is already logged in with "<username>" and  "<password>"
    Then  User is redirected to the Planetarium
    Then User add new planet "<planetname>"
    Then User see all the available celestial Bodies.
    Examples:
      | username | password | planetname |
      | Robin   | I am the day | planetX |


  Scenario Outline: User should be not able to see newly deleted celestial bodies.
    Given The User is already logged in with "<username>" and  "<password>"
    Given The planet "<planetname>" is already there
    Then  User is redirected to the Planetarium
    Then User delete new planet "<planetname>"
    Then User see all the available celestial Bodies.
    Examples:
      | username | password | planetname |
      | Robin   | I am the day | planetX |
