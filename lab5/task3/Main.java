package lab5.task3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("get читает значение, containsKey проверяет ключ. putIfAbsent принимает готовое значение, computeIfAbsent вычисляет отсутствующее. Повтор ID не перезаписываем.");
        Map<Integer, Course> index = new LinkedHashMap<>();
        for (Course course : List.of(new Course(1, "Java"), new Course(2, "SQL"), new Course(1, "Другой"))) {
            Course previous = index.putIfAbsent(course.id(), course);
            if (previous != null) { System.out.println("Повтор ID " + course.id() + ", сохранён " + previous.title()); }
        }
        System.out.println(index.get(1));
        System.out.println(index.containsKey(3));
        Course loaded = index.computeIfAbsent(3, id -> new Course(id, "Загруженный"));
        System.out.println(loaded);
        System.out.println(index);
    }
}
record Course(int id, String title) {}
