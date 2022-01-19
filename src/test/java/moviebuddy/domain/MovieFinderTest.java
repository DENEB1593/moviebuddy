package moviebuddy.domain;

import moviebuddy.MovieBuddyFactory;
import moviebuddy.MovieBuddyProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

/**
 * @author springrunner.kr@gmail.com
 */
@SpringJUnitConfig(MovieBuddyFactory.class)		// ExtendWith,ContextConfiguration을 포함한 메타 어노테이션
@ActiveProfiles(MovieBuddyProfile.CSV_MODE)
//@ExtendWith(SpringExtension.class)	// 테스트 전략 실행을 확장할 때 사용함, 테스트용 스프링 컨테이너 지원
//@ContextConfiguration(classes = MovieBuddyFactory.class) // 스프링 테스트 컨텍스트 지원, ContextConfiguration를 지정 시 해당 정보로 빈을 구성함
public class MovieFinderTest {

	@Autowired
	MovieFinder movieFinder;

	// 생성자 주입
//	@Autowired
//	public MovieFinderTest(MovieFinder movieFinder) {
//		this.movieFinder = movieFinder;
//	}

	// set 메소드
//	@Autowired
//	void setMovieFinder(MovieFinder movieFinder) {
//		this.movieFinder = movieFinder;
//	}

	@Test
	void NotEmpty_directedBy() {
		List<Movie> movies = movieFinder.directedBy("Michael Bay");
		Assertions.assertEquals(3, movies.size());
	}

	@Test
	void NotEmpty_ReleasedYearBy() {
		List<Movie> movies = movieFinder.releasedYearBy(2015);
		Assertions.assertEquals(225, movies.size());
	}

}
