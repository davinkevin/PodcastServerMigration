package lan.dk.podcastserver.mapping;

import lan.dk.podcastserver.entity.Cover;
import org.springframework.stereotype.Service;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Service
public class CoverMapping extends AbstractMapping<Cover> {

    public Cover add(Cover cover) {
        map.put(cover.oldId(), cover.id());
        return cover;
    }
}
