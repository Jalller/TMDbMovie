# TMDbMovie

# TMDb Movie Service

## Overview
This is a **Spring Boot** application that fetches **popular movies** from **The Movie Database (TMDb) API** and stores them in a **database**.

## Features
✔ Fetch movies from TMDb API  
✔ Convert movie data (DTO to Entity)  
✔ Save valid movies to the database  
✔ Logs errors and skips incomplete data  
✔ Uses **Spring Boot, Hibernate, and JPA**

## Project Structure
- `MovieService.java` → Fetches movies and saves them to the database
- `Utils.java` → Converts DTOs to Entities
- `MovieRepository.java` → Handles database operations
- `Main.java` → Starts the application

## UML Diagrams (PlantUML)
This project includes various UML diagrams:
1. **Class Diagram** → Shows structure of DTOs, Entities, Services
2. **Sequence Diagram** → Shows API request flow from TMDb to DB
3. **Component Diagram** → Shows interaction between app components
4. **Activity Diagram** → Workflow from fetching to saving movies
5. **Use Case Diagram** → Illustrates user interactions
6. **Deployment Diagram** → Shows server setup and external dependencies

## How to Run
1. Clone the repository
2. Add your **TMDB API key** to `application.properties`
3. Run the project using:
   ```sh
   mvn spring-boot:run
