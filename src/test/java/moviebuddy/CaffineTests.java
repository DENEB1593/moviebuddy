package moviebuddy;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class CaffineTests {

    @Test
    void useCache() throws InterruptedException {
        // 카페인 Cache 생성
        Cache<String, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(200, TimeUnit.MILLISECONDS)   // 200ms 후 저장된 캐시 삭제
                .maximumSize(100)
                .build();

        String key = "springrunner";
        Object value = new Object();

        Assertions.assertNull(cache.getIfPresent(key));

        cache.put(key, value);
        Assertions.assertEquals(value, cache.getIfPresent(key));

        TimeUnit.MILLISECONDS.sleep(100);

        Assertions.assertEquals(value, cache.getIfPresent(key));

        TimeUnit.MILLISECONDS.sleep(100);
    
        // 캐시시간 만료되어 값이 소거됨
        Assertions.assertEquals(value, cache.getIfPresent(key));
    }

}
