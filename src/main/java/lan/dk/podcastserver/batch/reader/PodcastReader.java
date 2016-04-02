package lan.dk.podcastserver.batch.reader;

import lan.dk.podcastserver.entity.Podcast;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PodcastReader implements ItemReader<Podcast> {

    final JdbcTemplate jdbcTemplate;

    @Override
    public Podcast read() throws Exception {
        return jdbcTemplate.query("SELECT * FROM PODCAST WHERE ID = 63;", (rs, rowNum) -> {
            log.info(rs.getNString("ID"));
            return Podcast.DEFAULT_PODCAST;
        }).get(0);
    }
}
