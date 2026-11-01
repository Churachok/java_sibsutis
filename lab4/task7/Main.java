package lab4.task7;

import java.util.*;

public class Main {
    static Optional<Course> findCourse(int id) {
        return id == 1 ? Optional.of(new Course(1, "Java")) : Optional.empty();
    }
    static ParseResult parseCourse(String text) {
        if (text == null || text.isBlank()) { return new ParseResult(null, "Пустое название"); }
        return new ParseResult(new Course(2, text.trim()), null);
    }
    static EnrollmentResult enroll(int freePlaces) { return new EnrollmentResult(freePlaces > 0, freePlaces > 0 ? "Принят" : "Нет мест"); }
    static int calculatePrice(int hours, int rate) {
        if (hours < 0 || rate < 0) { throw new IllegalArgumentException("Цена не может быть отрицательной"); }
        return Math.multiplyExact(hours, rate);
    }
    public static void main(String[] args) {
        System.out.println("Отсутствие курса — Optional, неверный ввод и отказ в записи — результат с причиной. Отрицательная цена нарушает контракт и вызывает unchecked-исключение.");
        System.out.println(findCourse(99).orElse(new Course(0, "Не найден")));
        System.out.println(parseCourse(" "));
        System.out.println(enroll(0));
        System.out.println(calculatePrice(40, 100));
        try { calculatePrice(-1, 100); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
    }
}
record Course(int id, String title) {}
record ParseResult(Course course, String error) {
    ParseResult {
        if ((course == null) == (error == null)) { throw new IllegalArgumentException("Нужен курс либо ошибка"); }
    }
}
record EnrollmentResult(boolean accepted, String reason) {}
