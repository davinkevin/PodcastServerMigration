package lan.dk.podcastserver.batch.writer;

import lan.dk.podcastserver.entity.WatchList;
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
public class WatchListWriter implements ItemWriter<WatchList> {

    private static final String INSERT = "INSERT INTO WATCH_LIST (ID, NAME) VALUES (?, ?);";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    WatchListWriter(@Qualifier("jdbcOutput") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void write(List<? extends WatchList> watchLists) throws Exception {
        watchLists
            .stream()
            .forEach(this::save);
    }

    private void save(WatchList w) {
        jdbcTemplate.update(INSERT, w.id(), w.name());
    }
}
