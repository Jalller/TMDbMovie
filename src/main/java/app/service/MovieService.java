package app.service;

import app.dtos.MovieDTO;
import app.dtos.MovieResponse;
import app.entities.Movie;
import app.repository.MovieRepository;
import app.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

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

    // Fetch popular movies from TMDb API
    public List<MovieDTO> fetchPopularMoviesFromTMDb() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + tmdbApiKey;
        RestTemplate restTemplate = new RestTemplate();

        String rawJsonResponse = restTemplate.getForObject(url, String.class);
        System.out.println("Raw TMDb API Response: " + rawJsonResponse); // Debugging: Print full JSON response

        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);

        if (response != null) {
            System.out.println("Parsed MovieResponse: " + response); // Debugging: Check parsed response
        }

        return response != null ? response.getResults() : List.of();
    }
    public void saveMovies(List<MovieDTO> movieDTOs) {
        for (MovieDTO dto : movieDTOs) {
            saveMovie(dto);
        }
    }

    public MovieDTO getMovieById(Long id) {
        // Fetch movie by ID and return as DTO, otherwise throw 404 error
        return movieRepository.findById(id)
                .map(Utils::convertToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    }

    private void saveMovie(MovieDTO movieDTO) {
        Movie movie = Utils.convertToEntity(movieDTO);
        System.out.println("Saving movie to DB: " + movie); // Debugging: Check movie before saving

        movieRepository.save(movie);

        System.out.println("✅ Saved movie: " + movie.getTitle());
    }

    private Movie convertToEntity(MovieDTO dto) {
        return Movie.builder()
                .title(dto.getTitle())
                .overview(dto.getOverview())
                .releaseDate(dto.getReleaseDate() != null ? java.time.LocalDate.parse(dto.getReleaseDate()) : null)
                .posterPath(dto.getPosterPath())
                .voteAverage(dto.getVoteAverage())
                .genre(dto.getGenreIds() != null ? dto.getGenreIds().toString() : "Unknown") // Convert genre IDs to String
                .build();
    }


}
