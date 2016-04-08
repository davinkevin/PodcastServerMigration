package lan.dk.podcastserver.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * Created by kevin on 08/04/2016 for PodcastServerMigration
 */
@Builder
@ToString
@Getter @Accessors(fluent = true)
public class CoverIdUrl {

    private UUID id;
    private String url;

}
