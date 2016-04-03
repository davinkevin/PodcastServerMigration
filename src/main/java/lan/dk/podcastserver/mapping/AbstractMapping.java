package lan.dk.podcastserver.mapping;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;

/**
 * Created by kevin on 02/04/2016 for PodcastServerMigration
 */
public abstract class AbstractMapping<T> {

    public final Map<Long, UUID> map = Maps.newConcurrentMap();

    public abstract T add(T cover);

    public UUID of(Long id) {
        return map.get(id);
    }

}
