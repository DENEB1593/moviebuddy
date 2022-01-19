package moviebuddy.data;

import lombok.extern.slf4j.Slf4j;
import moviebuddy.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public abstract class AbstractMetadataResourceMovieReader implements ResourceLoaderAware {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private String metadata;
    private ResourceLoader resourceLoader;

    public String getMetadata() {
        return metadata;
    }

    @Value("${movie.metadata}")
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public URL getMetadataUrl() {
        String location = getMetadata();

        if (location.startsWith("file:")) {
            // file URL 처리
        } else if (location.startsWith("http:")) {
            // http URL 처리
        }

        return ClassLoader.getSystemResource(location);
    }

    // 리소스 로더를 주입받는다
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource getMetadataResource() {
        // Spring Core를 활용한 리소스 주입
        return resourceLoader.getResource(getMetadata());
    }

    // 프로퍼티값 검증 로직, 의존성 주입 후 생성 됨
    @PostConstruct
    public void afterPropertiesSet() throws Exception {

        Resource resource = getMetadataResource();
        if (!resource.exists()) {
            throw new FileNotFoundException(metadata);
        }

        if (!resource.isReadable()) {
            throw new ApplicationException(String.format("cannot read to metadata. [%s]", metadata));

        }
        log.info(resource + "is ready");

//        URL metadataUrl = ClassLoader.getSystemResource(metadata);
        //
//        URL metadataUrl = getMetadataUrl();
//
//        if (Objects.isNull(metadataUrl)) {
//            throw new FileNotFoundException(metadata);
//        }
//
//        if (!Files.isReadable(Path.of(metadataUrl.toURI()))) {
//            throw new ApplicationException(String.format("cannot read to metadata. [%s]", metadata));
//        }
    }

    // 소멸 객체(I/O, 네트워크 등)
    // JSR-250 표준어노테이션을 사용하여 빈 소멸 시 실행하는 로직을 정의
    @PreDestroy
    public void destroy() throws Exception {
        log.info("destory..");
    }
}
