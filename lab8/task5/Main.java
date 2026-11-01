package lab8.task5;

import java.util.*;

public class Main {
    static String longestTitle(List<Course> courses) {
        return courses.stream().max(Comparator.comparingInt(Course::duration).thenComparing(Course::code))
            .map(course -> course.title() + " (" + course.duration() + " ч.)")
            .orElse("Курсов нет");
    }
    public static void main(String[] args) {
        System.out.println("max может вернуть пустой Optional. map преобразует найденный курс, orElse задаёт ответ для пустого списка.");
        System.out.println(longestTitle(List.of(new Course("JAVA-101", "Java", 40), new Course("SQL-101", "SQL", 20))));
        System.out.println(longestTitle(List.of()));
    }
}
record Course(String code, String title, int duration) {}
