package moviebuddy.data;

import moviebuddy.MovieBuddyFactory;
import moviebuddy.MovieBuddyProfile;
import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.AopTestUtils;
import org.springframework.util.Assert;

import java.util.List;

@ActiveProfiles(MovieBuddyProfile.XML_MODE)
@SpringJUnitConfig(MovieBuddyFactory.class)
@TestPropertySource(properties = "movie.metadata=movie_metadata.xml")
public class XmlMovieReaderTest {

    @Autowired
    MovieReader movieReader;

    //Bean named 'cachingMovieReaderFactory' is expected to be of type 'moviebuddy.data.XmlMovieReader'
    //but was actually of type 'com.sun.proxy.$Proxy41'
    // XmlMovieReader 타입을 요청받기로 하였으나 제공해주는 Bean의 타입이 Proxy라 빈 타입 오류 발생

    @Test
    void NotEmpty_LoadedMovies() {
        List<Movie> movies = movieReader.loadMovies();

        Assertions.assertEquals(1375, movies.size());
    }

    @Test
    void check_Type() {
        // 프록시 객체 여부 검증
        Assertions.assertTrue(AopUtils.isAopProxy(movieReader));

        // 같은 클래스인지 검증
        MovieReader targetObject = AopTestUtils.getTargetObject(movieReader);
        Assertions.assertTrue(XmlMovieReader.class.isAssignableFrom(targetObject.getClass()));
    }
}