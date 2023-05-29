package dev.mateusneres.stockmanager.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String formatInstant(Instant instant) {
        String timeZone = "America/Sao_Paulo";

        LocalDateTime localDate = instant.atZone(ZoneId.of(timeZone)).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return localDate.format(formatter);
    }

}
