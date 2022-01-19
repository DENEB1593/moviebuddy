package moviebuddy;

import moviebuddy.data.CsvMovieReader;
import moviebuddy.data.XmlMovieReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

// 빈 메타정보를 설정하며..
@Configuration
@PropertySource("/application.properties")
@ComponentScan(basePackages = {"moviebuddy"})   // 컴포넌트 스캔을 한다 basePackages를 통해 패키지 번위를 지
@Import({MovieBuddyFactory.DomainModuleConfig.class // Import를 사용하면 다른 설정(Configuaration)에서 Bean정보를 불러옴
        , MovieBuddyFactory.DataSourceModule.class})
public class MovieBuddyFactory {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("moviebuddy");

        return marshaller;
    }


    // 내부 설정(Configuration) 등록
    @Configuration
    static class DomainModuleConfig {
    }

    @Configuration
    static class DataSourceModule {

    }
}
