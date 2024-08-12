@AddMoons
Feature: AddMoons

  @SCRUM-TC-22
  Scenario Outline: As a User I should be able to add Moon (Positive Scenario)
    Given The User is already log on
    Given No Planet or Moon with name "<MoonName>" in planetarium
    Given Planet with ID "<OrbitedPlanetID>" exists
    When The User selects planets from the drop-down menu
    When The User selects moon from the drop-down menu
    When The User enters "<MoonName>" for moon name
    When The User enters "<OrbitedPlanetID>" for the planet that the moon is orbiting
    When "<image>" The User selects an image from file explorer for moon image
    When The User clicks on the submit button
    Then The Moon "<MoonName>" should be added to planetarium

  Examples:
    | MoonName | OrbitedPlanetID | image       |
    | MoonsAreThirtyCharactersLong!!     | 3               | moon-1.jpg  |
    | MoonsAreThirtyCharactersLong!!    | 3               |             |
    | 303030303030303030303030303030     | 3               | moon-1.jpg  |
    | 303030303030303030303030303030    | 3               |             |


  @SCRUM-TC-23
  Scenario Outline: As a User I should be able to add Moon (Negative Scenario)
    Given The User is already log on
    Given Planet with ID "3" exists
    Given The ID of the Planet "10" does not exist in the Planetarium
    Given No Planet or Moon with name "<MoonName>" in planetarium
    Given Moon name "AlreadyAddedMoonInTheDatabase!" exist
    When The User selects planets from the drop-down menu
    When The User selects moon from the drop-down menu
    When The User enters "<MoonName>" for moon name
    When The User enters "<OrbitedPlanetID>" for the planet that the moon is orbiting
    When "<image>" The User selects an image from file explorer for moon image
    When The User clicks on the submit button
    Then The Moon "<MoonName>" should not be added to planetarium

  Examples:
      | MoonName                                            | OrbitedPlanetID | image       |
      | AlreadyAddedMoonInTheDatabase!                                               | 3               | moon-1.jpg  |
      | AlreadyAddedMoonInTheDatabase!                                               | 3               |             |
      | MoonIsThirtyOneCharactersLong!!  | 3               | moon-1.jpg  |
      | MoonIsThirtyOneCharactersLong!!  | 3               |             |
      | 3131313131313131313131313131311  | 3               | moon-1.jpg  |
      | 3131313131313131313131313131311  | 3               |             |
      | MoonsAreThirtyCharactersLong!!                                              | 10              | moon-1.jpg  |
      | MoonsAreThirtyCharactersLong!!                                              | 10              |             |