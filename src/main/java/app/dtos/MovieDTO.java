package app.dtos;

import app.entities.Genre;
import lombok.Data;
import java.util.List;

@Data
public class MovieDTO {
    private String title;
    private String overview;
    private String releaseDate;
    private Double voteAverage;
    private String posterPath;
    private String genre;

    private List<Genre> genres; // Store the full list of genres

    // Automatically set the genre from the list
    public void setGenres(List<Genre> genres) {
        if (genres != null && !genres.isEmpty()) {
            this.genre = genres.get(0).getName(); // Set the first genre as default
        } else {
            this.genre = "Unknown"; // Handle missing genre
        }
    }
}

//    // Add a helper to pick the first genre or map them accordingly
//    public void setGenres(List<Genre> genres) {
//        if (!genres.isEmpty()) {
//            this.genre = genres.get(0).getName(); // Set the first genre name as the genre
//        }
//    }



