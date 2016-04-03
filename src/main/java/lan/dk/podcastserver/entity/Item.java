package lan.dk.podcastserver.entity;

import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

/*@Entity*/
@Slf4j
@Builder
@Getter @Setter
@Accessors(chain = true)
@NoArgsConstructor @AllArgsConstructor
public class Item {

    public  static Path rootFolder;
    public  static String fileContainer;
    public  static final Item DEFAULT_ITEM = new Item();
    private static final String PROXY_URL = "/api/podcast/%s/items/%s/download%s";

    private Long oldId;
    private UUID id;
    private String title;
    private Cover cover;
    private Podcast podcast;
    private String url;
    private ZonedDateTime pubdate;
    private String description;
    private String mimeType;
    private Long length;
    private String fileName;
    private ZonedDateTime downloadDate;
    private Status status = Status.NOT_DOWNLOADED;
    private ZonedDateTime creationDate;
    private Set<WatchList> watchLists = Sets.newHashSet();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        if (this == DEFAULT_ITEM && o != DEFAULT_ITEM || this != DEFAULT_ITEM && o == DEFAULT_ITEM) return false;

        Item item = (Item) o;

        if (id != null && item.id != null)
            return id.equals(item.id);

        if (url != null && item.url != null) {
            return url.equals(item.url) || FilenameUtils.getName(item.url).equals(FilenameUtils.getName(url));
        }

        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(url)
                .append((pubdate != null) ? pubdate.toInstant() : null)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", pubdate=" + pubdate +
                ", description='" + description + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", length=" + length +
                ", status='" + status + '\'' +
                ", downloaddate=" + downloadDate +
                ", podcast=" + podcast +
                '}';
    }
}
