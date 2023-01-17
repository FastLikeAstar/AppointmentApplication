package tools;


import java.sql.Timestamp;
import java.time.*;

public class DateConverter {

    /**
     * Helper method to convert a given ZonedDate into the LocalDateTime.
     * This is important because the database stores all time in UTC and the business hours are EST.
     * @param zonedDateTime the given Date to convert into Local time
     * @return the provided Zoned Date Time as Local Date Time
     */
    public static LocalDateTime convertZonedToLocal(ZonedDateTime zonedDateTime){
        LocalDateTime localeDateTime;
        localeDateTime = zonedDateTime.toLocalDateTime();

        return localeDateTime;
    }


    /**
     * Helper method to convert a given LocalDateTime into UTC time in the form as ZonedDateTime.
     * This is important because the database stores all time in UTC.
     * @param localDateTime Time from user's perspective.
     * @return returns the local time as a Utc Zoned Time
     */
    public static ZonedDateTime convertSystemLocalDateTimeToUtc(LocalDateTime localDateTime){
        ZonedDateTime localInUtcTime;
        ZonedDateTime localToZoned;
        localToZoned = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        localInUtcTime = localToZoned.withZoneSameLocal(ZoneId.of("UTC"));

        return localInUtcTime;
    }


    public static ZonedDateTime convertLocalDateToUTC(LocalDate date) {
        ZoneId userTimeZone = ZoneId.systemDefault();
        LocalDateTime dateTime = date.atStartOfDay();
        ZonedDateTime zonedDateTime = dateTime.atZone(userTimeZone);
        ZonedDateTime dateAsUtc = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
        return dateAsUtc;
    }

    /**
     * Helper method to convert a given ZonedDateTime (UTC) into a timestamp to store in database.
     * The provided timestamp will be in UTC. Other timezones are not compatible.
     * @param utcDate the ZonedDateTime (UTC) to convert to a timestamp according to UTC.
     * @return Provided ZonedDateTime(UTC) as a Timestamp (without timezone).
     */
    public static Timestamp convertUtcToTimestamp(ZonedDateTime utcDate){
        Timestamp timestamp;
        ZonedDateTime utcZdt= utcDate.withZoneSameLocal(ZoneId.of("UTC"));
        Instant utcAsInstant =  utcZdt.toInstant();
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

    /**
     * Helper method to convert a given timestamp from the database to local time (was UTC).
     * The provided timestamp should be of zoned UTC or this will lead to inaccurate conversions.
     * @param timestamp Timestamp from database. (This should be UTC)
     * @return Timestamp of local time of provided Timestamp.
     */
    public static Timestamp convertUtcToLocalTimestamp(Timestamp timestamp) {
        Timestamp timeToReturn;
        Instant instant = timestamp.toInstant();

        ZonedDateTime utcDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
        ZonedDateTime localDateTime = utcDateTime.withZoneSameLocal(ZoneId.systemDefault());

        timeToReturn = Timestamp.valueOf(localDateTime.toLocalDateTime());
        return timeToReturn;
    }

    /**
     * Helper method to convert a given timestamp from the database to local time (was UTC).
     * The provided timestamp should be of zoned UTC or this will lead to inaccurate conversions.
     * @param timestamp Timestamp from database. (This should be UTC)
     * @return Timestamp of local time of provided Timestamp.
     */
    public static Timestamp convertLocalTsToUtcTimestamp(Timestamp timestamp) {
        Timestamp timeToReturn;
        Instant instant = timestamp.toInstant();
        ZonedDateTime utcDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        ZonedDateTime localDateTime = utcDateTime.withZoneSameLocal(ZoneId.of("UTC"));

        timeToReturn = Timestamp.valueOf(localDateTime.toLocalDateTime());
        return timeToReturn;
    }
}
