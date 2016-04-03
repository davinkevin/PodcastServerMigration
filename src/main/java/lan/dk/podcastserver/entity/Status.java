package lan.dk.podcastserver.entity;


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
        return Status.valueOf(value);
    }
    
}
