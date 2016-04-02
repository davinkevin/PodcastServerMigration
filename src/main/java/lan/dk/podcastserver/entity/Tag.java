package lan.dk.podcastserver.entity;

import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Set;
import java.util.UUID;

/**
 * Created by kevin on 07/06/2014.
 */

/*@Entity*/
@Builder
@Getter @Setter
@Accessors(chain = true)
public class Tag {

    /*@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")*/
    private UUID id;

    /*@Column(unique = true)*/
    private String name;

    /*@ManyToMany(mappedBy = "tags")*/
    private Set<Podcast> podcasts = Sets.newHashSet();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Tag)) return false;

        Tag tag = (Tag) o;

        return new EqualsBuilder()
                .append(id, tag.id)
                .append(name, tag.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
