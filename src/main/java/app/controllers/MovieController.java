package app.controllers;

import app.dtos.MovieDTO;
import app.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Endpoint to fetch popular movies
    @GetMapping("/fetch")
    public List<MovieDTO> fetchMovies() {
        return movieService.fetchPopularMoviesFromTMDb();
    }

    @GetMapping("/api/movie/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movieService.getMovieById(id));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }
}
