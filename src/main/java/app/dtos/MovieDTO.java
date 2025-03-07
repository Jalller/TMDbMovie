package app.dtos;

import lombok.Data;

@Data
public class MovieDTO {
    private String title;
    private String overview;
    private String releaseDate;
    private Double voteAverage;
}
