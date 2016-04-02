package lan.dk.podcastserver.entity;

import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.UUID;

/**
 * Created by kevin on 17/01/2016 for PodcastServer
 */
/*@Entity*/
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WatchList {

    /*@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")*/
    private UUID id;
    private String name;
    private /*@ManyToMany*/ Set<Item> items = Sets.newHashSet();
}
