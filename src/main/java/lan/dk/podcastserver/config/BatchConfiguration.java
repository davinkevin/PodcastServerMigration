package lan.dk.podcastserver.config;

import lan.dk.podcastserver.batch.reader.PodcastReader;
import lan.dk.podcastserver.batch.writer.PodcastWriter;
import lan.dk.podcastserver.entity.Podcast;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("migrationId")
                /*.incrementer(new RunIdIncrementer())*/
                /*.listener(listener())*/
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(PodcastReader podcastReader, PodcastWriter podcastWriter) {
        return stepBuilderFactory.get("podcast")
                .<Podcast, Podcast> chunk(10)
                .reader(podcastReader)
                .writer(podcastWriter)
                .build();
    }

    @Bean
    JobRepository jobRepository () throws Exception {
        return new MapJobRepositoryFactoryBean().getObject();
    }

}
