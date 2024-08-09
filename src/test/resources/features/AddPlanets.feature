@AddPlanets
Feature: AddPlanets

	@SCRUM-TC-24
	Scenario Outline: As a User I want to add new planets to the Planetarium, so I can track the planet in the night sky
	
Add Planets Test Positive Scenario
		Given The User is already log on
		Given No planet with name "<PlanetName>" in planetarium
		When The User selects planets from the drop-down menu
		When The User enters "<PlanetName>" for planet name
		When "<image>" The User selects an image from filesystem for planet image
		When The User clicks on the submit button
		Then The planet "<PlanetName>" should be added to the planetarium

	Examples: 
		| PlanetName                     | image |
		| this planet name is 30 chars!! | true  |
		| Terra                          | true  |
		| this planet name is 30 chars!! | false |
		| Terra                          | false |

	@SCRUM-TC-25
	Scenario Outline: As a User I want to add new planets to the Planetarium, so I can track the planet in the night sky
	Add Planets Test Negative Scenario
		Given The User is already log on
		When The User selects planets from the drop-down menu
		When The User enters "<PlanetName>" for planet name
		When "<image>" The User selects an image from filesystem for planet image
		When The User clicks on the submit button
		Then The planet "<PlanetName>" should not be added to the planetarium

	Examples: 
		| PlanetName                      | image |
		| this planet name is 31 chars!!! | true  |
		| nonunique                       | true  |
		| this planet name is 31 chars!!! | false |
		| nonunique                       | false|