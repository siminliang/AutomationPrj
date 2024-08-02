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