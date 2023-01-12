package tools;


import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateConverter {

    /**
     * Helper method to convert a given UTC ZonedDate into the LocalDateTime.
     * This is important because the database stores all time in UTC.
     * @param utcDateTime the given Date to convert into Local time
     * @return the provided UTC Date Time as Local Date Time
     */
    public static LocalDateTime convertUtcToLocal(ZonedDateTime utcDateTime){
        LocalDateTime localeDateTime;
        localeDateTime = utcDateTime.toLocalDateTime();

        return localeDateTime;
    }


    /**
     * Helper method to convert a given LocalDateTime into UTC time in the form as ZonedDateTime.
     * This is important because the database stores all time in UTC.
     * @param localDateTime Time from user's perspective.
     * @return returns the local time as a Utc Zoned Time
     */
    public static ZonedDateTime convertLocalToUtc(LocalDateTime localDateTime){
        ZonedDateTime localInUtcTime;
        ZonedDateTime localToZoned;
        localToZoned = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        localInUtcTime = localToZoned.withZoneSameLocal(ZoneId.of("UTC"));

        return localInUtcTime;
    }

    /**
     * Helper method to convert a given ZonedDateTime (UTC) into a timestamp to store in database.
     * The provided timestamp will be in UTC. Other timezones are not compatible.
     * @param utcDate the ZonedDateTime (UTC) to convert to a timestamp according to UTC.
     * @return Provided ZonedDateTime(UTC) as a Timestamp (without timezone).
     */
    public static Timestamp convertUtcToTimestamp(ZonedDateTime utcDate){
        Timestamp timestamp;
        Instant utcAsInstant = utcDate.toInstant();
        timestamp = Timestamp.from(utcAsInstant);
        return timestamp;
    }

    /**
     * Helper method to convert a given timestamp to stored in database to ZonedDateTime (UTC).
     * The provided timestamp should be of zoned UTC or this will lead to inaccurate conversions.
     * @param timestamp Timestamp from database. (This should be UTC)
     * @return ZonedDateTime of provided Timestamp (without timezone).
     */
    public static ZonedDateTime convertTimestampToUtc(Timestamp timestamp){
        ZonedDateTime utcFromTimestamp;
        Instant instant = timestamp.toInstant();
        utcFromTimestamp = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));

        return utcFromTimestamp;
    }
}
