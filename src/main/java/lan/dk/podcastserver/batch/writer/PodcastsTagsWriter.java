package lan.dk.podcastserver.batch.writer;

import lan.dk.podcastserver.entity.PodcastsTags;
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
public class PodcastsTagsWriter implements ItemWriter<PodcastsTags> {

    private static final String INSERT = "INSERT INTO PODCAST_TAGS (PODCASTS_ID, TAGS_ID) VALUES (?, ?);";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    PodcastsTagsWriter(@Qualifier("jdbcOutput") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(List<? extends PodcastsTags> links) throws Exception {
        links.stream().forEach(this::save);
    }

    private void save(PodcastsTags t) {
        jdbcTemplate.update(INSERT, t.podcast(), t.tag());
    }
}
