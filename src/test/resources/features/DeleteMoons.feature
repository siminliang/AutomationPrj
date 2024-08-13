@DeleteMoons
Feature: DeleteMoons

	@SCRUM-TC-31
	Scenario Outline: As a user I want to be able to remove Moons from the Planetarium
	Delete Moon Positive Scenario
		Given Moon name "<MoonName>" exist
		Given The User is already log on
		When The User selects moon from drop-down menu
		When User enters valid moon name "<MoonName>" for celestial body to be deleted
		When User clicks on the Delete Button
		Then The moon "<MoonName>" should be deleted from the planetarium

	Examples: 
		| MoonName |
		| MoonsAreThirtyCharactersLong!!    |
		| 303030303030303030303030303030      |

	@SCRUM-TC-32
	Scenario Outline: As a user I should not be able to remove Moon from the Planetarium when given invalid names
	Delete Moon Negative Scenario
		Given The User is already log on
		Given There is no Moon named "<invalidMoonNames>" in planetarium
		When The User selects moon from drop-down menu
		When User enters invalid "<invalidMoonNames>"
		When User clicks on the Delete Button
		Then The user should see error message pop-up

	Examples: 
		| invalidMoonNames   |
		| NoMoonInDatabaseToDeleteHere!! |
		| 3131313131313131313131313131311                               |

	@SCRUM-TC-34
	Scenario Outline: User should not be able to delete a moon by  its ID
		Given The User is already log on
		Given Moon with ID "<ID>" exists
		When The User selects moon from drop-down menu
		When User enters moon id "<ID>" for celestial body to be deleted
		When User clicks on the Delete Button
		Then The user should see error
		And The moon with ID "<ID>" should not be deleted

	Examples:
		| ID |
		| 1  |