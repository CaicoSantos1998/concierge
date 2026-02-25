# Concierge - Authorization Server
This project is the ecosystem's identity provider. It is responsible for authenticating users/clients and generating the JWT token for the API-Library(Resource Server), to validate requests. 

## Functionalities
* **OAuth2 Authentication Server:** Emission of token via client_credentials and password.
* **Clients management:** The registration of applications that can access the API.
* **Shared database:**  Integrated with the same database as of API-Library(My API) for user recognition.

## Environment configuration
 * **DB_URL:** URL of the database used. EX: ("jdbc:postgresql://localhost:2918/dt_lb").
 * **DB_USERNAME:** User of database.
 * **DB_PASSWORD:** Password of database.

## Integration with resource server(API-Library)
For the API-Library or any other API that currently works, you must point for your resource server.
* **Issuer URL:** Ex: ("http://localhost:2929").
* **JWT Subject:** The token's sub field must exist in the users table for that API-Library to allow saving the records.

## How to Test
1 - Start this project to test it.

2 - Generating the token in the Postman via POST/oauth2/token.

3 - Used the token generated in the Header(Authorization: Bearer "token") in your resource server requests.

## License
See file [LICENSE](LICENSE) to details.
