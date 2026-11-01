package lab3.task1;

public class Main {
    public static void main(String[] args) {
        System.out.println("Компилятор видит метод интерфейса. При запуске выбирается реализация реального объекта.");
        Course course = new Course("JAVA-101", "Основы Java", 40);
        CourseFormatter formatter = new CompactCourseFormatter();
        System.out.println(formatter.format(course));
        formatter = new DetailedCourseFormatter();
        System.out.println(formatter.format(course));
    }
}

record Course(String code, String title, int hours) {}

interface CourseFormatter {
    String format(Course course);
}

class CompactCourseFormatter implements CourseFormatter {
    public String format(Course course) {
        return course.code() + ": " + course.title();
    }
}

class DetailedCourseFormatter implements CourseFormatter {
    public String format(Course course) {
        return "Код: " + course.code() + ", название: " + course.title() + ", часов: " + course.hours();
    }
}
