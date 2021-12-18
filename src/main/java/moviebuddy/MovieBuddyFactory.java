package moviebuddy;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// 빈 메타정보를 설정하며..
@Configuration
@ComponentScan(basePackages = {"moviebuddy"})   // 컴포넌트 스캔을 한다 basePackages를 통해 패키지 번위를 지
// Import를 사용하면 다른 설정(Configuaration)에서 Bean정보를 불러옴
@Import({MovieBuddyFactory.DomainModuleConfig.class
        , MovieBuddyFactory.DataSourceModule.class})
public class MovieBuddyFactory {

    // 내부 설정(Configuration) 등록
    @Configuration
    static class DomainModuleConfig {
    }

    @Configuration
    static class DataSourceModule {

    }
}
