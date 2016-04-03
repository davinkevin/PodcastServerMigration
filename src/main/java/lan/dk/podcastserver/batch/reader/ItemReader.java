package lan.dk.podcastserver.batch.reader;

import lan.dk.podcastserver.entity.Item;
import lan.dk.podcastserver.entity.Status;
import lan.dk.podcastserver.mapping.CoverMapping;
import lan.dk.podcastserver.mapping.PodcastMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Slf4j
@Service
public class ItemReader extends JdbcPagingItemReader<Item> {

    private final CoverMapping coverMapping;
    private final PodcastMapping podcastMapping;

    @Autowired
    public ItemReader(@Qualifier("input") DataSource input, CoverMapping coverMapping, PodcastMapping podcastMapping) throws Exception {
        this.coverMapping = coverMapping;
        this.podcastMapping = podcastMapping;
        SqlPagingQueryProviderFactoryBean pqp = new SqlPagingQueryProviderFactoryBean();

        pqp.setDatabaseType("H2");
        pqp.setDataSource(input);
        pqp.setSelectClause("SELECT *");
        pqp.setFromClause("FROM ITEM");
        pqp.setSortKey("ID");

        this.setDataSource(input);
        this.setQueryProvider(pqp.getObject());
        this.setPageSize(100);
        this.setRowMapper(rowMapper());
    }

    private RowMapper<Item> rowMapper() {
        return (rs, rowNum) -> Item.builder()
                .oldId(rs.getLong("ID"))
                .id(UUID.randomUUID())
                .title(rs.getString("title"))
                .url(rs.getString("url"))
                .description(rs.getString("description"))
                .mimeType(rs.getString("MIMETYPE"))
                .length(rs.getLong("LENGTH"))
                .fileName(rs.getString("FILE_NAME"))
                .status(Status.of(rs.getString("STATUS")))

                .cover(coverMapping.of(rs.getLong("COVER_ID")))
                .podcast(podcastMapping.of(rs.getLong("PODCAST_ID")))

                .pubdate(rs.getString("PUBDATE"))
                .creationDate(rs.getString("CREATION_DATE"))
                .downloadDate(rs.getString("DOWNLOAD_DATE"))

                .build();
    }


}
