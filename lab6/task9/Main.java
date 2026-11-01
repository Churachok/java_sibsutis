package lab6.task9;

import java.time.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Period — календарный день: подходит для срока подписки. Duration — прошедшие 24 часа: подходит для timeout. При DST календарный день может длиться 23 часа.");
        ZonedDateTime start = ZonedDateTime.of(2026, 3, 28, 12, 0, 0, 0, ZoneId.of("Europe/Berlin"));
        ZonedDateTime calendarDay = start.plus(Period.ofDays(1));
        ZonedDateTime hours24 = start.plus(Duration.ofHours(24));
        System.out.println(start);
        System.out.println("Календарный день: " + calendarDay);
        System.out.println("24 часа: " + hours24);
        System.out.println("Прошло часов за календарный день: " + Duration.between(start.toInstant(), calendarDay.toInstant()).toHours());
    }
}
