package lan.dk.podcastserver.batch.writer;

import lan.dk.podcastserver.bean.CoverIdUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kevin on 08/04/2016 for PodcastServerMigration
 */
@Slf4j
@Service
public class CoverUrlWriter implements ItemWriter<CoverIdUrl> {

    private static final String UPDATE = "UPDATE COVER SET URL = ? WHERE ID = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    CoverUrlWriter(@Qualifier("jdbcOutput") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(List<? extends CoverIdUrl> covers) throws Exception {
        covers
            .stream()
                .peek(c -> log.info("nouvelle url {}", c.url()))
                .forEach(this::save);
    }

    private void save(CoverIdUrl c) {
        int update = jdbcTemplate.update(UPDATE, c.url(), c.id().toString());
        log.info("Number of update : {}", update);
    }
}
