package lab5.task11;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("HashMap: каталог по ID, в среднем O(1). TreeSet: рейтинг, O(log n). ArrayDeque: FIFO, амортизированно O(1). LinkedHashSet: уникальные теги по появлению, в среднем O(1). TreeMap: диапазон, O(log n + k). ArrayList: индекс, O(1). Память у всех O(n), у связей и деревьев больше накладные расходы.");
        Map<Integer, String> catalog = new HashMap<>();
        NavigableSet<Integer> leaderboard = new TreeSet<>(Comparator.reverseOrder());
        Queue<String> queue = new ArrayDeque<>();
        Set<String> tags = new LinkedHashSet<>();
        NavigableMap<Integer, String> range = new TreeMap<>();
        List<String> indexed = new ArrayList<>();
        catalog.put(1, "Java");
        leaderboard.addAll(List.of(10, 30, 20));
        queue.offer("Анна");
        tags.addAll(List.of("java", "sql", "java"));
        range.put(10, "A"); range.put(20, "B");
        indexed.add("Java");
        System.out.println(catalog.get(1));
        System.out.println(leaderboard);
        System.out.println(queue.poll());
        System.out.println(tags);
        System.out.println(range.subMap(10, true, 20, false));
        System.out.println(indexed.get(0));
    }
}
