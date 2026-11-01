package lab5.task13;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("TreeMap и TreeSet дают стабильный порядок и не хранят повторы. Обратная HashMap по курсу помогает удалить его теги. Пустые множества удаляются.");
        TagIndex index = new TagIndex();
        index.add(1, Set.of("java", "backend"));
        index.add(2, Set.of("java"));
        System.out.println(index.find(List.of("java", "backend")));
        index.remove(1);
        System.out.println(index.snapshot());
        System.out.println(index.find(List.of("backend")));
    }
}
class TagIndex {
    private final Map<String, Set<Integer>> byTag = new TreeMap<>();
    private final Map<Integer, Set<String>> byCourse = new HashMap<>();
    void add(int id, Set<String> tags) {
        Set<String> copy = new TreeSet<>(tags);
        remove(id);
        byCourse.put(id, copy);
        for (String tag : copy) { byTag.computeIfAbsent(tag, key -> new TreeSet<>()).add(id); }
    }
    void remove(int id) {
        Set<String> tags = byCourse.remove(id);
        if (tags == null) { return; }
        for (String tag : tags) {
            Set<Integer> ids = byTag.get(tag);
            ids.remove(id);
            if (ids.isEmpty()) { byTag.remove(tag); }
        }
    }
    Set<Integer> find(List<String> tags) {
        if (tags.isEmpty()) { return Collections.unmodifiableSet(new TreeSet<>(byCourse.keySet())); }
        Set<Integer> result = new TreeSet<>(byTag.getOrDefault(tags.get(0), Set.of()));
        for (String tag : tags) { result.retainAll(byTag.getOrDefault(tag, Set.of())); }
        return Collections.unmodifiableSet(result);
    }
    Map<String, Set<Integer>> snapshot() {
        Map<String, Set<Integer>> result = new TreeMap<>();
        byTag.forEach((tag, ids) -> result.put(tag, Collections.unmodifiableSet(new TreeSet<>(ids))));
        return Collections.unmodifiableMap(result);
    }
}
