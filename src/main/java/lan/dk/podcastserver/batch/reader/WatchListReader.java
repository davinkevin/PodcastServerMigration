package lan.dk.podcastserver.batch.reader;

import lan.dk.podcastserver.entity.WatchList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

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
                    .id(asUUID(rs, "ID"))
                    .name(rs.getString("NAME"))
                .build();
    }

    public static UUID asUUID(ResultSet rs, String label) throws SQLException {
        String dashLessUUID = rs.getString(label);

        String p1 = dashLessUUID.substring(0, 7);
        String p2 = dashLessUUID.substring(8, 11);
        String p3 = dashLessUUID.substring(12, 15);
        String p4 = dashLessUUID.substring(16, 19);
        String p5 = dashLessUUID.substring(20, 31);

        return UUID.fromString( p1 + "-" + p2 + "-" + p3 + "-" + p4 + "-" + p5 );
    }


}
