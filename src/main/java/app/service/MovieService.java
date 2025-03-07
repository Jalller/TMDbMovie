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

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDTO> fetchPopularMoviesFromTMDb() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + tmdbApiKey;
        RestTemplate restTemplate = new RestTemplate();
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);

        // Log the fetched response for debugging
        if (response != null) {
            response.getResults().forEach(movie -> {
                System.out.println("Fetched movie: " + movie.getTitle() + ", Release Date: " + movie.getReleaseDate());
                System.out.println("Genre: " + movie.getGenre());
                System.out.println("Poster Path: " + movie.getPosterPath());
            });
        }
        return response != null ? response.getResults() : List.of();
    }

    public void saveMovies(List<MovieDTO> movieDTOs) {
        List<Movie> movies = movieDTOs.stream()
                .map(Utils::convertToEntity)
                .collect(Collectors.toList());
        for (Movie movie : movies) {
            if (movie.getTitle() != null && movie.getTitle().length() > 255) {
                movie.setTitle(movie.getTitle().substring(0, 255)); // Truncate if too long
            }
            if (movie.getOverview() != null && movie.getOverview().length() > 255) {
                movie.setOverview(movie.getOverview().substring(0, 255)); // Truncate if too long
            }
            movieRepository.save(movie);  // Save movie to DB
        }
    }
}
