package lan.dk.podcastserver.batch.writer;

import lan.dk.podcastserver.entity.Item;
import lan.dk.podcastserver.mapping.ItemMapping;
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
public class PodcastItemWriter implements ItemWriter<Item> {

    private static final String INSERT = "INSERT INTO ITEM (ID, TITLE, URL, DESCRIPTION, MIME_TYPE, LENGTH, FILE_NAME, STATUS, PODCAST_ID, COVER_ID, PUB_DATE, CREATION_DATE, DOWNLOAD_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private final ItemMapping itemMapping;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    PodcastItemWriter(ItemMapping itemMapping, @Qualifier("jdbcOutput") JdbcTemplate jdbcTemplate) {
        this.itemMapping = itemMapping;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(List<? extends Item> podcasts) throws Exception {
        podcasts
                .stream()
                .map(itemMapping::add)
                .forEach(this::save);
    }

    private void save(Item i) {
        jdbcTemplate.update(
                INSERT,
                i.id(), i.title(), i.url(), i.description(), i.mimeType(), i.length(), i.fileName(), i.status().toString(), i.podcast(), i.cover(), i.pubDate(), i.creationDate(), i.downloadDate()
        );
    }
}
