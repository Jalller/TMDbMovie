package app.controllers;

import app.dtos.MovieDTO;
import app.entities.Movie;
import app.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

    private final MovieService movieService;

    // Constructor injection for MovieService
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Endpoint to fetch popular movies from the database
    @GetMapping("/movies")
    public List<Movie> getPopularMovies() {
        return movieService.getPopularMovies();
    }

    // Endpoint to fetch a movie by its ID
    @GetMapping("/movies/{id}")
    public Optional<Movie> getMovieById(@PathVariable int id) {
        return movieService.getMovieById((long) id);
    }

    // Endpoint to fetch and save popular movies from TMDb
    @GetMapping("/movies/fetch")
    public List<MovieDTO> fetchAndSaveMovies() {
        return movieService.fetchPopularMoviesFromTMDb();
    }
}
