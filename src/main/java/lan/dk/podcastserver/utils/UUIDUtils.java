package lan.dk.podcastserver.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by kevin on 08/04/2016 for PodcastServerMigration
 */
public class UUIDUtils {

    public static UUID binaryToUUID(ResultSet rs, String label) throws SQLException {
        String dashLessUUID = rs.getString(label);

        String p1 = dashLessUUID.substring(0, 7);
        String p2 = dashLessUUID.substring(8, 11);
        String p3 = dashLessUUID.substring(12, 15);
        String p4 = dashLessUUID.substring(16, 19);
        String p5 = dashLessUUID.substring(20, 31);

        return UUID.fromString( p1 + "-" + p2 + "-" + p3 + "-" + p4 + "-" + p5 );
    }

}
