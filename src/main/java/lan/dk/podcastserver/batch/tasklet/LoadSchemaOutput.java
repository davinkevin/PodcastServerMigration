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
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by kevin on 03/04/2016 for PodcastServerMigration
 */
@Slf4j
@Service
public class LoadSchemaOutput implements Tasklet {

    private final JdbcTemplate jdbcTemplate;
    private final Path output;

    @Autowired
    LoadSchemaOutput(@Qualifier("jdbcOutput") JdbcTemplate jdbcTemplate) throws URISyntaxException {
        this.jdbcTemplate = jdbcTemplate;
        this.output = Paths.get(LoadSchemaOutput.class.getClassLoader().getResource("db/schema.sql").toURI());
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        log.info("Loading file to output : {}", output.toAbsolutePath().toString());

        jdbcTemplate.execute("DROP ALL OBJECTS;");
        jdbcTemplate.execute("RUNSCRIPT FROM '"+ this.output +"';");

        return RepeatStatus.FINISHED;
    }
}