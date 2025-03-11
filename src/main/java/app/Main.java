package app;

import app.service.MovieService;
import app.dtos.MovieDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;

@SpringBootApplication
@EntityScan(basePackages = "app.entities")
public class Main {

    private final MovieService movieService;

    public Main(MovieService movieService) {
        this.movieService = movieService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println("===== Fetching Movies from TMDb =====");

            var movieDTOs = movieService.fetchPopularMoviesFromTMDb();

            System.out.println("===== Saving Movies to Database =====");
            movieService.saveMovies(movieDTOs);

            System.out.println("✅ Movies Fetched & Saved Successfully!");

            // Test manual movie addition
            MovieDTO testMovie = new MovieDTO();
            testMovie.setTitle("Interstellar");
            testMovie.setOverview("A team of explorers travel through a wormhole in space...");
            testMovie.setReleaseDate(LocalDate.parse("2014-11-07"));
            testMovie.setPosterPath("/interstellar.jpg");
            testMovie.setVoteAverage(8.6);
            testMovie.setGenreIds(java.util.List.of(12, 18, 878));

            MovieDTO savedMovie = movieService.addMovie(testMovie);
            System.out.println("✅ Manually added movie: " + savedMovie.getTitle());
        };
    }
}
