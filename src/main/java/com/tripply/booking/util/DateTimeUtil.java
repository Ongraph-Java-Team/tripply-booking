package com.tripply.booking.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class DateTimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private DateTimeUtil() {
        // Private constructor to prevent instantiation
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static String formatDate(LocalDate date) {
        try {
            return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (DateTimeParseException e) {
            logger.error("Error formatting date: {}", date, e);
            throw e;
        }
    }

    public static String formatTime(LocalTime time) {
        try {
            return time.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
        } catch (DateTimeParseException e) {
            logger.error("Error formatting time: {}", time, e);
            throw e;
        }
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        try {
            return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        } catch (DateTimeParseException e) {
            logger.error("Error formatting dateTime: {}", dateTime, e);
            throw e;
        }
    }

    public static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (DateTimeParseException e) {
            logger.error("Error parsing date: {}", date, e);
            throw e;
        }
    }

    public static LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT));
        } catch (DateTimeParseException e) {
            logger.error("Error parsing time: {}", time, e);
            throw e;
        }
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        } catch (DateTimeParseException e) {
            logger.error("Error parsing dateTime: {}", dateTime, e);
            throw e;
        }
    }

    public static Date toDate(LocalDateTime dateTime) {
        try {
            return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            logger.error("Error converting LocalDateTime to Date: {}", dateTime, e);
            throw new RuntimeException(e);
        }
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (Exception e) {
            logger.error("Error converting Date to LocalDateTime: {}", date, e);
            throw new RuntimeException(e);
        }
    }

    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        try {
            return ChronoUnit.DAYS.between(startDate, endDate);
        } catch (Exception e) {
            logger.error("Error calculating days between {} and {}", startDate, endDate, e);
            throw new RuntimeException(e);
        }
    }

    public static long hoursBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        try {
            return ChronoUnit.HOURS.between(startDateTime, endDateTime);
        } catch (Exception e) {
            logger.error("Error calculating hours between {} and {}", startDateTime, endDateTime, e);
            throw new RuntimeException(e);
        }
    }

    public static LocalDateTime startOfDay(LocalDate date) {
        try {
            return date.atStartOfDay();
        } catch (Exception e) {
            logger.error("Error getting start of day for date: {}", date, e);
            throw new RuntimeException(e);
        }
    }

    public static LocalDateTime endOfDay(LocalDate date) {
        try {
            return date.atTime(LocalTime.MAX);
        } catch (Exception e) {
            logger.error("Error getting end of day for date: {}", date, e);
            throw new RuntimeException(e);
        }
    }

    public static ZonedDateTime getCurrentZonedDateTime(ZoneId zoneId) {
        try {
            return ZonedDateTime.now(zoneId);
        } catch (Exception e) {
            logger.error("Error getting current ZonedDateTime for zoneId: {}", zoneId, e);
            throw new RuntimeException(e);
        }
    }

    public static ZonedDateTime toZonedDateTime(LocalDateTime dateTime, ZoneId zoneId) {
        try {
            return dateTime.atZone(zoneId);
        } catch (Exception e) {
            logger.error("Error converting LocalDateTime to ZonedDateTime: {}", dateTime, e);
            throw new RuntimeException(e);
        }
    }

    public static long calculateNumberOfNights(LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        // Calculate the duration between check-in and check-out times
        long hoursBetween = ChronoUnit.HOURS.between(checkInTime, checkOutTime);
        long daysBetween = ChronoUnit.DAYS.between(checkInTime.toLocalDate(), checkOutTime.toLocalDate());

        // If checkOutTime is on the same day or after checkInTime, consider it as 1 night
        if (hoursBetween < 24 && daysBetween == 0) {
            return 1;
        } else {
            return daysBetween;
        }
    }
}
