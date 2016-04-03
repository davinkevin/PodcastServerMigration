package lan.dk.podcastserver.config;

import lan.dk.podcastserver.batch.reader.*;
import lan.dk.podcastserver.batch.writer.*;
import lan.dk.podcastserver.entity.*;
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
    public Job migrationOfId(Step resetDbStep, Step coverStep, Step podcastStep, Step itemStep, Step tagStep, Step podcastsTagsStep, Step watchListStep, Step watchListsItemsStep) {
        return jobBuilderFactory.get("migrationOfId")
                /*.incrementer(new RunIdIncrementer())*/
                /*.listener(listener())*/
                /*.flow(podcastStep)*/
                .start(resetDbStep)
                .next(coverStep)
                .next(podcastStep)
                .next(itemStep)
                .next(tagStep)
                .next(podcastsTagsStep)
                .next(watchListStep)
                .next(watchListsItemsStep)
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
                .<Podcast, Podcast> chunk(100)
                .reader(podcastReader)
                .writer(podcastWriter)
                .build();
    }

    @Bean
    public Step itemStep(JdbcPagingItemReader<Item> itemReader, PodcastItemWriter itemWriter) {
        return stepBuilderFactory.get("item")
                .<Item, Item> chunk(100)
                .reader(itemReader)
                .writer(itemWriter)
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

    @Bean
    public Step tagStep(TagReader tagReader, TagWriter tagWriter) {
        return stepBuilderFactory.get("tag")
                .<Tag, Tag> chunk(100)
                .reader(tagReader)
                .writer(tagWriter)
                .build();
    }

    @Bean
    public Step podcastsTagsStep(PodcastsTagsReader reader, PodcastsTagsWriter writer) {
        return stepBuilderFactory.get("podcastsTags")
                .<PodcastsTags, PodcastsTags> chunk(100)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Step watchListStep(WatchListReader reader, WatchListWriter writer) {
        return stepBuilderFactory.get("watchList")
                .<WatchList, WatchList> chunk(100)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Step watchListsItemsStep(WatchListsItemsReader reader, WatchListsItemsWriter writer) {
        return stepBuilderFactory.get("watchListsItems")
                .<WatchListsItems, WatchListsItems> chunk(100)
                .reader(reader)
                .writer(writer)
                .build();
    }

}
