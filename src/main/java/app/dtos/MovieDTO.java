package app.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
    private int id;
    private String title;
    private LocalDate releaseDate;
    private String genre;
    private double voteAverage;
    private String posterPath;
    private String overview;
}
