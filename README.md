# Test project for storing users and their locations
### Main stack of technologies:
* Spring-Boot (mvc, jpa, validation, test)
* Lombok
* Liquibase
* Testcontainers
* Mapstruct
* PostgresSql
* Maven

### Main features
* New location will be created on every request in case if user exist.
If user does not exist endpoint will return 404 http status code. Besides
endpoint can return 400 status in case if input dto is not valid
(userId or/and createOn or/and any of coordinates absent).
* User entity has optimistic locking to avoid "lost update" situation. 
In case of optimistic locking exception endpoint will return 422 http status.
Besides endpoint can return 400 status in case if input dto is not valid
(userId absents).
* Endpoints that return user with last location or user with locations in time range
also can return 404 http status code in case if user does not exist. Besides 
all input parameters are required. If any of them is missed endpoint will return 400 status.

### Additional information
Database instance is needed ror running. You can start docker container 
with postgresql by this command.
```
docker run --name postgresDB -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=postgresadmin -e POSTGRES_DB=user_locations -d postgres
```
