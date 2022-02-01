package moviebuddy;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;

import java.util.concurrent.TimeUnit;

// 빈 메타정보를 설정하며..
@Configuration
@PropertySource("/application.properties")
@ComponentScan(basePackages = {"moviebuddy"})   // 컴포넌트 스캔을 한다 basePackages를 통해 패키지 번위를 지
@Import({MovieBuddyFactory.DomainModuleConfig.class // Import를 사용하면 다른 설정(Configuaration)에서 Bean정보를 불러옴
        , MovieBuddyFactory.DataSourceModuleConfig.class})
@EnableCaching //선언전 캐쉬기능 동작
public class MovieBuddyFactory implements CachingConfigurer {

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


    /*
    @Bean
    public CachingAspect cachingAspect(CacheManager cacheManager) {
        return new CachingAspect(cacheManager);
    }
     */

/*    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public Advisor cachingAdvisor(CacheManager cacheManager) {
        AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, CacheResult.class);
        Advice advice = new CachingAdvice(cacheManager);

        // Advisor = Pointcut(대상선정 알고리즘) + Advice(부가기능)
        return new DefaultPointcutAdvisor(pointcut, advice);
    }*/

    @Override
    public CacheManager cacheManager() {
        // cachemanager
        return caffineCacheManger();
    }

    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(caffineCacheManger());
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        // @EnableAsync -> AsyncConfigurer
        // @EnableScheduling -> SchedulingConfigurer
        return new SimpleCacheErrorHandler();
    }

    // 내부 설정(Configuration) 등록
    @Configuration
    static class DomainModuleConfig {
    }

    @Configuration
    static class DataSourceModuleConfig {
    }
}
