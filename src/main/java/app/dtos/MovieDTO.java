package app.dtos;

import app.entities.Genre;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class MovieDTO {
    private String title;
    private String overview;
    @Setter
    private String releaseDate;
    private Double voteAverage;
    private String posterPath;
    private String genre;  // Keep this for simplicity
    private List<Genre> genres;  // To capture all genres in the response

    // Automatically set the genre from the list of genres in case it's an array or list
    public void setGenres(List<Genre> genres) {
        if (genres != null && !genres.isEmpty()) {
            this.genre = genres.get(0).getName();  // We will take the first genre
        } else {
            this.genre = "Unknown";  // Default if no genre is found
        }
    }

}


