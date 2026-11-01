package lab4.task10;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        System.out.println("Код очищается от крайних пробелов и переводится в верхний регистр. Тесты проверяют нормализацию и недопустимые значения."); System.out.println(new CourseCode(" java-101 ")); }
}
record CourseCode(String value) {
    CourseCode {
        if (value == null) { throw new IllegalArgumentException("Пустой код"); }
        value = value.trim().toUpperCase(Locale.ROOT);
        if (!value.matches("[A-Z]{2,10}-[0-9]{3}")) { throw new IllegalArgumentException("Неверный код курса"); }
    }
}
