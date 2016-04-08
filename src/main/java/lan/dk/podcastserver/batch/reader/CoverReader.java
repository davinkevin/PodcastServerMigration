package lan.dk.podcastserver.batch.reader;

import lan.dk.podcastserver.entity.Cover;
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
public class CoverReader extends JdbcPagingItemReader<Cover> {


    @Autowired
    public CoverReader(@Qualifier("input") DataSource input) throws Exception {
        SqlPagingQueryProviderFactoryBean pqp = new SqlPagingQueryProviderFactoryBean();

        pqp.setDatabaseType("H2");
        pqp.setDataSource(input);
        pqp.setSelectClause("SELECT *");
        pqp.setFromClause("FROM COVER");
        pqp.setSortKey("ID");

        this.setDataSource(input);
        this.setQueryProvider(pqp.getObject());
        this.setPageSize(100);
        this.setRowMapper(rowMapper());
    }

    private RowMapper<Cover> rowMapper() {
        return (rs, rowNum) -> Cover
                .builder()
                    .id(UUID.randomUUID())
                    .oldId(rs.getLong("id"))
                    .height(rs.getInt("height"))
                    .width(rs.getInt("width"))
                    .url(rs.getString("url"))
                .build();
    }


}
