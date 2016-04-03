package lan.dk.podcastserver.batch.writer;

import lan.dk.podcastserver.entity.WatchListsItems;
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
public class WatchListsItemsWriter implements ItemWriter<WatchListsItems> {

    private static final String INSERT = "INSERT INTO WATCH_LIST_ITEMS (WATCH_LISTS_ID, ITEMS_ID) VALUES (?, ?);";
    private final JdbcTemplate jdbcTemplate;

    @Autowired WatchListsItemsWriter(@Qualifier("jdbcOutput") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(List<? extends WatchListsItems> links) throws Exception {
        links.stream().forEach(this::save);
    }

    private void save(WatchListsItems l) {
        jdbcTemplate.update(INSERT, l.watchList(), l.item());
    }
}
