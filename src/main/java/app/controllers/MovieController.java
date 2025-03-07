package app.controllers;

import app.dtos.MovieDTO;
import app.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/fetch")
    public List<MovieDTO> fetchMovies() {
        return movieService.fetchPopularMoviesFromTMDb();
    }
}
