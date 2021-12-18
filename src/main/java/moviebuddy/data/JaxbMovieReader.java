package moviebuddy.data;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import moviebuddy.ApplicationException;
import moviebuddy.MovieBuddyProfile;
import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieReader;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Profile(MovieBuddyProfile.XML_MODE)
@Repository
public class JaxbMovieReader implements MovieReader {
    @Override
    public List<Movie> loadMovies() {
        try {
            final JAXBContext jaxb = JAXBContext.newInstance(MovieMetaData.class);
            final Unmarshaller unmarshaller = jaxb.createUnmarshaller();

            final InputStream content =ClassLoader.getSystemResourceAsStream("movie_metadata.xml");
            final Source source = new StreamSource(content);
            final MovieMetaData metaData = (MovieMetaData) unmarshaller.unmarshal(source);

            return metaData.toMovies();

        } catch (JAXBException e) {
            throw new ApplicationException("failed to load movies data", e);
        }
    }

    @XmlRootElement(name = "moviemetadata")
    @Getter
    @Setter
    public static class MovieMetaData {
        private List<MovieData> movies;

        public List<Movie> toMovies() {
            return movies.stream()
                    .map(MovieData::toMovie)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class MovieData {
        private String title;
        private List<String> genres;
        private String language;
        private String country;
        private int releaseYear;
        private String director;
        private List<String> actors;
        private URL imdbLink;
        private String watchedDate;

        public Movie toMovie() {
            return Movie.of(title, genres, language, country, releaseYear, director, actors, imdbLink, watchedDate);
        }
    }
}
