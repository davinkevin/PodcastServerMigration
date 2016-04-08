package lan.dk.podcastserver.batch.reader;

import lan.dk.podcastserver.entity.WatchListsItems;
import lan.dk.podcastserver.mapping.ItemMapping;
import lan.dk.podcastserver.utils.UUIDUtils;
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
public class WatchListsItemsReader extends JdbcPagingItemReader<WatchListsItems> {

    private final ItemMapping itemMapping;

    @Autowired
    public WatchListsItemsReader(@Qualifier("input") DataSource input, ItemMapping itemMapping) throws Exception {
        this.itemMapping = itemMapping;

        SqlPagingQueryProviderFactoryBean pqp = new SqlPagingQueryProviderFactoryBean();

        pqp.setDatabaseType("H2");
        pqp.setDataSource(input);
        pqp.setSelectClause("SELECT *");
        pqp.setFromClause("FROM WATCH_LIST_ITEMS");
        pqp.setSortKey("WATCH_LISTS_ID");

        this.setDataSource(input);
        this.setQueryProvider(pqp.getObject());
        this.setPageSize(100);
        this.setRowMapper(rowMapper());
    }

    private RowMapper<WatchListsItems> rowMapper() {
        return (rs, rowNum) -> WatchListsItems.builder()
                    .item(itemMapping.of(rs.getLong("ITEMS_ID")))
                    .watchList(UUIDUtils.binaryToUUID(rs, "WATCH_LISTS_ID"))
                .build();
    }

}
