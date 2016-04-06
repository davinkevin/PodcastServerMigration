package lan.dk.podcastserver.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

/**
 * Created by kevin on 06/04/2016 for PodcastServerMigration
 */
@Slf4j
@Service
public class LoadInputDb implements Tasklet {
    private final JdbcTemplate jdbcTemplate;
    private final Path input;

    @Autowired
    LoadInputDb(@Qualifier("jdbcInput") JdbcTemplate jdbcTemplate, @Value("${migration.input}") File input) throws URISyntaxException {
        this.jdbcTemplate = jdbcTemplate;
        this.input = input.toPath();
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        log.info("Loading file in input : {}", input.toAbsolutePath().toString());

        jdbcTemplate.execute("DROP ALL OBJECTS;");
        jdbcTemplate.execute("RUNSCRIPT FROM '"+ this.input.toUri() +"';");

        return RepeatStatus.FINISHED;
    }
}
