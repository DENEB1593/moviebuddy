package moviebuddy.data;

import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieReader;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CachingMovieReaderTest {

    @Test
    void caching() {
        CacheManager cacheManager = new ConcurrentMapCacheManager();
        MovieReader target = new DummyMovieReader();

        CachingMovieReader movieReader = new CachingMovieReader(cacheManager, target);

        assertNull(movieReader.getCachedData());

        List<Movie> movies = movieReader.loadMovies();
        assertNotNull(movieReader.getCachedData());
        assertSame(movieReader.loadMovies(), movies);
    }

    class DummyMovieReader implements MovieReader {

        @Override
        public List<Movie> loadMovies() {
            return new ArrayList<>();
        }
    }
}