package lan.dk.podcastserver.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.nio.file.Path;
import java.util.UUID;

/*@Entity*/
@Slf4j
@Builder
@Getter
@Accessors(chain = true, fluent = true)
@NoArgsConstructor @AllArgsConstructor
public class Item {

    public  static Path rootFolder;
    public  static String fileContainer;
    public  static final Item DEFAULT_ITEM = new Item();
    private static final String PROXY_URL = "/api/podcast/%s/items/%s/download%s";

    private Long oldId;
    private UUID id;
    private String title;
    private String url;
    private String description;
    private String mimeType;
    private Long length;
    private String fileName;
    private Status status = Status.NOT_DOWNLOADED;

    private UUID podcast;
    private UUID cover;

    private String pubDate;
    private String creationDate;
    private String downloadDate;

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
                .append((pubDate != null) ? pubDate : null)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", pubDate=" + pubDate +
                ", description='" + description + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", length=" + length +
                ", status='" + status + '\'' +
                ", downloaddate=" + downloadDate +
                ", podcast=" + podcast +
                '}';
    }
}
