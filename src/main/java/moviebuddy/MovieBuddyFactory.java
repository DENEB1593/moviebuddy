package moviebuddy;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import moviebuddy.cache.CachingAdvice;
import moviebuddy.data.CachingMovieReader;
import moviebuddy.data.CsvMovieReader;
import moviebuddy.data.XmlMovieReader;
import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieReader;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.FileNotFoundException;
import java.lang.annotation.Target;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;

// 빈 메타정보를 설정하며..
@Configuration
@PropertySource("/application.properties")
@ComponentScan(basePackages = {"moviebuddy"})   // 컴포넌트 스캔을 한다 basePackages를 통해 패키지 번위를 지
@Import({MovieBuddyFactory.DomainModuleConfig.class // Import를 사용하면 다른 설정(Configuaration)에서 Bean정보를 불러옴
        , MovieBuddyFactory.DataSourceModuleConfig.class})
public class MovieBuddyFactory {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("moviebuddy");

        return marshaller;
    }

    @Bean
    public CacheManager caffineCacheManger() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS));

        return cacheManager;
    }


    // 내부 설정(Configuration) 등록
    @Configuration
    static class DomainModuleConfig {
    }

    @Configuration
    static class DataSourceModuleConfig {
        // 두개의 Bean이 발견되는 경우 Primary의 빈을 먼저 확인
        @Primary
        @Bean
        public ProxyFactoryBean cachingMovieReaderFactory(ApplicationContext context) {
            MovieReader target = context.getBean(MovieReader.class);
            CacheManager cacheManager = context.getBean(CacheManager.class);

            // 프록시 Bean 등록
            ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
            proxyFactoryBean.setTarget(target);
            // proxyFactoryBean.setProxyTargetClass(true);
            // 클래스 프록시 활성화한다(서브클래스도 활성화한다)
            // 클래스 프록시 제약사항 (final, 생성자 2번 호출)
            proxyFactoryBean.addAdvice(new CachingAdvice(cacheManager));

            return proxyFactoryBean;
        }


    }
}
