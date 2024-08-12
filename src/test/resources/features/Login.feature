@Login
Feature: Login


	Scenario Outline: As a User I want to Login to my Account so that I can Enter the Planetarium
	Login Functionality Test Positive Scenario
		Given The User is on the Login Page
		Given Account with username "<username>" and password "<password>" already registered
		When The User enters "<username>" into username input bar
		When The User enters "<password>" into password input bar
		When The User clicks on the Login Button
		Then The User is redirected to the Planetarium

	Examples: 
		| username | password       |
		| Batman   | I am the night |


	Scenario Outline: As the System I don't want a user to Login to an Account using an invalid Username and Password combo so that I can ensure my user accounts are secure
	Login Functionality Test Negative Scenario
		Given The User is on the Login Page
		Given Account with username "Lisan" and password "al-gaib" already registered
		When The User enters "<username>" into username input bar
		When The User enters "<password>" into password input bar
		When The User clicks on the Login Button
		Then The User is kept at the login page

	Examples: 
		| username | password  |
		| Lisan    | atreidies |
		| Paul     | al-gaib   |
		|          |           |
		| Lisan    |           |
		|          | al-gaib   |

	Scenario Outline: As a User I want to Logout of my Account so that I can exit the Planetarium
	Login Functionality Test Positive Scenario
		Given The User is on the Login Page
		Given Account with username "<username>" and password "<password>" already registered
		When The User enters "<username>" into username input bar
		When The User enters "<password>" into password input bar
		When The User clicks on the Login Button
		When The User is redirected to the Planetarium
		When The User clicks on the Logout Button
		Then The User is redirected back to the Login page

	Examples: 
		| username | password       |
		| Batman   | I am the night |


	Scenario: As the System I don't want a user to bypass the Login page so that I can ensure the Planetarium is only for logged in users
	Login Functionality Test Negative Scenario
		Given The User is on the Login Page
		When The User enters the Planetarium Main Page URL into the browser URL
		Then The User is not redirected to the Planetarium