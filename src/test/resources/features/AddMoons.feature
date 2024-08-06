@AddMoons
Feature: AddMoons

  @SCRUM-TC-22
  Scenario Outline: As a User I should be able to add Moon (Positive Scenario)
    Given The User is already log on.
    Given No Planet or Moon name "<MoonName>" in Planetarium
#    Given The "<OrbitedPlanetID>" Planet that the moon orbiting in Planetarium
    When The User selects planets from the drop-down menu
    When The Use select moon from the drop-down menu
    When The User enters "<MoonName>" for moon name
    When The User enters "<OrbitedPlanetID>" for the planet that the moon is orbiting
    When "<image>" The User selects an image from file explorer for moon image
    When The User clicks on the submit button
    Then The Moon "<MoonName>" should be added to planetarium

  Examples:
    | MoonName | OrbitedPlanetID | image |
    | Noom     | 1               | true  |
    | Noom     | 1               | false |


  @SCRUM-TC-23
  Scenario Outline: As a User I should be able to add Moon (Negative Scenario)
    Given The User is already log on.
    Given There is no more than 4 planets (Orbited ID should be at maxed 4)
    When The User selects planets from the drop-down menu
    When The Use select moon from the drop-down menu
    When The User enters "<MoonName>" for moon name
    When The User enters "<OrbitedPlanetID>" for the planet that the moon is orbiting
    When "<image>" The User selects an image from file explorer for moon image
    When The User clicks on the submit button
    Then The Moon "<MoonName>" should not be added to planetarium

  Examples:
      | MoonName                                            | OrbitedPlanetID | image |
      | Titan                                               | 1               | true  |
      | Titan                                               | 1               | false |
      | SuperNoomIsTheBestMoonInTheGalaxyAndItNotEvenClose  | 1               | true  |
      | SuperNoomIsTheBestMoonInTheGalaxyAndItNotEvenClose  | 1               | false |
      | Normal                                              | 10              | true  |
      | Normal                                              | 10              | false |