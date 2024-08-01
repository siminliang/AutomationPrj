@Login
Feature: Login

	@SCRUM-TC-17
	Scenario Outline: As a User I want to Login to my Account so that I can Enter the Planetarium
	Login Functionality Test Positive Scenario
		Given The User is on the Login Page
		When The User enters "<username>" into username input bar
		When The User enters "<password>" into password input bar
		When The User clicks on the Login Button
		Then The User is redirected to the Planetarium

	Examples: 
		| username                        | password                     |
		| Batman | I am the night |

	@SCRUM-TC-18
	Scenario Outline: As the System I don't want a user to Login to an Account using an invalid Username and Password combo so that I can ensure my user accounts are secure
	Login Functionality Test Negative Scenario
		Given The User is on the Login Page
		When The User enters "<username>" into username input bar
		When The User enters "<password>" into password input bar
		When The User clicks on the Login Button
		Then The User is kept at the login page

	Examples: 
		| username | password  |
		| Lisan    | atreidies |
		| Paul     | al-gaib   |