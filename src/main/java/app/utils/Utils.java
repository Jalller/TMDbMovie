package app.utils;

import app.dtos.MovieDTO;
import app.entities.Movie;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static Movie convertToEntity(MovieDTO dto) {
        System.out.println("Converting MovieDTO: " + dto); // Debugging: Print DTO before converting

        LocalDate releaseDate = null;

        if (dto.getReleaseDate() != null && !dto.getReleaseDate().isEmpty()) {
            try {
                releaseDate = LocalDate.parse(dto.getReleaseDate());
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Invalid date format for movie: " + dto.getTitle() + " | Date: " + dto.getReleaseDate());
            }
        }

        Movie movie = Movie.builder()
                .title(dto.getTitle() != null ? dto.getTitle() : "Untitled")
                .overview(dto.getOverview() != null ? dto.getOverview() : "No overview available")
                .releaseDate(releaseDate)
                .genre(dto.getGenreIds() != null ? convertGenreIdsToString(dto.getGenreIds()) : "Unknown")
                .posterPath(dto.getPosterPath() != null ? "https://image.tmdb.org/t/p/w500" + dto.getPosterPath() : "No image available")
                .voteAverage(dto.getVoteAverage() != null ? dto.getVoteAverage() : 0.0)
                .build();

        System.out.println("Converted Movie: " + movie); // Debugging: Check final Movie entity

        return movie;
    }

    private static String convertGenreIdsToString(List<Integer> genreIds) {
        return genreIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
