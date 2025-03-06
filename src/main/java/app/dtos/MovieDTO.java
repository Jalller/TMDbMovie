package app.dtos;

import lombok.Data;

@Data
public class MovieDTO {
    private int id;
    private String title;
    private String overview;
    private String releaseDate;
    private double voteAverage;
    private String posterPath;
}
