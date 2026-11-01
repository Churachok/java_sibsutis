package lab3.task5;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        System.out.println("Identifiable, CoursePolicy, Formatter и ClockProvider — интерфейсы для заменяемого поведения. BaseEntity — абстрактный класс с общим ID. Course — обычный класс с данными.");
        Course course = new Course(1, "Java");
        CoursePolicy policy = value -> !value.title.isBlank();
        Formatter formatter = value -> value.getId() + ": " + value.title;
        ClockProvider clock = Instant::now;
        System.out.println(policy.accept(course));
        System.out.println(formatter.format(course));
        System.out.println(clock.now());
    }
}

interface Identifiable { long getId(); }
interface CoursePolicy { boolean accept(Course course); }
interface Formatter { String format(Course course); }
interface ClockProvider { Instant now(); }
abstract class BaseEntity implements Identifiable {
    private final long id;
    BaseEntity(long id) { this.id = id; }
    public long getId() { return id; }
}
class Course extends BaseEntity {
    final String title;
    Course(long id, String title) { super(id); this.title = title; }
}
