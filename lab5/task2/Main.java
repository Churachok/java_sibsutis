package lab5.task2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("LinkedHashSet убирает повторы и сохраняет первый порядок. У равных кодов должны совпадать equals и hashCode.");
        List<CourseCode> codes = List.of(new CourseCode(" java-101 "), new CourseCode("SQL-101"), new CourseCode("JAVA-101"));
        Set<CourseCode> unique = new LinkedHashSet<>(codes);
        System.out.println(unique);
        System.out.println("Совпадают: " + codes.get(0).equals(codes.get(2)));
    }
}
record CourseCode(String value) {
    CourseCode { value = Objects.requireNonNull(value).trim().toUpperCase(Locale.ROOT); }
}
