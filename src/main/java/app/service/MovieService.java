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
    private final String tmdbApiKey;

    public MovieService(MovieRepository movieRepository, @Value("${TMDB_API_KEY}") String tmdbApiKey) {
        this.movieRepository = movieRepository;
        this.tmdbApiKey = tmdbApiKey;
    }

    public List<MovieDTO> fetchPopularMoviesFromTMDb() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + tmdbApiKey;
        RestTemplate restTemplate = new RestTemplate();
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);

        if (response != null) {
            for (MovieDTO movie : response.getResults()) {
                System.out.println("Fetched Movie: " + movie.getTitle());
                System.out.println("Overview: " + movie.getOverview());
                System.out.println("Release Date: " + movie.getReleaseDate());
                System.out.println("Genre: " + movie.getGenre());
                System.out.println("Poster Path: " + movie.getPosterPath());
                System.out.println("-------------------------------------");
            }
        }
        return response != null ? response.getResults() : List.of();
    }

    public void saveMovies(List<MovieDTO> movieDTOs) {
        List<Movie> movies = movieDTOs.stream()
                .map(Utils::convertToEntity)
                .collect(Collectors.toList());

        for (Movie movie : movies) {
            if (movie.getTitle() == null) {
                System.out.println("⚠️ Warning: Movie has no title!");
            }
            if (movie.getOverview() == null) {
                System.out.println("⚠️ Warning: Movie " + movie.getTitle() + " has no overview!");
            }
            if (movie.getReleaseDate() == null) {
                System.out.println("⚠️ Warning: Movie " + movie.getTitle() + " has no release date!");
            }

            movieRepository.save(movie);  // Save movie to DB
        }
    }
}
