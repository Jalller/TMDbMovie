//package app.components;
//
//import app.entities.Movie;
//import app.service.MovieService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class MovieDataFetcher implements CommandLineRunner {
//
//    @Autowired
//    private MovieService movieService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("==== Fetching Movies from TMDb =====");
//
//        // Assuming movieService.fetchMoviesFromTMDb() is the method to fetch data from the TMDb API
//        List<Movie> movies = movieService.fetchMoviesFromTMDb();
//
//        System.out.println("Fetched " + movies.size() + " movies from TMDb:");
//
//        for (Movie movie : movies) {
//            // If release date is missing, set it to "Unknown"
//            if (movie.getReleaseDate() == null) {
//                movie.setReleaseDate("Unknown");
//                System.out.println("Warning: Missing release date for movie: " + movie.getTitle());
//            }
//            System.out.println("Title: " + movie.getTitle() + ", Release Date: " + movie.getReleaseDate());
//        }
//
//        System.out.println("===== Saving Movies to Database =====");
//        movieService.saveMovies(movies);
//    }
//}
