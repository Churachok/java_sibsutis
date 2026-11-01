package lab5.task1;

import java.util.*;

public class Main {
    static long byIndex(List<Integer> list) {
        long sum = 0;
        for (int i = 0; i < list.size(); i++) { sum += list.get(i); }
        return sum;
    }
    static long byIterator(List<Integer> list) {
        long sum = 0;
        for (int value : list) { sum += value; }
        return sum;
    }
    static void measure(List<Integer> list) {
        long start = System.nanoTime();
        long sum1 = byIndex(list);
        long middle = System.nanoTime();
        long sum2 = byIterator(list);
        long end = System.nanoTime();
        System.out.println(list.getClass().getSimpleName() + ": индекс " + (middle - start) + " нс, обход " + (end - middle) + " нс, суммы " + sum1 + " " + sum2);
    }
    public static void main(String[] args) {
        System.out.println("ArrayList: get O(1), вставка в середину O(n). LinkedList: get O(n). Последовательный обход обеих коллекций O(n). Здесь выбираю ArrayList; замер учебный, без выводов о микробенчмарке.");
        List<String> registrations = new ArrayList<>(List.of("Анна", "Иван", "Анна"));
        registrations.add(1, "Олег");
        registrations.remove("Иван");
        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new LinkedHashSet<>();
        for (String name : registrations) { if (!seen.add(name)) { duplicates.add(name); } }
        System.out.println(registrations);
        System.out.println("Повторы: " + duplicates);
        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < 5000; i++) { array.add(i); }
        measure(array);
        measure(new LinkedList<>(array));
    }
}
