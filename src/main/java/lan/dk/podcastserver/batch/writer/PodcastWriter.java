package lan.dk.podcastserver.batch.writer;

import lan.dk.podcastserver.entity.Podcast;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Slf4j
@Service
/*@RequiredArgsConstructor(onConstructor = @__(@Autowired))*/
public class PodcastWriter implements ItemWriter<Podcast> {

    @Override
    public void write(List<? extends Podcast> list) throws Exception {
        log.info(list.get(0).getTitle());
    }
}
