package app.utils;

import app.dtos.MovieDTO;
import app.entities.Movie;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Utils {
    public static Movie convertToEntity(MovieDTO dto) {
        LocalDate releaseDate = null;
        if (dto.getReleaseDate() != null) {
            try {
                releaseDate = LocalDate.parse(dto.getReleaseDate());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format for movie: " + dto.getTitle());
            }
        }

        return Movie.builder()
                .title(dto.getTitle())
                .overview(dto.getOverview())
                .releaseDate(releaseDate)
                .genre(dto.getGenre())  // Handle genre
                .posterPath(dto.getPosterPath())  // Handle posterPath
                .voteAverage(dto.getVoteAverage())
                .build();
    }
}
