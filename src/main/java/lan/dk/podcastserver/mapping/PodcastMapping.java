package lan.dk.podcastserver.mapping;

import lan.dk.podcastserver.entity.Podcast;
import org.springframework.stereotype.Service;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Service
public class PodcastMapping extends AbstractMapping<Podcast> {

    @Override
    public Podcast add(Podcast podcast) {
        map.put(podcast.oldId(), podcast.id());
        return podcast;
    }

}
