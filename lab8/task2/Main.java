package lab8.task2;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("flatMap раскрывает модули и темы. Нормализованные темы становятся ключами индекса, а TreeSet убирает повторы курсов.");
        List<Course> courses = List.of(
            new Course("JAVA-101", List.of(new Module(List.of(" OOP ", "Collections")), new Module(List.of("oop", " Streams ")))),
            new Course("JAVA-201", List.of(new Module(List.of("streams", "Concurrency")))));
        Map<String, Set<String>> index = courses.stream()
            .flatMap(course -> course.modules().stream()
                .flatMap(module -> module.topics().stream())
                .map(topic -> topic.trim().toLowerCase(Locale.ROOT))
                .filter(topic -> !topic.isEmpty())
                .map(topic -> Map.entry(topic, course.code())))
            .collect(Collectors.groupingBy(Map.Entry::getKey, TreeMap::new,
                Collectors.mapping(Map.Entry::getValue, Collectors.toCollection(TreeSet::new))));
        System.out.println("Уникальные темы: " + index.keySet());
        index.forEach((topic, codes) -> System.out.println(topic + " -> " + codes));
    }
}
record Module(List<String> topics) { Module { topics = List.copyOf(topics); } }
record Course(String code, List<Module> modules) { Course { modules = List.copyOf(modules); } }
