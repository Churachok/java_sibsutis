package lab8.task6;

import java.util.*;
import java.util.stream.*;

public class Main {
    static Collector<Course, Accumulator, Stats> statistics() {
        return Collector.of(Accumulator::new, Accumulator::add, Accumulator::merge, Accumulator::finish);
    }
    static Stats standard(List<Course> courses) {
        IntSummaryStatistics stats = courses.stream().collect(Collectors.summarizingInt(Course::duration));
        return new Stats(stats.getCount(), stats.getSum(),
            stats.getCount() == 0 ? OptionalInt.empty() : OptionalInt.of(stats.getMin()),
            stats.getCount() == 0 ? OptionalInt.empty() : OptionalInt.of(stats.getMax()));
    }
    static void compare(List<Course> courses) {
        Stats sequential = courses.stream().collect(statistics());
        Stats parallel = courses.parallelStream().collect(statistics());
        Stats ready = standard(courses);
        System.out.println(sequential);
        System.out.println("Parallel и готовый collector совпали: " + (sequential.equals(parallel) && sequential.equals(ready)));
        if (!sequential.equals(parallel) || !sequential.equals(ready)) { throw new AssertionError("Неверная статистика"); }
    }
    public static void main(String[] args) {
        System.out.println("Каждая часть потока получает свой аккумулятор. Combiner складывает count/sum и объединяет min/max, finisher возвращает неизменяемый Stats.");
        compare(IntStream.rangeClosed(1, 1000).mapToObj(value -> new Course(value)).toList());
        compare(List.of());
    }
}
record Course(int duration) {}
record Stats(long count, long sum, OptionalInt min, OptionalInt max) {}
class Accumulator {
    private long count, sum;
    private int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    void add(Course course) {
        count++; sum += course.duration();
        min = Math.min(min, course.duration()); max = Math.max(max, course.duration());
    }
    Accumulator merge(Accumulator other) {
        count += other.count; sum += other.sum;
        min = Math.min(min, other.min); max = Math.max(max, other.max);
        return this;
    }
    Stats finish() {
        return new Stats(count, sum, count == 0 ? OptionalInt.empty() : OptionalInt.of(min), count == 0 ? OptionalInt.empty() : OptionalInt.of(max));
    }
}
