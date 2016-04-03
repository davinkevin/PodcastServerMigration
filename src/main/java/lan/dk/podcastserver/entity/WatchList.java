package lan.dk.podcastserver.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * Created by kevin on 17/01/2016 for PodcastServer
 */
/*@Entity*/
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class WatchList {
    private UUID id;
    private String name;
}
