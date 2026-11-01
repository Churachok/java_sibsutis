package lab6.task12;

import java.time.*;
import java.util.List;

public class Main {
    static ZonedDateTime resolve(LocalDateTime local, ZoneId zone) throws InvalidLocalTimeException {
        List<ZoneOffset> offsets = zone.getRules().getValidOffsets(local);
        if (offsets.isEmpty()) { throw new InvalidLocalTimeException("Несуществующее время: " + local + " " + zone); }
        return ZonedDateTime.ofStrict(local, offsets.get(0), zone);
    }
    public static void main(String[] args) throws InvalidLocalTimeException {
        System.out.println("Политика занятия: несуществующее локальное время отклоняем, для повторённого выбираем первый offset. Час считается по прошедшему времени, день — по календарю.");
        ZoneId zone = ZoneId.of("Europe/Berlin");
        LocalDateTime gap = LocalDateTime.of(2026, 3, 29, 2, 30);
        System.out.println("Автокоррекция Java: " + gap.atZone(zone));
        try { resolve(gap, zone); }
        catch (InvalidLocalTimeException e) { System.out.println(e.getMessage()); }
        LocalDateTime overlap = LocalDateTime.of(2026, 10, 25, 2, 30);
        System.out.println("Два offset: " + zone.getRules().getValidOffsets(overlap));
        ZonedDateTime resolved = resolve(overlap, zone);
        System.out.println("Выбран ранний момент: " + resolved);
        System.out.println("Поздний: " + resolved.withLaterOffsetAtOverlap());
        ZonedDateTime beforeGap = LocalDateTime.of(2026, 3, 28, 12, 0).atZone(zone);
        System.out.println("Плюс час: " + beforeGap.plusHours(1));
        System.out.println("Плюс день: " + beforeGap.plusDays(1));
        ZonedDateTime beforeTransition = LocalDateTime.of(2026, 3, 29, 1, 30).atZone(zone);
        System.out.println("Час через переход: " + beforeTransition.plusHours(1));
    }
}
class InvalidLocalTimeException extends Exception {
    InvalidLocalTimeException(String message) { super(message); }
}
