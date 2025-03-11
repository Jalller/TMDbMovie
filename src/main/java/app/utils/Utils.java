package app.utils;

import app.dtos.MovieDTO;
import app.entities.Movie;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    // Convert MovieDTO to Movie Entity
    public static Movie convertToEntity(MovieDTO dto) {
        System.out.println("Converting MovieDTO: " + dto);

        // Since releaseDate in MovieDTO is already a LocalDate, we use it directly.
        LocalDate releaseDate = dto.getReleaseDate();

        Movie movie = Movie.builder()
                .title(dto.getTitle() != null ? dto.getTitle() : "Untitled")
                .overview(dto.getOverview() != null ? dto.getOverview() : "No overview available")
                .releaseDate(releaseDate)
                .genre(dto.getGenreIds() != null ? convertGenreIdsToString(dto.getGenreIds()) : "Unknown")
                .posterPath(dto.getPosterPath() != null ? "https://image.tmdb.org/t/p/w500" + dto.getPosterPath() : "No image available")
                .voteAverage(dto.getVoteAverage() != null ? dto.getVoteAverage() : 0.0)
                .build();

        System.out.println("Converted Movie: " + movie);
        return movie;
    }

    // Convert Movie Entity to MovieDTO
    public static MovieDTO convertToDTO(Movie movie) {
        return MovieDTO.builder()
                .title(movie.getTitle())
                .overview(movie.getOverview())
                .releaseDate(String.valueOf(movie.getReleaseDate())) // ✅ Fixed: Using LocalDate directly
                .posterPath(movie.getPosterPath())
                .voteAverage(movie.getVoteAverage())
                .genreIds(convertGenreStringToList(movie.getGenre()))
                .build();
    }

    private static String convertGenreIdsToString(List<Integer> genreIds) {
        return genreIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    private static List<Integer> convertGenreStringToList(String genre) {
        if (genre == null || genre.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(genre.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
