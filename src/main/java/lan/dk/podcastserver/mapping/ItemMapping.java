package lan.dk.podcastserver.mapping;

import lan.dk.podcastserver.entity.Item;
import org.springframework.stereotype.Service;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
@Service
public class ItemMapping extends AbstractMapping<Item> {

    @Override
    public Item add(Item cover) {
        map.put(cover.oldId(), cover.id());
        return cover;
    }

}
