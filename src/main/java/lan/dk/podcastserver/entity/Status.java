package lan.dk.podcastserver.entity;


import static java.util.Objects.isNull;

/**
 * Created by kevin on 18/02/15.
 */
public enum Status {
    NOT_DOWNLOADED,
    DELETED,
    STARTED,
    FINISH,
    STOPPED,
    PAUSED;

    public static Status of(String value) {
        return isNull(value) ? NOT_DOWNLOADED : Status.valueOf(value);
    }
    
}
