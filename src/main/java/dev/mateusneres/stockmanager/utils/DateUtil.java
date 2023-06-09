package dev.mateusneres.stockmanager.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * This class is responsible for formatting the date and time.
 */
public class DateUtil {

    /**
     * This method is responsible for formatting the instant.
     * @param instant Instant
     * @return String
     */
    public static String formatInstant(Instant instant) {
        String timeZone = "America/Sao_Paulo";

        LocalDateTime localDate = instant.atZone(ZoneId.of(timeZone)).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return localDate.format(formatter);
    }

}
