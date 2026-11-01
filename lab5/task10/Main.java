package lab5.task10;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поля equals/hashCode нельзя менять, пока объект хранится как ключ. Изменять название безопасно, если равенство зависит только от final ID.");
        Course course = new Course(1, "Java");
        Set<Course> set = new HashSet<>();
        Map<Course, String> map = new HashMap<>();
        set.add(course);
        map.put(course, "Есть");
        course.title = "SQL";
        System.out.println(set.contains(course) + " " + map.get(course));
        BrokenCourse broken = new BrokenCourse("A");
        Set<BrokenCourse> badSet = new HashSet<>();
        Map<BrokenCourse, String> badMap = new HashMap<>();
        badSet.add(broken);
        badMap.put(broken, "Есть");
        broken.title = "B";
        System.out.println(badSet.contains(broken) + " " + badMap.get(broken));
    }
}
class Course {
    final int id;
    String title;
    Course(int id, String title) { this.id = id; this.title = title; }
    @Override
    public boolean equals(Object other) { return other instanceof Course c && id == c.id; }
    @Override
    public int hashCode() { return Integer.hashCode(id); }
}
class BrokenCourse {
    String title;
    BrokenCourse(String title) { this.title = title; }
    @Override
    public boolean equals(Object other) { return other instanceof BrokenCourse c && title.equals(c.title); }
    @Override
    public int hashCode() { return title.hashCode(); }
}
