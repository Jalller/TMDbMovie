package app.utils;

import app.dtos.MovieDTO;
import app.entities.Movie;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static Movie convertToEntity(MovieDTO dto) {
        System.out.println("Converting MovieDTO: " + dto);

        LocalDate releaseDate = parseDate(String.valueOf(dto.getReleaseDate()));

        return Movie.builder()
                .title(dto.getTitle() != null ? dto.getTitle() : "Untitled")
                .overview(dto.getOverview() != null ? dto.getOverview() : "No overview available")
                .releaseDate(releaseDate)
                .genre(dto.getGenreIds() != null ? convertGenreIdsToString(dto.getGenreIds()) : "Unknown")
                .posterPath(dto.getPosterPath() != null ? "https://image.tmdb.org/t/p/w500" + dto.getPosterPath() : "No image available")
                .voteAverage(dto.getVoteAverage() != null ? dto.getVoteAverage() : 0.0)
                .isDeleted(false)
                .build();
    }

    public static MovieDTO convertToDTO(Movie movie) {
        return MovieDTO.builder()
                .title(movie.getTitle())
                .overview(movie.getOverview())
                .releaseDate(movie.getReleaseDate() != null ? LocalDate.parse(movie.getReleaseDate().toString()) : null)
                .posterPath(movie.getPosterPath())
                .voteAverage(movie.getVoteAverage())
                .genreIds(convertGenreStringToList(movie.getGenre()))
                .isDeleted(movie.isDeleted())
                .build();
    }

    private static LocalDate parseDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            System.out.println("⚠️ Invalid date format: " + dateString);
            return null;
        }
    }

    private static String convertGenreIdsToString(List<Integer> genreIds) {
        return genreIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    private static List<Integer> convertGenreStringToList(String genre) {
        if (genre == null || genre.trim().isEmpty() || genre.equals("[]")) {
            return List.of();
        }
        return Arrays.stream(genre.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
