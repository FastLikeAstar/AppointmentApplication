package tools;


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
     * @param localDateTime
     * @return returns the local time as a Utc Zoned Time
     */
    public static ZonedDateTime convertLocalToUtc(LocalDateTime localDateTime){
        ZonedDateTime localInUtcTime;
        ZonedDateTime localToZoned;
        localToZoned = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        localInUtcTime = localToZoned.withZoneSameLocal(ZoneId.of("UTC"));

        return localInUtcTime;
    }
}
