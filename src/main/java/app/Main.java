package app;

import app.service.MovieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.domain.EntityScan;

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
        };
    }
}
