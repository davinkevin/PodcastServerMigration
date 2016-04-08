package lan.dk.podcastserver.batch.reader;

import lan.dk.podcastserver.bean.CoverIdUrl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.UUID;

/**
 * Created by kevin on 08/04/2016 for PodcastServerMigration
 */
@Slf4j
@Service

public class CoverUrlReader extends JdbcPagingItemReader<CoverIdUrl> {

    private static final String URL_FORMATTER = "%s/api/podcast/%s/%s";
    private final String domain;

    @Autowired
    public CoverUrlReader(@Qualifier("output") DataSource output, @Value("${migration.domain}") String domain) throws Exception {
        this.domain = domain;
        SqlPagingQueryProviderFactoryBean pqp = new SqlPagingQueryProviderFactoryBean();

        pqp.setDatabaseType("H2");
        pqp.setDataSource(output);
        pqp.setSelectClause("SELECT c.ID as COVER, c.URL as URL, p.ID as PODCAST");
        pqp.setFromClause("FROM COVER c INNER JOIN PODCAST p ON c.id = p.COVER_ID");
        pqp.setWhereClause("WHERE c.URL LIKE '%"+ domain +"%'");
        pqp.setSortKey("COVER");

        this.setDataSource(output);
        this.setQueryProvider(pqp.getObject());
        this.setPageSize(100);
        this.setRowMapper(rowMapper());
    }

    private RowMapper<CoverIdUrl> rowMapper() {
        return (rs, rn) -> CoverIdUrl.builder()
                    .id(UUID.fromString(rs.getString("COVER")))
                    .url(String.format(URL_FORMATTER, domain, UUID.fromString(rs.getString("PODCAST")).toString(), FilenameUtils.getName(rs.getString("URL"))))
                .build();
    }

}
