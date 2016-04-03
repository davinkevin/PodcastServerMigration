package lan.dk.podcastserver.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * Created by kevin on 03/04/2016 for PodcastServerMigration
 */
@Builder
@Getter @Accessors(fluent = true)
public class PodcastsTags {

    UUID podcast;
    UUID tag;

}
