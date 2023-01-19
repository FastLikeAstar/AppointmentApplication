package tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import tools.DateConverter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.*;

public class DateConverterTest {


    @Test
    public void testConvertZonedToLocal(){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Assert.assertEquals("Local dates should match", localDateTime, DateConverter.convertZonedToLocal(zonedDateTime));
    }

    @Test
    public void testConvertSystemLocalDateTimeToUtc(){
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 1,1);
        LocalDateTime utcAsLocal = LocalDateTime.of(2023, 1, 1, 8,1);
        ZonedDateTime testAnswer = utcAsLocal.atZone(ZoneId.of("UTC"));
        Assert.assertEquals("Testing as system default in MST, please change test if otherwise. should be same hours", testAnswer, DateConverter.convertSystemLocalDateTimeToUtc(localDateTime));
    }

    @Test
    public void testConvertLocalDateToUTC(){
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 1, 1,1);
        LocalDateTime utcAsLocal = LocalDateTime.of(2023, 1, 1, 8,1);
        ZonedDateTime testAnswer = utcAsLocal.atZone(ZoneId.of("UTC"));
        Assert.assertEquals("Testing as system default in MST, please change test if otherwise. should be same hours", testAnswer, DateConverter.convertSystemLocalDateTimeToUtc(localDateTime));
    }

    @Test
    public void testConvertUtcToTimestamp(){
        LocalDateTime localDateTime = LocalDateTime.now(Clock.systemUTC());
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC")); // to add UTC tag

        Instant timestamp = localDateTime.toInstant(ZoneOffset.UTC);
        Timestamp test = Timestamp.from(timestamp);

        Assert.assertEquals("Should be same timestamp", test, DateConverter.convertUtcToTimestamp(zonedDateTime));
    }

    @Test
    public void testConvertUtcToLocalTimestamp(){
        // Assumes that local time zone is not UTC
        Timestamp utcTimestamp = Timestamp.from(Instant.now());
        Timestamp localTimestamp = DateConverter.convertUtcToLocalTimestamp(utcTimestamp);
        Instant localInstant = localTimestamp.toInstant();
        ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(localInstant, ZoneId.systemDefault());
        Instant utcInstant = utcTimestamp.toInstant();
        ZonedDateTime utcZonedDateTime = ZonedDateTime.ofInstant(utcInstant, ZoneId.of("UTC"));
        Assert.assertEquals(localZonedDateTime, utcZonedDateTime.withZoneSameLocal(ZoneId.systemDefault()));
    }

    @Test
    public void testConvertLocalTsToUtcTimestamp() {
        // Assumes that local time zone is not UTC
        Timestamp localTimestamp = Timestamp.from(Instant.now());
        Timestamp utcTimestamp = DateConverter.convertLocalTsToUtcTimestamp(localTimestamp);
        Instant localInstant = localTimestamp.toInstant();
        ZonedDateTime localZonedDateTime = ZonedDateTime.ofInstant(localInstant, ZoneId.systemDefault());
        Instant utcInstant = utcTimestamp.toInstant();
        ZonedDateTime utcZonedDateTime = ZonedDateTime.ofInstant(utcInstant, ZoneId.of("UTC"));
        Assert.assertEquals(localZonedDateTime.withZoneSameInstant(ZoneId.of("UTC")), utcZonedDateTime);
    }

}
