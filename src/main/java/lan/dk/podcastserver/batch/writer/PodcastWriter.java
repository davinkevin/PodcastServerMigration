package lan.dk.podcastserver.batch.writer;

import lan.dk.podcastserver.entity.Podcast;
import lan.dk.podcastserver.mapping.PodcastMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Slf4j
@Service
public class PodcastWriter implements ItemWriter<Podcast> {

    private static final String INSERT = "INSERT INTO PODCAST (ID, TITLE, URL, SIGNATURE, TYPE, DESCRIPTION, HAS_TO_BE_DELETED, LAST_UPDATE, COVER_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private final PodcastMapping podcastMapping;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    PodcastWriter(PodcastMapping podcastMapping, @Qualifier("jdbcOutput") JdbcTemplate jdbcTemplate) {
        this.podcastMapping = podcastMapping;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(List<? extends Podcast> podcasts) throws Exception {
        podcasts
                .stream()
                .map(podcastMapping::add)
                .forEach(this::save)
        ;

        log.info("{} elements in mapping store", podcastMapping.map.size());
    }

    private void save(Podcast p) {
        jdbcTemplate.update(INSERT, p.id(), p.title(), p.url(), p.signature(), p.type(), p.description(), p.hasToBeDeleted(), p.lastUpdate(), p.cover());
    }
}
