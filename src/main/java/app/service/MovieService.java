package app.service;

import app.dtos.MovieDTO;
import app.dtos.MovieResponse;
import app.entities.Movie;
import app.repository.MovieRepository;
import app.mappers.MovieMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final String tmdbApiKey;

    public MovieService(MovieRepository movieRepository, @Value("${TMDB_API_KEY}") String tmdbApiKey) {
        this.movieRepository = movieRepository;
        this.tmdbApiKey = tmdbApiKey;
    }

    // ✅ Fetch popular movies from TMDb API
    public List<MovieDTO> fetchPopularMoviesFromTMDb() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + tmdbApiKey;
        RestTemplate restTemplate = new RestTemplate();

        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);

        return response != null ? response.getResults() : List.of();
    }

    // ✅ Save multiple movies
    public void saveMovies(List<MovieDTO> movieDTOs) {
        for (MovieDTO dto : movieDTOs) {
            saveMovie(dto);
        }
    }

    // ✅ Save a single movie
    private void saveMovie(MovieDTO movieDTO) {
        Movie movie = MovieMapper.convertToEntity(movieDTO);
        movieRepository.save(movie);
        log.info("✅ Saved movie: {}", movie.getTitle());
    }

    // ✅ Get movie by ID
    public MovieDTO getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(MovieMapper::convertToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    }

    // ✅ Add new movie (Fix for missing method)
    public MovieDTO addMovie(MovieDTO movieDTO) {
        Movie movie = MovieMapper.convertToEntity(movieDTO);
        Movie savedMovie = movieRepository.save(movie);
        log.info("✅ Added new movie: {}", savedMovie.getTitle());
        return MovieMapper.convertToDTO(savedMovie);
    }

    // ✅ Update existing movie
    public MovieDTO updateMovie(Long id, MovieDTO movieDTO) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

        existingMovie.setTitle(movieDTO.getTitle());
        existingMovie.setOverview(movieDTO.getOverview());
        existingMovie.setReleaseDate(movieDTO.getReleaseDate());
        existingMovie.setPosterPath(movieDTO.getPosterPath());
        existingMovie.setVoteAverage(movieDTO.getVoteAverage());
        existingMovie.setGenre(movieDTO.getGenreIds() != null ? movieDTO.getGenreIds().toString() : "Unknown");

        Movie updatedMovie = movieRepository.save(existingMovie);
        log.info("✅ Updated movie: {}", updatedMovie.getTitle());

        return MovieMapper.convertToDTO(updatedMovie);
    }

    // ✅ Soft Delete (Marks movie as deleted)
    @Transactional
    public void softDeleteMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

        movie.setDeleted(true);
        movieRepository.save(movie);
        log.info("✅ Soft deleted movie: {}", movie.getTitle());
    }

    // ✅ Hard Delete (Permanently removes movie)
    @Transactional
    public void hardDeleteMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }

        movieRepository.deleteById(movieId);
        log.info("✅ Hard deleted movie with ID: {}", movieId);
    }
}
