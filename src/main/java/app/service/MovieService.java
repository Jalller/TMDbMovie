package app.service;

import app.dtos.MovieDTO;
import app.dtos.MovieResponse;
import app.entities.Movie;
import app.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final String tmdbApiKey;

    public MovieService(MovieRepository movieRepository, @Value("${TMDB_API_KEY}") String tmdbApiKey) {
        this.movieRepository = movieRepository;
        this.tmdbApiKey = tmdbApiKey;
    }

    // Fetch popular movies from TMDb API
    public List<MovieDTO> fetchPopularMoviesFromTMDb() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + tmdbApiKey;
        RestTemplate restTemplate = new RestTemplate();
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);

        if (response != null) {
            // Log the full response to inspect the data structure
            System.out.println("TMDb Response: " + response); // Debugging: Check the response structure

            // Debugging individual movie fields
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

    // Save movies to the database
    public void saveMovies(List<MovieDTO> movieDTOs) {
        // Save the movies using the repository, Hibernate will take care of the mapping.
        movieDTOs.forEach(this::saveMovie);
    }

    // Let Hibernate handle entity creation and persistence
    private void saveMovie(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movieDTO.getTitle());
        movie.setOverview(movieDTO.getOverview());
        movie.setGenre(movieDTO.getGenre());
        movie.setPosterPath(movieDTO.getPosterPath());
        movie.setVoteAverage(movieDTO.getVoteAverage());

        // Parse the release date directly here, and let Hibernate handle the persistence
        if (movieDTO.getReleaseDate() != null && !movieDTO.getReleaseDate().isEmpty()) {
            try {
                movie.setReleaseDate(java.time.LocalDate.parse(movieDTO.getReleaseDate()));
            } catch (Exception e) {
                System.out.println("Error parsing release date for movie " + movieDTO.getTitle() + ": " + movieDTO.getReleaseDate());
            }
        }

        // Save the movie to the DB using the repository
        movieRepository.save(movie);
    }
}
