package lab8.task1;

import java.util.*;

public class Main {
    static List<Course> sorted(List<Course> courses) {
        return courses.stream().sorted(Comparator.comparing(Course::level)
            .thenComparingInt(Course::duration).thenComparing(Course::code).thenComparing(Course::title)).toList();
    }
    public static void main(String[] args) {
        System.out.println("Порядок: level, duration, code, title. Дополнительные критерии убирают зависимость от порядка входа.");
        List<Course> courses = List.of(new Course(Level.BEGINNER, 40, "JAVA-101", "Java"),
            new Course(Level.BEGINNER, 40, "SQL-101", "SQL"), new Course(Level.ADVANCED, 20, "JAVA-301", "Streams"),
            new Course(Level.BEGINNER, 20, "WEB-101", "HTML"));
        List<Course> reversed = new ArrayList<>(courses);
        Collections.reverse(reversed);
        System.out.println(sorted(courses));
        System.out.println("Порядок совпал: " + sorted(courses).equals(sorted(reversed)));
    }
}
enum Level { BEGINNER, INTERMEDIATE, ADVANCED }
record Course(Level level, int duration, String code, String title) {}
