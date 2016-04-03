package lan.dk.podcastserver.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.UUID;

@Builder @Getter
@Accessors(fluent = true)
public class Tag {

    private Long oldId;
    private UUID id;
    private String name;

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
