package utils;

import constants.Constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Date/time formatting utilities for screenshots, reports, and test data.
 */
public class DateTimeUtil {

    private DateTimeUtil() {}

    /** Filesystem-safe timestamp: 2024-03-15_14-30-55 */
    public static String getTimestamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT));
    }

    /** Human-readable for report headers: 15-Mar-2024 14:30:55 */
    public static String getReportTimestamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(Constants.REPORT_TIMESTAMP_FMT));
    }

    /** Today as yyyy-MM-dd */
    public static String getDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /** Future/past date offset by days, with custom pattern */
    public static String getFutureDate(int daysFromNow, String pattern) {
        return LocalDate.now().plusDays(daysFromNow)
                .format(DateTimeFormatter.ofPattern(pattern));
    }

    /** Unique ID for order refs, usernames etc: 20240315-143055 */
    public static String getUniqueId() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
    }
}
