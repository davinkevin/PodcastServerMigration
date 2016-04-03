package lan.dk.podcastserver.config;

import lan.dk.podcastserver.batch.reader.CoverReader;
import lan.dk.podcastserver.batch.writer.CoverWriter;
import lan.dk.podcastserver.batch.writer.PodcastWriter;
import lan.dk.podcastserver.entity.Cover;
import lan.dk.podcastserver.entity.Podcast;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcPagingItemReader;
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
    public Job migrationOfId(Step resetDbStep, Step coverStep, Step podcastStep) {
        return jobBuilderFactory.get("migrationOfId")
                /*.incrementer(new RunIdIncrementer())*/
                /*.listener(listener())*/
                /*.flow(podcastStep)*/
                .start(resetDbStep)
                .next(coverStep)
                .next(podcastStep)
                /*
                .next(coverStep)
                .end()
                */
                .build();
    }

    @Bean
    public Step resetDbStep(Tasklet resetDbTasklet) {
        return stepBuilderFactory.get("resetDbStep")
                .tasklet(resetDbTasklet)
                .build();
    }

    @Bean
    public Step podcastStep(JdbcPagingItemReader<Podcast> podcastReader, PodcastWriter podcastWriter) {
        return stepBuilderFactory.get("podcast")
                .<Podcast, Podcast> chunk(10)
                .reader(podcastReader)
                .writer(podcastWriter)
                .build();
    }

    @Bean
    public Step coverStep(CoverReader coverReader, CoverWriter coverWriter) {
        return stepBuilderFactory.get("cover")
                .<Cover, Cover> chunk(100)
                .reader(coverReader)
                .writer(coverWriter)
                .build();
    }

}
