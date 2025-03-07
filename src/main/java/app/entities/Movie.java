package app.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "overview")
    private String overview;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "genre")
    private String genre;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "vote_average")
    private Double voteAverage;  // Corrected field name

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    // Builder pattern (manual way)
    public static class MovieBuilder {
        private String title;
        private String overview;
        private LocalDate releaseDate;
        private String genre;
        private String posterPath;
        private Double voteAverage;

        public MovieBuilder title(String title) {
            this.title = title;
            return this;
        }

        public MovieBuilder overview(String overview) {
            this.overview = overview;
            return this;
        }

        public MovieBuilder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public MovieBuilder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public MovieBuilder posterPath(String posterPath) {
            this.posterPath = posterPath;
            return this;
        }

        public MovieBuilder voteAverage(Double voteAverage) {
            this.voteAverage = voteAverage;
            return this;
        }

        public Movie build() {
            Movie movie = new Movie();
            movie.setTitle(this.title);
            movie.setOverview(this.overview);
            movie.setReleaseDate(this.releaseDate);
            movie.setGenre(this.genre);
            movie.setPosterPath(this.posterPath);
            movie.setVoteAverage(this.voteAverage);
            return movie;
        }
    }

    // Static method to create a builder instance
    public static MovieBuilder builder() {
        return new MovieBuilder();
    }
}
