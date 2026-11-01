package lab6.task10;

import java.time.*;
import java.time.format.*;

public class Main {
    static LocalDate parse(String field, String value) throws InvalidDateFieldException {
        if (value == null) { throw new InvalidDateFieldException(field, null, null); }
        try { return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT)); }
        catch (DateTimeParseException e) { throw new InvalidDateFieldException(field, value, e); }
    }
    public static void main(String[] args) {
        System.out.println("STRICT отклоняет невозможные даты и лишний текст. Сообщение содержит поле и значение; cause сохранён, но пользователю не печатается.");
        for (String value : new String[]{"29.02.2024", "29.02.2025", "31.04.2026", "01.11.2026 text"}) {
            try { System.out.println(parse("startDate", value)); }
            catch (InvalidDateFieldException e) { System.out.println(e.getMessage()); }
        }
    }
}
class InvalidDateFieldException extends Exception {
    private final String field;
    private final String value;
    InvalidDateFieldException(String field, String value, Throwable cause) {
        super("Поле " + field + ": неверная дата [" + value + "]", cause);
        this.field = field;
        this.value = value;
    }
    String field() { return field; }
    String value() { return value; }
}
