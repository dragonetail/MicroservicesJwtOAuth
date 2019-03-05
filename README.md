

Microservices - Java, Maven, Spring-boot, OAuth2 JWT Server (Springboot)


# Objective

Just create 3 microservices to demonstrate how works the communication between microservices spring-boot and Oauth2 server using JWT made in spring-boot too. 

\- In the OAuth server using JWT microservice, the user can make the login, logoff, validate+refreshing the token. In this microservice have one database (mysql) that is stored: userId, name, e-mail, password (create just a few examples to demonstrate that is working). Login returns the Auth token.

\- Microservice A has a database (mysql) that have stored: userId, address, zip code, city and country (create just a few examples to demonstrate that is working). 

\- Microservice B has a database (mysql) that have stored: userId, skill description (create just a few examples to demonstrate that is working), also receive a request from microservice A to get the skills of the users. 



# DB Test Scripts

```sql
DROP DATABASE IF EXISTS oauth;
CREATE DATABASE oauth DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

DROP DATABASE IF EXISTS service1;
CREATE DATABASE service1 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

DROP DATABASE IF EXISTS service2;
CREATE DATABASE service2 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

DROP USER IF EXISTS dbuser;
CREATE USER dbuser@'%' IDENTIFIED WITH mysql_native_password BY 'DBPassw0rd!';
GRANT ALL PRIVILEGES ON oauth.* TO 'dbuser'@'%';
GRANT ALL PRIVILEGES ON service1.* TO 'dbuser'@'%';
GRANT ALL PRIVILEGES ON service2.* TO 'dbuser'@'%';
FLUSH PRIVILEGES;
```



# Postman test scripts

```JSON
{
	"variables": [],
	"info": {
		"name": "Test",
		"_postman_id": "0455356c-e642-e3d7-2533-86cd82487578",
		"description": "",
		"schema": "https://schema.getpostman.com/json/collection/v2.0.0/collection.json"
	},
	"item": [
		{
			"name": "http://localhost:5000/auth/login",
			"request": {
				"url": "http://localhost:5000/auth/login",
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"description": ""
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n\t\"username\": \"admin\",\n\t\"password\": \"USPassw@rd!\"\n}"
				},
				"description": ""
			},
			"response": []
		},
		{
			"name": "http://localhost:5001/candidate/me",
			"request": {
				"url": "http://localhost:5001/candidate/me",
				"method": "GET",
				"header": [
					{
						"key": "Authorization",
						"value": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTUxNjk2ODQ5LCJleHAiOjE1NTE2OTg2NDl9.sPfOy32jJRNDTUtwuZ-ZDqHTm9MIazCZM15062-wXQFoAOt4Sey-EpRbYcztFNEZi0xEKQWzKa-03hNexeXHGg",
						"description": ""
					}
				],
				"body": {},
				"description": ""
			},
			"response": []
		},
		{
			"name": "http://localhost:5002/skills/candidate/me",
			"request": {
				"url": "http://localhost:5002/skills/candidate/me",
				"method": "GET",
				"header": [
					{
						"key": "Authorization",
						"value": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTUxNjk2ODQ5LCJleHAiOjE1NTE2OTg2NDl9.sPfOy32jJRNDTUtwuZ-ZDqHTm9MIazCZM15062-wXQFoAOt4Sey-EpRbYcztFNEZi0xEKQWzKa-03hNexeXHGg",
						"description": ""
					}
				],
				"body": {},
				"description": ""
			},
			"response": []
		},
		{
			"name": "http://localhost:5000/auth/checkUsernameAvailability?username=admin1",
			"request": {
				"url": {
					"raw": "http://localhost:5000/auth/checkUsernameAvailability?username=admin1",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "5000",
					"path": [
						"auth",
						"checkUsernameAvailability"
					],
					"query": [
						{
							"key": "username",
							"value": "admin1",
							"equals": true,
							"description": ""
						}
					],
					"variable": []
				},
				"method": "GET",
				"header": [],
				"body": {},
				"description": ""
			},
			"response": []
		},
		{
			"name": "http://localhost:5000/auth/signup",
			"request": {
				"url": "http://localhost:5000/auth/signup",
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"description": ""
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n\t\"username\": \"testuser\",\n\t\"password\": \"USPassw@rd!\"\n}"
				},
				"description": ""
			},
			"response": []
		},
		{
			"name": "http://localhost:5001/token/refresh",
			"request": {
				"url": "http://localhost:5000/token/refresh",
				"method": "POST",
				"header": [
					{
						"key": "Authorization",
						"value": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiaWF0IjoxNTUxNzAxMDg0LCJleHAiOjE1NTE3MDI4ODR9.g_9Hw7uUxKBwUsrDmUIHYZc8dsPwmodq9U9oiWRbt6UMn75Dkf5B3XHljZjD8dOt8x01ey2SllKf8dq4UcZXHg",
						"description": ""
					}
				],
				"body": {},
				"description": ""
			},
			"response": []
		}
	]
}
```






