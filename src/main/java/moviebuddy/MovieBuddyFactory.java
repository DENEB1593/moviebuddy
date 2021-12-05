package moviebuddy;

import moviebuddy.domain.CsvMovieReader;
import moviebuddy.domain.MovieFinder;
import moviebuddy.domain.MovieReader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

// 빈 메타정보를 설정하며..
@Configuration
// Import를 사용하면 다른 설정(Configuaration)에서 Bean정보를 불러옴
@Import({MovieBuddyFactory.DomainModuleConfig.class
        , MovieBuddyFactory.DataSourceModule.class})
public class MovieBuddyFactory {

    // 내부 설정(Configuration) 등록
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")  // Intellij 버그용 어노테이션
    @Configuration
    static class DomainModuleConfig {
        @Bean
        public MovieFinder movieFinder(MovieReader movieReader) {
            return new MovieFinder(movieReader);
        }
    }

    @Configuration
    static class DataSourceModule {
        @Bean
        public MovieReader movieReader() {
            return new CsvMovieReader();
        }
    }
}
