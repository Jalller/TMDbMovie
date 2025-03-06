package app.service;

import app.dtos.MovieDTO;
import app.entities.Movie;
import app.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    @Value("${TMDB_API_KEY}")
    private String tmdbApiKey;

    private final MovieRepository movieRepository;

    public List<MovieDTO> fetchPopularMoviesFromTMDb() {
        List<MovieDTO> allMovies = new ArrayList<>();
        Set<Integer> fetchedMovieIds = new HashSet<>();
        int page = 1;
        boolean hasMorePages = true;

        while (hasMorePages) {
            String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + tmdbApiKey + "&language=en-US&page=" + page;
            RestTemplate restTemplate = new RestTemplate();

            try {
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
                String responseBody = response.getBody();
                List<MovieDTO> currentPageMovies = mapResponseToDTO(responseBody);

                if (currentPageMovies.isEmpty()) {
                    hasMorePages = false;
                } else {
                    for (MovieDTO movie : currentPageMovies) {
                        if (!fetchedMovieIds.contains(movie.getId())) {
                            allMovies.add(movie);
                            fetchedMovieIds.add(movie.getId());
                        }
                    }
                }
                page++;
            } catch (HttpClientErrorException e) {
                e.printStackTrace();
                break;
            }
        }

        saveMoviesToDatabase(allMovies);
        return allMovies;
    }

    private void saveMoviesToDatabase(List<MovieDTO> movieDTOs) {
        List<Movie> movies = movieDTOs.stream()
                .map(this::convertDTOToEntity)
                .collect(Collectors.toList());
        movieRepository.saveAll(movies);
    }

    private List<MovieDTO> mapResponseToDTO(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode resultsNode = rootNode.path("results");

            List<MovieDTO> movies = new ArrayList<>();
            for (JsonNode movieNode : resultsNode) {
                MovieDTO movieDTO = MovieDTO.builder()
                        .id(movieNode.path("id").asInt())
                        .title(movieNode.path("title").asText())
                        .releaseDate(LocalDate.parse(movieNode.path("release_date").asText()))
                        .genre(movieNode.path("genre_ids").toString()) // Adjust if needed
                        .voteAverage(movieNode.path("vote_average").asDouble())
                        .posterPath(movieNode.path("poster_path").asText())
                        .overview(movieNode.path("overview").asText())
                        .build();
                movies.add(movieDTO);
            }

            return movies;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Movie convertDTOToEntity(MovieDTO movieDTO) {
        return Movie.builder()
                .title(movieDTO.getTitle())
                .releaseDate(movieDTO.getReleaseDate())
                .overview(movieDTO.getOverview())
                .voteAverage(movieDTO.getVoteAverage())
                .posterPath(movieDTO.getPosterPath())
                .genre(movieDTO.getGenre())
                .build();
    }
    // Fetch movies from the database
    public List<Movie> getPopularMovies() {
        return movieRepository.findAll();
    }

    // Fetch a movie by ID from the database
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

}
