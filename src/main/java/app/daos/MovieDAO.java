//package app.daos;
//
//import com.example.moviefetcher.model.Movie;
//import org.springframework.stereotype.Component;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class MovieDAO {
//
//    private final MovieRepository movieRepository;
//
//    public MovieDAO(MovieRepository movieRepository) {
//        this.movieRepository = movieRepository;
//    }
//
//    public List<Movie> findAllMovies() {
//        return movieRepository.findAll();
//    }
//
//    public Optional<Movie> findById(int id) {
//        return movieRepository.findById(id);
//    }
//
//    public void saveMovie(Movie movie) {
//        movieRepository.save(movie);
//    }
//
//    public void deleteMovie(int id) {
//        movieRepository.deleteById(id);
//    }
//}
