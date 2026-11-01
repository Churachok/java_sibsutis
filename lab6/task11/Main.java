package lab6.task11;

import java.time.*;
import java.util.TimeZone;

public class Main {
    static ZonedDateTime show(Instant webinar, ZoneId userZone) { return webinar.atZone(userZone); }
    public static void main(String[] args) {
        System.out.println("Момент хранится как Instant. Зона пользователя передаётся явно, поэтому default zone сервера не меняет бизнес-результат.");
        Instant webinar = Instant.parse("2026-11-01T12:00:00Z");
        ZoneId[] zones = {ZoneId.of("Asia/Novosibirsk"), ZoneId.of("Europe/Berlin"), ZoneId.of("America/New_York")};
        for (ZoneId zone : zones) { System.out.println(show(webinar, zone)); }
        TimeZone original = TimeZone.getDefault();
        ZonedDateTime before = show(webinar, zones[0]);
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Honolulu"));
            System.out.println("Смена зоны сервера не повлияла: " + before.equals(show(webinar, zones[0])));
        } finally { TimeZone.setDefault(original); }
    }
}
