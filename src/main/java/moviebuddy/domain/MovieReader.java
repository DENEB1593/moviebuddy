package moviebuddy.domain;

import moviebuddy.domain.Movie;

import java.util.List;

/**
 * MovieReader는 CSV,JAXB의 인터페이스다.
 * 도메인 영역이 아닌 데이터 영역에 위치해야 맞지만
 * MovieFinder는 MovieReader를 의존하고 있다.
 * 때문에 MovieReader가 data에 속해 있어 data 영역이 변경되는 경우 MovieFinder에 영향을 미치기 때문에
 * MovieReader를 domain 패키지에 위치시켜 영향을 최소화할 수 있다. 이를 분리된 인터페이스 패턴(Seperated Interface Pattern)이라고 한다.
 */
public interface MovieReader {

    List<Movie> loadMovies();

}
