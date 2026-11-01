package lab5.task14;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("LinkedHashMap с accessOrder хранит порядок обращений. get и put обновляют давность; первый ключ вытесняется. null запрещён, ключи должны иметь стабильный hashCode.");
        LruCache<Integer, String> cache = new LruCache<>(2);
        cache.put(1, "A"); cache.put(2, "B");
        System.out.println(cache.keys());
        cache.get(1);
        cache.put(3, "C");
        System.out.println(cache.keys());
        System.out.println(cache.get(2));
        cache.put(1, "Новый A");
        cache.put(4, "D");
        System.out.println(cache.keys());
        System.out.println(cache.get(1));
        try { cache.put(null, "Ошибка"); }
        catch (NullPointerException e) { System.out.println("null запрещён"); }
    }
}
class LruCache<K, V> {
    private final int capacity;
    private final LinkedHashMap<K, V> values = new LinkedHashMap<>(16, 0.75f, true);
    LruCache(int capacity) {
        if (capacity < 1) { throw new IllegalArgumentException("Ёмкость должна быть положительной"); }
        this.capacity = capacity;
    }
    Optional<V> get(K key) { return Optional.ofNullable(values.get(Objects.requireNonNull(key))); }
    void put(K key, V value) {
        values.put(Objects.requireNonNull(key), Objects.requireNonNull(value));
        if (values.size() > capacity) { values.remove(values.keySet().iterator().next()); }
    }
    List<K> keys() { return List.copyOf(values.keySet()); }
}
