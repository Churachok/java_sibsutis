package lab5.task7;

import java.util.*;
import java.util.function.Function;

public class Main {
    static <T> T firstOrThrow(List<T> list) {
        if (list.isEmpty()) { throw new NoSuchElementException("Пустой список"); }
        return list.get(0);
    }
    static <T> void swap(List<T> list, int first, int second) {
        Objects.checkIndex(first, list.size());
        Objects.checkIndex(second, list.size());
        T value = list.get(first);
        list.set(first, list.get(second));
        list.set(second, value);
    }
    static <T, K> Map<K, T> indexBy(List<T> list, Function<T, K> keyFunction) {
        Map<K, T> result = new LinkedHashMap<>();
        for (T value : list) {
            Objects.requireNonNull(value);
            K key = Objects.requireNonNull(keyFunction.apply(value));
            if (result.putIfAbsent(key, value) != null) { throw new IllegalArgumentException("Повтор ключа: " + key); }
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println("firstOrThrow отклоняет пустой список, swap проверяет оба индекса, indexBy отклоняет повтор ключа.");
        List<String> values = new ArrayList<>(List.of("Java", "SQL"));
        System.out.println(firstOrThrow(values));
        swap(values, 0, 1);
        System.out.println(values);
        System.out.println(indexBy(values, String::length));
        try { indexBy(List.of("Java", "Ruby"), String::length); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
    }
}
