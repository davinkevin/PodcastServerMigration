package lan.dk.podcastserver.mapping;

import lan.dk.podcastserver.entity.Tag;
import org.springframework.stereotype.Service;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Service
public class TagMapping extends AbstractMapping<Tag> {

    @Override
    public Tag add(Tag tag) {
        map.put(tag.oldId(), tag.id());
        return tag;
    }
}
