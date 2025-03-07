package app.dtos;

import lombok.Data;

@Data
public class MovieDTO {
    private String title;
    private String overview;
    private String releaseDate;
    private Double voteAverage;
    private String genre;  // Add genre field
    private String posterPath;  // Add posterPath field

//    // Add a helper to pick the first genre or map them accordingly
//    public void setGenres(List<Genre> genres) {
//        if (!genres.isEmpty()) {
//            this.genre = genres.get(0).getName(); // Set the first genre name as the genre
//        }
//    }
}


