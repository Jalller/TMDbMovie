package app.utils;

import app.dtos.MovieDTO;
import app.entities.Movie;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Utils {
    public static Movie convertToEntity(MovieDTO dto) {
        // Handle releaseDate parsing safely to avoid NullPointerException
        LocalDate releaseDate = null;
        if (dto.getReleaseDate() != null) {
            try {
                releaseDate = LocalDate.parse(dto.getReleaseDate());  // Parse releaseDate if it's not null
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format for movie: " + dto.getTitle());
            }
        }

        return Movie.builder()
                .title(dto.getTitle())
                .overview(dto.getOverview())
                .releaseDate(releaseDate)  // Set the releaseDate if valid, otherwise null
                .voteAverage(dto.getVoteAverage())  // Use voteAverage as before
                .build();
    }
}
