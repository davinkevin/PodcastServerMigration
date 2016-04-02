package lan.dk.podcastserver.entity;

import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
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

  /*  @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")*/
    private UUID id;
    private String title;

    /*@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)*/
    private Cover cover;

    /*@ManyToOne(cascade={CascadeType.MERGE}, fetch = FetchType.EAGER)*/
    private Podcast podcast;

    /*@Column(length = 65535, unique = true)*/
    private String url;

    private ZonedDateTime pubdate;

    /*@Column(length = 65535)*/
    private String description;
    private String mimeType;
    private Long length;
    private String fileName;
    private ZonedDateTime downloadDate;

    /*@Enumerated(EnumType.STRING)*/
    private Status status = Status.NOT_DOWNLOADED;

    /*@CreatedDate*/
    private ZonedDateTime creationDate;

    /*@ManyToMany(mappedBy = "items", cascade = CascadeType.REFRESH)*/
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

        return StringUtils.equals(getLocalUrl(), item.getLocalUrl());
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

    /* Helpers */
    /*@Transient*/
    private String getLocalUrl() {
        return (fileName == null)
                ? null
                : fileContainer + "/" + podcast.getTitle() + fileName;
    }

}
