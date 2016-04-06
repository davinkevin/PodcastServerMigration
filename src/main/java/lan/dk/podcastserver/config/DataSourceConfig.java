package lan.dk.podcastserver.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource input() throws SQLException {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName("org.h2.Driver");
        /*dataSource.setJdbcUrl("jdbc:h2:mem:input;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;INIT=DROP ALL OBJECTS\\; RUNSCRIPT FROM 'classpath:db/input.sql'");*/
        dataSource.setJdbcUrl("jdbc:h2:mem:input");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public DataSource output() throws SQLException, URISyntaxException {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName("org.h2.Driver");
        /*dataSource.setJdbcUrl("jdbc:h2:tcp://localhost:9092//tmp/output;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;INIT=DROP ALL OBJECTS\\; RUNSCRIPT FROM 'classpath:db/output.sql'");*/
        /*dataSource.setJdbcUrl("jdbc:h2:tcp://localhost:9092//tmp/output");*/
        dataSource.setJdbcUrl("jdbc:h2:mem:output");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }


    @Bean
    public JdbcTemplate jdbcInput(@Qualifier("input") DataSource input) {
        return new JdbcTemplate(input);
    }


    @Bean
    public JdbcTemplate jdbcOutput(@Qualifier("output") DataSource output) {
        return new JdbcTemplate(output);
    }
}
