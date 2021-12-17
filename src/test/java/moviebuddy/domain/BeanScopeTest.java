package moviebuddy.domain;

import moviebuddy.MovieBuddyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScopeTest {

    @Test
    void Equals_MovieFinderBean() {
        // 별도의 스코프를 지정하지 않으면 빈은 기본적으로 싱글톤(Singleton) 스코프로 지정한다
        ApplicationContext context = new AnnotationConfigApplicationContext(MovieBuddyFactory.class);
        MovieFinder movieFinder = context.getBean(MovieFinder.class);
        MovieFinder movieFinder2 = context.getBean(MovieFinder.class);

        // 프로토타입 스코프를 지정하면 빈 요청을 받을 때 마다 새로운 객체를 생성해줌
        Assertions.assertEquals(movieFinder, movieFinder2);
    }
}
