# Spring Security App

This is a sample Spring Boot application that uses spring security

## Getting Started

### Prerequisites

Before you can run this application, you'll need to have the following installed on your machine:

- [Java 11](https://www.oracle.com/co/java/technologies/javase/jdk11-archive-downloads.html)
- [Gradle](https://gradle.org/install/)
- [Docker](https://docs.docker.com/get-docker/)

### Installation

1. Clone this repository to your local machine using `git clone https://github.com/your-username/my-spring-boot-app.git`
2. Navigate to the project directory using `cd my-spring-boot-app`
3. Run the application using Gradle: `./gradlew bootRun`
4. if you want to run the project locally without using docker, you have to modify the application.yml with your database credentials
5. Alternatively, you can run the project using the provided `docker-compose.yml` for this you should follow next steps:
   - build the project: `./gradlew build`
   - build images: `docker-compose build`
   - run the app: `docker-compose up -d`
### Usage

Once the application is running, you can access the API by sending HTTP requests to `http://localhost:8081/api`. The following endpoints are available:

- `POST /api/v1/authenticate/register`
- `POST /api/v1/authenticate/authenticate`

### Configuration

The application can be configured by editing the `application.yml` file, which is located in the `src/main/resources` directory. You can modify the database connection settings, server port, and other configuration options as needed.
if you desire to change the running port using docker-compose you have to modify it in [docker-compose.yml](docker-compose.yml)


