package lan.dk.podcastserver.config;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Configuration
public class InMemoryJobRepositoryConfig {

    @Bean
    BatchConfigurer configurer(@Qualifier("batchDataSource") DataSource dataSource){
        return new DefaultBatchConfigurer(dataSource);
    }

    @Bean
    @Primary
    DataSource batchDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("batchDataSource")
                .build();
    }
}
