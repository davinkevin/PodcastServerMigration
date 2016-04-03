package lan.dk.podcastserver.batch.writer;

import lan.dk.podcastserver.entity.Cover;
import lan.dk.podcastserver.mapping.CoverMapping;
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
public class CoverWriter implements ItemWriter<Cover> {

    private static final String INSERT = "INSERT INTO COVER (ID, URL, WIDTH, HEIGHT) VALUES (?, ?, ?, ?);";

    private final CoverMapping coverMapping;
    private final JdbcTemplate jdbcTemplate;

    @Autowired CoverWriter(CoverMapping coverMapping, @Qualifier("jdbcOutput") JdbcTemplate jdbcTemplate) {
        this.coverMapping = coverMapping;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(List<? extends Cover> covers) throws Exception {
        covers
            .stream()
            .map(coverMapping::add)
            .forEach(this::save);
    }

    private void save(Cover c) {
        jdbcTemplate.update(INSERT, c.id(), c.url(), c.width(), c.height());
    }
}
