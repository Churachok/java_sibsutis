package lab3.task3;

public class Main {
    public static void main(String[] args) {
        System.out.println("Цикл работает только с интерфейсом. Для нового формата достаточно добавить реализацию в массив.");
        Course course = new Course("JAVA-101", "Java", 40);
        CourseFormatter[] formatters = {
            new CompactCourseFormatter(), new DetailedCourseFormatter(), new UpperCourseFormatter()
        };
        for (CourseFormatter formatter : formatters) {
            System.out.println(formatter.format(course));
        }
    }
}

record Course(String code, String title, int hours) {}
interface CourseFormatter { String format(Course course); }
class CompactCourseFormatter implements CourseFormatter {
    public String format(Course course) { return course.code(); }
}
class DetailedCourseFormatter implements CourseFormatter {
    public String format(Course course) { return course.code() + " " + course.title() + " " + course.hours(); }
}
class UpperCourseFormatter implements CourseFormatter {
    public String format(Course course) { return course.title().toUpperCase(java.util.Locale.ROOT); }
}
