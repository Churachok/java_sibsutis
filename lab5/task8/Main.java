package lab5.task8;

import java.util.*;

public class Main {
    static <T extends Comparable<? super T>> T max(List<T> values) {
        if (values.isEmpty()) { throw new NoSuchElementException("Пустой список"); }
        T result = values.get(0);
        for (T value : values) { if (value.compareTo(result) > 0) { result = value; } }
        return result;
    }
    public static void main(String[] args) {
        System.out.println("Comparable<? super T> принимает сравнение с типом T или его предком. Поэтому подходит и SpecialScore, который сравнивается как BaseScore.");
        System.out.println(max(List.of(2, 8, 1)));
        System.out.println(max(List.of("Java", "SQL")));
        System.out.println(max(List.of(new Score(10), new Score(20))));
        System.out.println(max(List.of(new SpecialScore(3), new SpecialScore(7))).points);
    }
}
record Score(int points) implements Comparable<Score> {
    public int compareTo(Score other) { return Integer.compare(points, other.points); }
}
class BaseScore implements Comparable<BaseScore> {
    final int points;
    BaseScore(int points) { this.points = points; }
    public int compareTo(BaseScore other) { return Integer.compare(points, other.points); }
}
class SpecialScore extends BaseScore {
    SpecialScore(int points) { super(points); }
}
