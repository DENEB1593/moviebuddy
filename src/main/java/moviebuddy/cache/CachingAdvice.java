package moviebuddy.cache;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Objects;

public class CachingAdvice implements MethodInterceptor {

    private final CacheManager cacheManager;

    public CachingAdvice(CacheManager cacheManager) {
        this.cacheManager = Objects.requireNonNull(cacheManager);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 캐시된 데이터가 존재하면, 즉시반환
        // 대상객체(invocation) : CSVMovieReader, XMLMovieReader
        Cache cache = cacheManager.getCache(invocation.getThis().getClass().getName());
        Object cachedValue = cache.get(invocation.getMethod().getName(), Object.class);
        if (Objects.nonNull(cachedValue)) {
            return cachedValue;
        }

        // 캐시된 데이터가 미존재면, 대상 객체 명령 위임 및 반환된 값을 저장
        cachedValue = invocation.proceed();
        cache.put(invocation.getMethod().getName(), cachedValue);

        return cachedValue;
    }
}
