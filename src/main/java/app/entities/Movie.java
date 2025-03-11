package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "movie")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "overview", length = 1000)
    private String overview;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "genre", length = 255)
    private String genre;

    @Column(name = "poster_path", length = 1000) // ✅ Explicitly map to "poster_path"
    private String posterPath;

    @Column(name = "vote_average")
    private Double voteAverage;

    @Column(name = "is_deleted", nullable = false) // ✅ Explicitly map to "is_deleted"
    private boolean isDeleted = false;
}
