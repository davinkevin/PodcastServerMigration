package lan.dk.podcastserver.batch.reader;

import lan.dk.podcastserver.entity.PodcastsTags;
import lan.dk.podcastserver.mapping.PodcastMapping;
import lan.dk.podcastserver.mapping.TagMapping;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Created by kevin on 03/04/2016 for PodcastServerMigration
 */
@Service
public class PodcastsTagsReader extends JdbcPagingItemReader<PodcastsTags> {

    private final PodcastMapping podcastMapping;
    private final TagMapping tagMapping;

    @Autowired
    public PodcastsTagsReader(@Qualifier("input") DataSource input, PodcastMapping podcastMapping, TagMapping tagMapping) throws Exception {
        this.podcastMapping = podcastMapping;
        this.tagMapping = tagMapping;

        SqlPagingQueryProviderFactoryBean pqp = new SqlPagingQueryProviderFactoryBean();

        pqp.setDatabaseType("H2");
        pqp.setDataSource(input);
        pqp.setSelectClause("SELECT *");
        pqp.setFromClause("FROM PODCAST_TAGS");
        pqp.setSortKey("PODCASTS_ID");

        this.setDataSource(input);
        this.setQueryProvider(pqp.getObject());
        this.setPageSize(100);
        this.setRowMapper(rowMapper());
    }

    private RowMapper<PodcastsTags> rowMapper() {
        return (rs, rowNum) -> PodcastsTags.builder()
                    .podcast(podcastMapping.of(rs.getLong("PODCASTS_ID")))
                    .tag(tagMapping.of(rs.getLong("TAGS_ID")))
                .build();
    }

}
