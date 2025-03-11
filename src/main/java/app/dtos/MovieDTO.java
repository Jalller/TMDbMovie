package app.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
    private String title;
    private String overview;

    @JsonProperty("release_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;

    private Boolean isDeleted; // Added isDeleted field

    // Custom constructor to handle LocalDate conversion
    public MovieDTO(String title, String overview, String releaseDate, String posterPath, Double voteAverage, List<Integer> genreIds, Boolean isDeleted) {
        this.title = title;
        this.overview = overview;
        this.releaseDate = (releaseDate != null) ? LocalDate.parse(releaseDate) : null;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.genreIds = genreIds;
        this.isDeleted = (isDeleted != null) ? isDeleted : false; // Default to false if not provided
    }
}
