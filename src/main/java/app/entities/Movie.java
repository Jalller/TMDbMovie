package app.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "movies")
public class Movie {
    @Id
    private int id;

    private String title;
    private String overview;
    private String releaseDate;
    private double voteAverage;
    private String posterPath;
}
