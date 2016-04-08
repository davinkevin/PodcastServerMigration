package lan.dk.podcastserver.batch.reader;

import lan.dk.podcastserver.entity.WatchList;
import lan.dk.podcastserver.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Slf4j
@Service
public class WatchListReader extends JdbcPagingItemReader<WatchList> {

    @Autowired
    public WatchListReader(@Qualifier("input") DataSource input) throws Exception {
        SqlPagingQueryProviderFactoryBean pqp = new SqlPagingQueryProviderFactoryBean();

        pqp.setDatabaseType("H2");
        pqp.setDataSource(input);
        pqp.setSelectClause("SELECT *");
        pqp.setFromClause("FROM WATCH_LIST");
        pqp.setSortKey("ID");

        this.setDataSource(input);
        this.setQueryProvider(pqp.getObject());
        this.setPageSize(100);
        this.setRowMapper(rowMapper());
    }

    private RowMapper<WatchList> rowMapper() {
        return (rs, rowNum) -> WatchList.builder()
                    .id(UUIDUtils.binaryToUUID(rs, "ID"))
                    .name(rs.getString("NAME"))
                .build();
    }


}
