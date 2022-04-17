## Example to use sprint boot webflux with postgresql.

### Project description
The project has one Book entity. <br>
Standard structure with repository + service + controller. <br>
Used Flux and Mono for data processing.
And also unit test + integration tests.
### Technology
Java 17 + Spring boot 2.6.6 <br>
* Spring boot data r2dbc
* Spring boot webflux
* Postgresql
* Mapstruct
* Flyway
### How to run

1. Install docker-compose and run it with file [docker-compose.yml](docker-compose.yml). Use command `docker-compose up -d`.
2. Run java application: `mvn spring-boot:run`

