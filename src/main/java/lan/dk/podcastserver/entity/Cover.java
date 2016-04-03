package lan.dk.podcastserver.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

/*@Entity*/
@Builder
@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public class Cover {

    private Long oldId;
    private UUID id;
    private String url;
    private Integer width;
    private Integer height;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cover)) return false;

        Cover cover = (Cover) o;
        return new EqualsBuilder()
                .append(StringUtils.lowerCase(url), StringUtils.lowerCase(cover.url))
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(StringUtils.lowerCase(url))
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Cover{" +
                "height=" + height +
                ", width=" + width +
                ", url='" + url + '\'' +
                ", id=" + id +
                ", oldId=" + oldId +
                '}';
    }
}
