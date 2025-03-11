package app.controllers;

import app.dtos.MovieDTO;
import app.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/fetch")
    public List<MovieDTO> fetchMovies() {
        return movieService.fetchPopularMoviesFromTMDb();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping
    public ResponseEntity<MovieDTO> addMovie(@RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.addMovie(movieDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.updateMovie(id, movieDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean hardDelete) {
        if (hardDelete) {
            movieService.hardDeleteMovie(id);
            return ResponseEntity.ok("✅ Movie permanently deleted");
        } else {
            movieService.softDeleteMovie(id);
            return ResponseEntity.ok("✅ Movie soft deleted");
        }
    }
}
