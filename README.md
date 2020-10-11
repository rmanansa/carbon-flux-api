# README #

This README documents the steps necessary to get this application up and running.

### PFAI API Framework ###

* This Project is a Platform. It gives you the foundation to start creating your API. When you run the Docker script, 
the application will create a Postgres instance in your localhost, create the database, tables, and functions as well 
insert sample data in the database. Subsequently, Docker builds and deploys the RESTful API to a Jetty web server. You
have a running RESTful API connected to a Postgres instance locally in no time. 
* To start developing your own API implementation, use UserEndpointController to add/edit endpoints. 
* Version 1.0

### Getting Started ###

* Download api_framework from CLI
* $> git clone git@bitbucket.org:noodleai/api_framework.git
* $> cd api_framework
* Run docker-compose with .env file provided
* $> docker-compose up --build

### Using the Application
* Use Homepage to test API functions
* URL: http://localhost:8080/
* Use Swagger UI to inspect Specification and test out each API endpoints.
* URL: http://localhost:8080/swagger-ui.html

### Using Advanced Setup of the Application
* Run DB separately from the app
* $> docker-compose up db &
* Running and Stopping Application 
* $> docker-compose up web 
* $> docker-compose down web 
* Optionally, press CTRL-Z to stop the web service
### Contribution guidelines ###

* Writing tests
* Code review -- Submit a PR in Bitbucket
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin: ramil.manansala@noodle.ai
