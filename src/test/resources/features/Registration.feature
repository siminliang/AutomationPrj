@Registration
Feature: Registration

	@SCRUM-TC-19
	Scenario Outline: As a User I want to Register an Account with the Planetarium so that I can Login Securely
	Registration Feature Testing Positive Scenario
		Given The User is on the Login Page
		Given No Registered User with username "<username>"
		When The User clicks on Create an Account Button
		When The User enters "<username>" into registration username input bar
		When The User enters "<password>" into registration password input bar
		When The User clicks on the Create Button
		Then The User is registered and redirected into the Planetarium Login page

	Examples: 
		| username                       | password                       |
		| Paul muad'dib atreides!!!!!!!! | Air power sea power desert30!! |

	@SCRUM-TC-20
	Scenario Outline: As the System I don't want a user to Register an Account using invalid usernames and passwords so that I can ensure system requirements are met
	Registration Feature Testing Positive Scenario
		Given The User is on the Login Page
		Given Account with username "Lisan" and password "al-gaib" already registered
		When The User clicks on Create an Account Button
		When The User enters "<username>" into registration username input bar
		When The User enters "<password>" into registration password input bar
		When The User clicks on the Create Button
		Then The User is kept at the Registration page

	Examples: 
		| username                        | password                        |
		| Lisan                           | atreidies                       |
		| House Harkonnen owns Arrakis!!! | Air power sea power desert30!!  |
		| House Harkonnen owns Arrakis!!! | The Lisan will liberate Arrakis |
		|                                 | Air power sea power desert30!!  |
		|                                 | The Lisan will liberate Arrakis |
		| Paul muad'dib atreides!!!!!!!!  |                                 |
		| House Harkonnen owns Arrakis!!! |                                 |
		| Paul muad'dib atreides!!!!!!!!  | The Lisan will liberate Arrakis |