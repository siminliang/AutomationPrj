@DeletePlanets
Feature: DeletePlanets


	Scenario Outline: As a user I want to be able to remove Planets from the Planetarium
	Delete Planet Positive Scenario
		Given The User is already log on
		Given Given Planet names "<PlanetName>" exists
		When The User selects planets from drop-down menu
		When User enters valid "<PlanetName>" for celestial body to be deleted
		When User clicks on the Delete Button
		Then The planet "<PlanetName>" should be deleted from the Planetarium

	Examples: 
		| PlanetName |
		| Namke      |
		| Vegeta     |
		| 777        |
		| 888        |


	Scenario Outline: As a user I want to be able to remove Planets from the Planetarium, Negative Scenario
	Delete Planet Negative Scenario
		Given The User is already log on
		Given No planet with name "<PlanetName>" exist
		When The User selects planets from the drop-down menu
		When User enters invalid "<PlanetName>" for celestial body to be deleted
		When User clicks on the Delete Button
		Then The user should see error message pop-up

	Examples: 
		| PlanetName     |
		| ZaWarudo       |
		| toki yo tomare |


	Scenario Outline: User should not be able to delete a planet by its ID
		Given The User is already log on
		Given Planet with ID "<ID>" exists
		When The User selects planets from drop-down menu
		When User enters planet ID "<ID>" for celestial body to be deleted
		When User clicks on the Delete Button
		Then The user should see error
		And The planet with ID "<ID>" should not be deleted

	Examples: 
		| ID |
		| 1  |