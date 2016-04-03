package lan.dk.podcastserver.batch.reader;

import lan.dk.podcastserver.entity.Tag;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * Created by kevin on 03/04/2016 for PodcastServerMigration
 */
@Service
public class TagReader extends JdbcPagingItemReader<Tag> {

    @Autowired
    public TagReader(@Qualifier("input") DataSource input) throws Exception {
        SqlPagingQueryProviderFactoryBean pqp = new SqlPagingQueryProviderFactoryBean();

        pqp.setDatabaseType("H2");
        pqp.setDataSource(input);
        pqp.setSelectClause("SELECT *");
        pqp.setFromClause("FROM TAG");
        pqp.setSortKey("ID");

        this.setDataSource(input);
        this.setQueryProvider(pqp.getObject());
        this.setPageSize(100);
        this.setRowMapper(rowMapper());
    }

    private RowMapper<Tag> rowMapper() {
        return (rs, rowNum) -> Tag.builder()
                    .id(UUID.randomUUID())
                    .oldId(rs.getLong("ID"))
                    .name(rs.getString("NAME"))
                .build();
    }

}
