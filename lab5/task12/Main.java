package lab5.task12;

import java.util.*;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        System.out.println("Повторный save заменяет запись по ID. null запрещён. findAll возвращает неизменяемый снимок; Course тоже неизменяем.");
        Repository<Integer, Course> repository = new MemoryRepository<>(Course::id);
        repository.save(new Course(1, "Java"));
        repository.save(new Course(1, "Java 21"));
        List<Course> snapshot = repository.findAll();
        repository.save(new Course(2, "SQL"));
        System.out.println(snapshot);
        System.out.println(repository.findById(1));
        System.out.println(repository.deleteById(2));
        try { snapshot.clear(); }
        catch (UnsupportedOperationException e) { System.out.println("Снимок неизменяемый"); }
        try { repository.save(null); }
        catch (NullPointerException e) { System.out.println("null запрещён"); }
    }
}
record Course(int id, String title) {}
interface Repository<ID, T> {
    void save(T value);
    Optional<T> findById(ID id);
    List<T> findAll();
    boolean deleteById(ID id);
}
class MemoryRepository<ID, T> implements Repository<ID, T> {
    private final Map<ID, T> values = new LinkedHashMap<>();
    private final Function<T, ID> idFunction;
    MemoryRepository(Function<T, ID> idFunction) { this.idFunction = Objects.requireNonNull(idFunction); }
    public void save(T value) {
        Objects.requireNonNull(value);
        ID id = Objects.requireNonNull(idFunction.apply(value));
        values.put(id, value);
    }
    public Optional<T> findById(ID id) { return Optional.ofNullable(values.get(Objects.requireNonNull(id))); }
    public List<T> findAll() { return List.copyOf(values.values()); }
    public boolean deleteById(ID id) { return values.remove(Objects.requireNonNull(id)) != null; }
}
