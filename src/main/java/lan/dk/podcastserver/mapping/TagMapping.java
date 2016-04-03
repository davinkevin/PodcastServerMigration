package lan.dk.podcastserver.mapping;

import lan.dk.podcastserver.entity.Tag;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
public class TagMapping extends AbstractMapping<Tag> {

    @Override
    public Tag add(Tag tag) {
        map.put(tag.getOldId(), tag.getId());
        return tag;
    }
}
