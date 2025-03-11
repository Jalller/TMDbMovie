package app;

import app.dtos.MovieDTO;
import app.entities.Movie;
import app.repository.MovieRepository;
import app.service.MovieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EntityScan(basePackages = "app.entities")
public class Main {

    private final MovieService movieService;
    private final MovieRepository movieRepository;

    public Main(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println("===== Fetching Movies from TMDb =====");

            List<MovieDTO> movieDTOs = movieService.fetchPopularMoviesFromTMDb();

            System.out.println("===== Saving Movies to Database =====");
            movieDTOs.forEach(movieDTO -> System.out.println("✅ Saving movie: " + movieDTO.getTitle()));

            movieService.saveMovies(movieDTOs);

            System.out.println("✅ Movies Fetched & Saved Successfully!");

            // Fetch the first saved movie from the database
            Optional<Movie> firstSavedMovie = movieRepository.findAll().stream().findFirst();

            if (firstSavedMovie.isPresent()) {
                Movie movie = firstSavedMovie.get();
                System.out.println("===== Updating Movie: " + movie.getTitle() + " (ID: " + movie.getId() + ") =====");

                MovieDTO updatedMovie = new MovieDTO(
                        "Updated " + movie.getTitle(),
                        movie.getOverview(),
                        movie.getReleaseDate().toString(),
                        movie.getPosterPath(),
                        movie.getVoteAverage(),
                        List.of() // Assuming genres are stored differently
                );

                MovieDTO result = movieService.updateMovie(movie.getId(), updatedMovie);
                System.out.println("✅ Updated movie: " + result.getTitle());
            } else {
                System.out.println("⚠️ No movies found in database. Update skipped.");
            }
        };
    }
}
