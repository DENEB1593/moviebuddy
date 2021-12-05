package moviebuddy;

import moviebuddy.domain.CsvMovieReader;
import moviebuddy.domain.MovieFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 빈 메타정보를 설정하며..
@Configuration
public class MovieBuddyFactory {

    // 스프링 컨테이너에 빈을 등록한다.
    @Bean
    public MovieFinder movieFinder() {
        return new MovieFinder(new CsvMovieReader());
    }

}
