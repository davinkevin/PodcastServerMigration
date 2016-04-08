package lan.dk.podcastserver.config;

import lan.dk.podcastserver.batch.reader.*;
import lan.dk.podcastserver.batch.tasklet.BackupOutputDb;
import lan.dk.podcastserver.batch.tasklet.LoadInputDb;
import lan.dk.podcastserver.batch.tasklet.LoadSchemaOutput;
import lan.dk.podcastserver.batch.writer.*;
import lan.dk.podcastserver.bean.CoverIdUrl;
import lan.dk.podcastserver.entity.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
    public Job migrationOfId(Step initInputDbStep, Step initOutputDbStep, Step coverStep, Step podcastStep, Step itemStep, Step tagStep, Step podcastsTagsStep, Step watchListStep, Step watchListsItemsStep, Step backupDbStep, Step changeCoverUrlStep) {
        return jobBuilderFactory.get("migrationOfId")
                .start(initInputDbStep)
                    .next(initOutputDbStep)
                    .next(coverStep)
                    .next(podcastStep)
                    .next(itemStep)
                    .next(tagStep)
                    .next(podcastsTagsStep)
                    .next(watchListStep)
                    .next(watchListsItemsStep)
                    .next(changeCoverUrlStep)
                    .next(backupDbStep)
                .build();
    }

    @Bean
    public Step initInputDbStep(LoadInputDb loadInputDb) {
        return stepBuilderFactory.get("initInputDbStep")
                .tasklet(loadInputDb)
                .build();
    }

    @Bean
    public Step initOutputDbStep(LoadSchemaOutput loadSchemaOutput) {
        return stepBuilderFactory.get("initOutputDbStep")
                .tasklet(loadSchemaOutput)
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

    @Bean
    public Step changeCoverUrlStep(CoverUrlReader reader, CoverUrlWriter writer) {
        return stepBuilderFactory.get("changeCoverUrl")
                .<CoverIdUrl, CoverIdUrl> chunk(100)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Step backupDbStep(BackupOutputDb backupOutputDb) {
        return stepBuilderFactory.get("backupdb")
                .tasklet(backupOutputDb)
                .build();
    }

}
