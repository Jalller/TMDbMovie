package app;

import app.service.MovieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
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
            System.out.println("===== Application Started =====");

            movieService.fetchPopularMoviesFromTMDb().forEach(movieDTO -> {
                System.out.println("Title: " + movieDTO.getTitle() + ", Release Date: " + movieDTO.getReleaseDate());
            });

            System.out.println("===== Application Finished =====");
        };
    }
}
