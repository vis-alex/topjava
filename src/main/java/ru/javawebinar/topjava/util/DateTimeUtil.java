package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(
            LocalDateTime lt, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {

        return lt.toLocalTime().compareTo(startTime) >= 0 && lt.toLocalTime().compareTo(endTime) < 0
                && lt.toLocalDate().compareTo(startDate) >= 0 && lt.toLocalDate().compareTo(endDate) <=0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

