package lan.dk.podcastserver.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;

/**
 * Created by kevin on 03/04/2016 for PodcastServerMigration
 */
@Slf4j
@Service
public class BackupDb implements Tasklet {

    private final JdbcTemplate jdbcTemplate;
    /*private final Path destination = Paths.get("/tmp");*/

    @Autowired
    BackupDb(@Qualifier("jdbcOutput") JdbcTemplate jdbcTemplate) throws URISyntaxException {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        jdbcTemplate.execute("SCRIPT TO '/tmp/updated.sql';");

        return RepeatStatus.FINISHED;
    }
}
