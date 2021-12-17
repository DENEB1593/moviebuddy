package moviebuddy.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieFinder {

    private final MovieReader movieFinder;

    @Autowired  // 생성자에 Autowired를 추가해주면 의존성 주입 진행한다.
    // 생성자의 매개변수가 1개인 경우 Autowired를 생성해도 되지만, 2개 이상인 경우 Autowired를 지정해줘야한다
    public MovieFinder(@Qualifier("csvMovieReader") MovieReader movieReader) {
        this.movieFinder = Objects.requireNonNull(movieReader);
    }

    /**
     * 저장된 영화 목록에서 감독으로 영화를 검색한다.
     *
     * @param directedBy 감독
     * @return 검색된 영화 목록
     */
    public List<Movie> directedBy(String directedBy) {
        return movieFinder.loadMovies().stream()
                .filter(it -> it.getDirector().toLowerCase().contains(directedBy.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * 저장된 영화 목록에서 개봉년도로 영화를 검색한다.
     *
     * @param releasedYearBy
     * @return 검색된 영화 목록
     */
    public List<Movie> releasedYearBy(int releasedYearBy) {
        return movieFinder.loadMovies().stream()
                .filter(it -> Objects.equals(it.getReleaseYear(), releasedYearBy))
                .collect(Collectors.toList());
    }
}
