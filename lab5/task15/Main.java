package lab5.task15;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("LinkedHashMap и LinkedHashSet сохраняют порядок ввода. DFS сначала обходит зависимости, затем курс. Повтор вершины текущего пути означает цикл. Неизвестная вершина отклоняется. Время и память O(V+E).");
        Graph<String> graph = new Graph<>();
        for (String name : List.of("Java", "OOP", "Streams", "SQL")) { graph.addVertex(name); }
        graph.addPrerequisite("OOP", "Java");
        graph.addPrerequisite("Streams", "OOP");
        System.out.println(graph.plan());
        graph.addPrerequisite("Java", "Streams");
        try { graph.plan(); }
        catch (IllegalStateException e) { System.out.println(e.getMessage()); }
        try { graph.addPrerequisite("Web", "Java"); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
    }
}
class Graph<T> {
    private final Map<T, Set<T>> prerequisites = new LinkedHashMap<>();
    void addVertex(T vertex) { prerequisites.putIfAbsent(Objects.requireNonNull(vertex), new LinkedHashSet<>()); }
    void addPrerequisite(T course, T prerequisite) {
        if (!prerequisites.containsKey(course) || !prerequisites.containsKey(prerequisite)) {
            throw new IllegalArgumentException("Неизвестная вершина: " + course + " или " + prerequisite);
        }
        prerequisites.get(course).add(prerequisite);
    }
    List<T> plan() {
        Map<T, Integer> states = new HashMap<>();
        List<T> path = new ArrayList<>();
        List<T> result = new ArrayList<>();
        for (T vertex : prerequisites.keySet()) { visit(vertex, states, path, result); }
        return List.copyOf(result);
    }
    private void visit(T vertex, Map<T, Integer> states, List<T> path, List<T> result) {
        int state = states.getOrDefault(vertex, 0);
        if (state == 2) { return; }
        if (state == 1) {
            List<T> cycle = new ArrayList<>(path.subList(path.indexOf(vertex), path.size()));
            cycle.add(vertex);
            throw new IllegalStateException("Цикл: " + cycle);
        }
        states.put(vertex, 1);
        path.add(vertex);
        for (T prerequisite : prerequisites.get(vertex)) { visit(prerequisite, states, path, result); }
        path.remove(path.size() - 1);
        states.put(vertex, 2);
        result.add(vertex);
    }
}
