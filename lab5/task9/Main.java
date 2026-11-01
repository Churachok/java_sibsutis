package lab5.task9;

import java.util.*;

public class Main {
    static <T> void copy(List<? extends T> producer, List<? super T> consumer) {
        for (T value : producer) { consumer.add(value); }
    }
    public static void main(String[] args) {
        System.out.println("Из extends читаем Course, но не добавляем: там может быть список подтипа. В super добавляем Course, но при чтении гарантирован только Object.");
        List<OnlineCourse> source = List.of(new OnlineCourse("Java"));
        List<Course> target = new ArrayList<>();
        copy(source, target);
        List<? extends Course> producer = source;
        Course course = producer.get(0);
        List<? super Course> consumer = new ArrayList<Object>();
        consumer.add(course);
        Object value = consumer.get(0);
        System.out.println(target.get(0).title);
        System.out.println(value);
    }
}
class Course {
    final String title;
    Course(String title) { this.title = title; }
    @Override
    public String toString() { return title; }
}
class OnlineCourse extends Course { OnlineCourse(String title) { super(title); } }
