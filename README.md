# README #

This README documents the steps necessary to get this application up and running.

### OPEN WEATHER API  ###

* This is an open-source project for Carbon Flux Research
* Version 1.0

### Getting Started ###

* Download api_framework from CLI
* $> git clone https://github.com/rmanansa/carbon-flux-api.git
* $> cd carbon-flux-api
* Run docker-compose with .env file provided
* $> docker-compose up --build

### Using the Application
* Use Swagger UI to inspect Specification and test out each API endpoints.
* URL: http://localhost:8080/swagger-ui.html

### Using Advanced Setup of the Application
* Run DB separately from the app
* $> docker-compose up db &
* Running and Stopping Application 
* $> mvn spring-boot:run 
* press CTRL-Z to stop the web service
### Contribution guidelines ###

* Writing tests
* Code review -- Submit a PR in Bitbucket
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin: ramilmanansala@gmail.com
