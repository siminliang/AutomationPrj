@AddMoons
Feature: AddMoons

  @SCRUM-TC-22
  Scenario Outline: As a User I should be able to add Moon (Positive Scenario)
    Given The User is already log on.
    Given No Planet or Moon with name "<MoonName>" in planetarium
    Given The ID of the Planet "<OrbitedPlanetID>" that the moon orbiting does exist in Planetarium
    When The User selects planets from the drop-down menu
    When The User selects moon from the drop-down menu
    When The User enters "<MoonName>" for moon name
    When The User enters "<OrbitedPlanetID>" for the planet that the moon is orbiting
    When "<image>" The User selects an image from file explorer for moon image
    When The User clicks on the submit button
    Then The Moon "<MoonName>" should be added to planetarium

  Examples:
    | MoonName | OrbitedPlanetID | image       |
    | Noom     | 1               | moon-1.jpg  |
    | Noom     | 1               |             |


  @SCRUM-TC-23
  Scenario Outline: As a User I should be able to add Moon (Negative Scenario)
    Given The User is already log on.
    Given The ID of the Planet "<OrbitedPlanetID>" does not exist in the Planetarium
    Given Moon name "Titan" exist
    When The User selects planets from the drop-down menu
    When The User selects moon from the drop-down menu
    When The User enters "<MoonName>" for moon name
    When The User enters "<OrbitedPlanetID>" for the planet that the moon is orbiting
    When "<image>" The User selects an image from file explorer for moon image
    When The User clicks on the submit button
    Then The Moon "<MoonName>" should not be added to planetarium

  Examples:
      | MoonName                                            | OrbitedPlanetID | image       |
      | Titan                                               | 1               | moon-1.jpg  |
      | Titan                                               | 1               |             |
      | SuperNoomIsTheBestMoonInTheGalaxyAndItNotEvenClose  | 1               | moon-1.jpg  |
      | SuperNoomIsTheBestMoonInTheGalaxyAndItNotEvenClose  | 1               |             |
      | Normal                                              | 10              | moon-1.jpg  |
      | Normal                                              | 10              |             |