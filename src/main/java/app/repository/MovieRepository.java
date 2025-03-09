package app.repository;

import app.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}

//The JPA part should be implemented using Entities and DTOs. -
// The DTOs should be used to convert the movie json from the TMDb API with Jackson.
// - Also, the DTO’s should be used as return types and arguments for the DAO methods.
// This means that the DTOs should be used to transfer data between the service layer / Main / tests methods,
// and the DAO layer.

//@Repository
//public interface MovieRepository extends JpaRepository<Movie, Long> {
//    // No need to write SQL queries! JpaRepository provides methods like:
//    // - save(Movie movie)
//    // - findById(Long id)
//    // - findAll()
//    // - deleteById(Long id)
//}
//How Data Flows Without an Explicit DAO:
//MovieService calls MovieRepository (DAO equivalent).
//MovieRepository uses Spring JPA to communicate with the database.
//DTOs are used to transfer data between layers.