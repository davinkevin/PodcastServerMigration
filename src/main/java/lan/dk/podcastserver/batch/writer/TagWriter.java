package lan.dk.podcastserver.batch.writer;

import lan.dk.podcastserver.entity.Tag;
import lan.dk.podcastserver.mapping.TagMapping;
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
public class TagWriter implements ItemWriter<Tag> {

    private static final String INSERT = "INSERT INTO TAG (ID, NAME) VALUES (?, ?);";

    private final TagMapping tagMapping;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    TagWriter(TagMapping tagMapping, @Qualifier("jdbcOutput") JdbcTemplate jdbcTemplate) {
        this.tagMapping = tagMapping;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(List<? extends Tag> tags) throws Exception {
        tags
            .stream()
            .map(tagMapping::add)
            .forEach(this::save);
    }

    private void save(Tag t) {
        jdbcTemplate.update(
                INSERT,
                t.id(), t.name()
        );
    }
}
