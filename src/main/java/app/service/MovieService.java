package app.service;

import app.dtos.MovieDTO;
import app.dtos.MovieResponse;
import app.entities.Movie;
import app.repository.MovieRepository;
import app.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate;

    @Value("${tmdb.api.key}")
    private String apiKey;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.restTemplate = new RestTemplate();
    }

    public List<MovieDTO> fetchPopularMoviesFromTMDb() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);

        if (response != null) {
            System.out.println("Fetched " + response.getResults().size() + " movies from TMDb:");
            response.getResults().forEach(movie -> {
                if (movie.getReleaseDate() == null) {
                    System.out.println("Warning: Missing release date for movie: " + movie.getTitle());
                }
                System.out.println("Title: " + movie.getTitle() + ", Release Date: " + movie.getReleaseDate());
            });
        }

        return response != null ? response.getResults() : List.of();
    }

    public void saveMovies(List<MovieDTO> movieDTOs) {
        List<Movie> movies = movieDTOs.stream()
                .map(Utils::convertToEntity)
                .collect(Collectors.toList());

        movieRepository.saveAll(movies);
        System.out.println("✅ Saved " + movies.size() + " movies to the database.");
    }
}
