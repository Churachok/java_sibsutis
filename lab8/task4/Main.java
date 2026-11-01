package lab8.task4;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println("groupingBy с summarizingInt считает количество, сумму и среднее за один проход курсов. Потом обходим только готовые группы.");
        List<Course> courses = List.of(new Course("Java", Level.BEGINNER, 40),
            new Course("SQL", Level.BEGINNER, 20), new Course("Streams", Level.ADVANCED, 30));
        Map<Level, IntSummaryStatistics> grouped = courses.stream().collect(
            Collectors.groupingBy(Course::level, () -> new EnumMap<>(Level.class), Collectors.summarizingInt(Course::duration)));
        List<LevelSummary> report = grouped.entrySet().stream().map(entry -> {
            IntSummaryStatistics stats = entry.getValue();
            return new LevelSummary(entry.getKey(), stats.getCount(), stats.getSum(), stats.getAverage());
        }).toList();
        report.forEach(System.out::println);
    }
}
enum Level { BEGINNER, INTERMEDIATE, ADVANCED }
record Course(String title, Level level, int duration) {}
record LevelSummary(Level level, long count, long totalDuration, double averageDuration) {}
