# Service-entertain-user

Description
This is a Java-based application that utilizes Spring-boot, Lombok, Java 17 and MapStruct. The main purpose of this application is to fetch random jokes from an external service, process the jokes, and provide an API for users to consume.

**Features**
Fetches jokes from an external service.
Filters and selects the most suitable joke based on predefined criteria.
Provides a RESTful API for users to get a random joke.


## Project Structure
JokeMapper class: Handles the mapping between Joke and ResponseJoke objects.
ExternalJoke and JokeOutput classes: These are Lombok data classes that represent the internal and external representations of a joke.
JokeService class: Fetches jokes from ExternalJokeRestClient, processes them and returns processed jokes.
JokeController class: Handles HTTP requests and responses.

## How to Use
This is a backend service designed to be used with an according front-end

Here's the main endpoint available:

GET /V1/entertain/getJoke
Use this endpoint to fetch a random joke from the external service.


## Setup

Before running the application, make sure that Java JDK 17 is installed.
To run the application, follow these steps:
Clone this repository.
Open the project in your favourite IDE on Intellij IDEA.
Build and run the application.




