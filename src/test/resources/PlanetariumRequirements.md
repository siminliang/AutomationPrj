# Planetarium Requirements Document
- **Epic**
  - Adding Planets
- **User Stories**
  - As a User I want to add new planets to the Planetarium, so I can track the planet in the night sky
- **Acceptance Criteria**
  - As a User I want to add new planets to the Planetarium, so I can track the planet in the night sky
    - Given: The User is on the Planetarium Homepage
    - Given: No planet with name "<PlanetName\>" in planetarium
    - When: The User selects planets from the drop-down menu
    - When: The User enters "<planet name\>" for planet name
    - When: The User selects an image from filesystem for planet image
    - When The User clicks on the submit button
    - Then: The planet should be added to the planetarium
    - data:
      - planet name
        - this planet name is 30 chars!!
        - this planet name is 31 chars!!!
        - Terra
        - nonunique


- **Epic**
  - Login
- **User Stories**
  - As a User I want to Login to my Account so that I can Enter the Planetarium
  - As the System I don't want a user to Login to an Account using an invalid Username and Password combo so that I can ensure my user accounts are secure
    Login Functionality Test Negative Scenario
  - As a User I want to Logout of my Account so that I can exit the Planetarium
    Login Functionality Test Positive Scenario
  - As the System I don't want a user to bypass the Login page so that I can ensure the Planetarium is only for logged in users
    Login Functionality Test Negative Scenario
- **Acceptance Criteria**
  - As a User I want to Login to my Account so that I can Enter the Planetarium
    - Given: The User is on the Login Page
    - Given: Account with username "<username>" and password "<password>" already registered
    - When: The User enters "<username>" into username input bar
    - When: The User enters "<password>" into password input bar
    - When: The User clicks on the Login Button
    - Then: The User is redirected to the Planetarium
    - data:
      - username
        - Batman
      - password
        - I am the night
  - As the System I don't want a user to Login to an Account using an invalid Username and Password combo so that I can ensure my user accounts are secure
    Login Functionality Test Negative Scenario
    - Given: The User is on the Login Page
    - Given: Account with username "Lisan" and password "al-gaib" already registered
    - When: The User enters "<username>" into username input bar
    - When: The User enters "<password>" into password input bar
    - When: The User clicks on the Login Button
    - Then: The User is kept at the login page
    - data:
      - username
        - Lisan
        - Paul
        - 
      - password
        - atreidies
        - al-gaib
        - 
  - As a User I want to Logout of my Account so that I can exit the Planetarium
    Login Functionality Test Positive Scenario
    - Given: The User is on the Login Page
    - Given: Account with username "<username>" and password "<password>" already registered
    - When: The User enters "<username>" into username input bar
    - When: The User enters "<password>" into password input bar
    - When: The User clicks on the Login Button
    - When: The User is redirected to the Planetarium
    - When: The User clicks on the Logout Button
    - Then: The User is redirected back to the Login page
    - data:
      - username
        - Batman
      - password
        - I am the night
  - As the System I don't want a user to bypass the Login page so that I can ensure the Planetarium is only for logged in users
    Login Functionality Test Negative Scenario
    - Given: The User is on the Login Page
    - When: The User enters the Planetarium Main Page URL into the browser URL
    - Then: The User is not redirected to the Planetarium



- **Epic**
  - Registration
- **User Stories**
  - As a User I want to Register an Account with the Planetarium so that I can Login Securely
    Registration Feature Testing Positive Scenario
  - As the System I don't want a user to Register an Account using invalid usernames and passwords so that I can ensure system requirements are met
    Registration Feature Testing Positive Scenario
- **Acceptance Criteria**
  - As a User I want to Register an Account with the Planetarium so that I can Login Securely
    Registration Feature Testing Positive Scenario
    - Given: The User is on the Login Page
    - Given: No Registered User with username "<username>"
    - When: The User clicks on Create an Account Link
    - When: The User enters "<username>" into registration username input bar
    - When: The User enters "<password>" into registration password input bar
    - When: The User clicks on the Create Button
    - Then: The User is registered and redirected into the Planetarium Login page
    - data:
      - username
        - Paul muad'dib atreides!!!!!!!!
      - password
        - Air power sea power desert30!!
  - As the System I don't want a user to Register an Account using invalid usernames and passwords so that I can ensure system requirements are met
    Registration Feature Testing Positive Scenario
    - Given The User is on the Login Page
    - Given Account with username "Lisan" and password "al-gaib" already registered
    - When The User clicks on Create an Account Link
    - When The User enters "<username>" into registration username input bar
    - When The User enters "<password>" into registration password input bar
    - When The User clicks on the Create Button
    - Then The User is kept at the Registration page
    - data:
      - username
        - Lisan
        - House Harkonnen owns Arrakis!!!
        - Paul muad'dib atreides!!!!!!!!
        - 
      - password
        - atreidies
        - Air power sea power desert30!!
        - The Lisan will liberate Arrakis
        - 

