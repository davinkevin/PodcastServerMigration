package lan.dk.podcastserver.batch.reader;

import lan.dk.podcastserver.entity.Podcast;
import lan.dk.podcastserver.mapping.CoverMapping;
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
public class PodcastReader extends JdbcPagingItemReader<Podcast> {

    final CoverMapping coverMapping;

    @Autowired
    public PodcastReader(@Qualifier("input") DataSource input, CoverMapping coverMapping) throws Exception {
        this.coverMapping = coverMapping;
        SqlPagingQueryProviderFactoryBean pqp = new SqlPagingQueryProviderFactoryBean();

        pqp.setDatabaseType("H2");
        pqp.setDataSource(input);
        pqp.setSelectClause("SELECT *");
        pqp.setFromClause("FROM PODCAST");
        pqp.setSortKey("ID");

        this.setDataSource(input);
        this.setQueryProvider(pqp.getObject());
        this.setPageSize(100);
        this.setRowMapper(rowMapper());
    }

    private RowMapper<Podcast> rowMapper() {
        return (rs, rowNum) -> Podcast.builder()
                .oldId(rs.getLong("ID"))
                .id(UUID.randomUUID())
                .title(rs.getString("title"))
                .url(rs.getString("url"))
                .signature(rs.getString("signature"))
                .type(rs.getString("type"))
                .description(rs.getString("description"))
                .hasToBeDeleted(rs.getBoolean("HAS_TO_BE_DELETED"))
                .lastUpdate(rs.getString("LAST_UPDATE"))
                .cover(coverMapping.of(rs.getLong("COVER_ID")))
                .build();
    }


}
