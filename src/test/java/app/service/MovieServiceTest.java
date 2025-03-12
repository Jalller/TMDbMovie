package app.service;

import app.dtos.MovieDTO;
import app.entities.Movie;
import app.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie testMovie;
    private MovieDTO testMovieDTO;

    @BeforeEach
    void setUp() {
        testMovie = new Movie(1L, "Inception", "A mind-bending thriller",
                LocalDate.parse("2010-07-16"), "Sci-Fi",
                "/poster.jpg", 8.8, false);

        testMovieDTO = new MovieDTO("Inception", "A mind-bending thriller",
                LocalDate.parse("2010-07-16"), "/poster.jpg",
                8.8, List.of(878), false);
    }

    @Test
    void testGetMovieById() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(testMovie));

        MovieDTO result = movieService.getMovieById(1L);

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
    }

    @Test
    void testAddMovie() {
        when(movieRepository.save(any(Movie.class))).thenReturn(testMovie);

        MovieDTO result = movieService.addMovie(testMovieDTO);

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
    }

    @Test
    void testUpdateMovie() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(testMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(testMovie);

        MovieDTO updatedMovieDTO = new MovieDTO("Inception", "Updated overview",
                LocalDate.parse("2010-07-16"), "/poster_updated.jpg",
                9.0, List.of(878), false);

        MovieDTO result = movieService.updateMovie(1L, updatedMovieDTO);

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
        assertEquals("Updated overview", result.getOverview());
        assertEquals(9.0, result.getVoteAverage());
    }

    @Test
    void testSoftDeleteMovie() {
        when(movieRepository.findById(1L)).thenReturn(Optional.of(testMovie));

        movieService.softDeleteMovie(1L);

        verify(movieRepository, times(1)).save(any(Movie.class));
        assertTrue(testMovie.isDeleted());
    }

    @Test
    void testHardDeleteMovie() {
        when(movieRepository.existsById(1L)).thenReturn(true);

        movieService.hardDeleteMovie(1L);

        verify(movieRepository, times(1)).deleteById(1L);
    }
}
