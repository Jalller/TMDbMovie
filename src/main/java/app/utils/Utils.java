package app.utils;

import app.dtos.MovieDTO;
import app.entities.Movie;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Utils {
    public static Movie convertToEntity(MovieDTO dto) {
        LocalDate releaseDate = null;

        if (dto.getReleaseDate() != null && !dto.getReleaseDate().isEmpty()) {
            try {
                releaseDate = LocalDate.parse(dto.getReleaseDate());
            } catch (DateTimeParseException e) {
                System.out.println("⚠️ Invalid date format for movie: " + dto.getTitle());
            }
        }

        return Movie.builder()
                .title(dto.getTitle() != null ? dto.getTitle() : "Untitled") // Default if null
                .overview(dto.getOverview() != null ? dto.getOverview() : "No overview available") // Default if null
                .releaseDate(releaseDate)
                .genre(dto.getGenre() != null ? dto.getGenre() : "Unknown") // Handle missing genre
                .posterPath(dto.getPosterPath() != null ? dto.getPosterPath() : "No image available") // Default if null
                .voteAverage(dto.getVoteAverage() != null ? dto.getVoteAverage() : 0.0) // Default voteAverage
                .build();
    }
}
