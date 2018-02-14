package ru.javawebinar.topjava.util;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public static <T extends Comparable> boolean isBetween(T elemDate, T startDate, T endDate) {
        return elemDate.compareTo(startDate) >= 0 && elemDate.compareTo(endDate) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseLocalDate(String param, HttpServletRequest request) {
        return Objects.isNull(request.getParameter(param)) ? null : LocalDate.parse(request.getParameter(param));
    }

    public static LocalTime parseLocalTime(String param, HttpServletRequest request) {
        return Objects.isNull(request.getParameter(param)) ? null : LocalTime.parse(request.getParameter(param));
    }
}
