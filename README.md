# Dungeons and Dragons Role Playing service

## Project Description

Developed a role playing service based on the board game Dungeons and Dragons.

## Technologies Used

* Spring Boot
* Spring Data
* Maven
* Amazon RDS
* Kubernetes
* Docker
* Maven
* FluentD
* Grafana
* Loki
* Log4j
* PostgreSQL
* Git

## Features

* Administrator features are accessible through HTTP requests using a RESTful API
* Create new user
* Login using form-data and logout
* Keeps track of users currently logged in using sessions
* Removes old session of a user if same user is logging in from a different area
* Friend system to see friends who are online

To-do list:

* Fix error of not saving when changes when logging out
* Implement a web socket for communication
* Implement game logic
* Convert any transfer of form-data to json

## Getting Started

Begin by cloning the repository
```git clone https://github.com/210201sre/DnD.git```

Run `DungeonsAndDragons.sql` to have a working database (recommended to use postgreSQL, or change the DDL for the query language you prefer)

To run the application you will need to set up a Kubernetes cluster. To do this install Docker and setup the default Kubernetes cluster. Also install Helm to setup Grafana, Loki and nginx ingress

Convert the application into an image using:
```./mvnw spring-boot:build-image```

Change the tag of the image that was built to a repository you control on Docker Hub
```Docker tag {image built} {username/repository}```

Push the tagged image onto your repository
```Docker push {username/repository}```

On the Kubernetes cluster apply the manifests and create a secret with the username, password and url to your database.

Once everything has been setup you can enter the application for a web browser at the url "http://localhost/dungeons-and-dragons"