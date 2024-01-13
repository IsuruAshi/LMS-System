# Lecture Management System (Spring Boot)

## Version
0.1.0

## License
Copyright & copy ;  2024 Isuru Ashinsana. All Rights Reserved. <br>
This project is licensed under the [MIT License](LICENSE.txt)

## Overview

This Spring Boot application serves as a backend API for a Lecture Management System, providing features for managing lectures, courses, and related resources.

## Getting Started

Ensure you have the following installed:

- [Java Development Kit (JDK)](https://adoptopenjdk.net/) - version 8 or later
- [Maven](https://maven.apache.org/)



## Project Structure

The project follows a standard layered architecture:

- **`src/main/java/com/example/lecturemanagement`**
    - **`controller`**: Contains REST controllers for lecture and course management.
    - **`service`**: Contains business logic for managing lectures and courses.
    - **`repository`**: Data access layer with Spring Data JPA repositories.
    - **`entity`**: Data model entities (e.g., Lecture, Course).
    - **`exception`**: Custom exception handling.
    - **`Application.java`**: Main class to run the Spring Boot application.
  

- **`src/main/resources`**
    - **`application.properties`**: Configuration properties.


Access the API at `http://localhost:8080/api/v1/lecturers`.




