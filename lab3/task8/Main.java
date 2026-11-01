package lab3.task8;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Read-only объект не должен обещать save и всегда бросать исключение. Поэтому чтение и запись разделены на интерфейсы.");
        CourseRepository repository = new MemoryCourseRepository();
        repository.save(new Course(1, "Java"));
        CourseReader reader = new ReadOnlyCourseRepository(repository);
        System.out.println(reader.findById(1));
    }
}

record Course(int id, String title) {}
interface CourseReader { Optional<Course> findById(int id); }
interface CourseRepository extends CourseReader { void save(Course course); }
class MemoryCourseRepository implements CourseRepository {
    private final Map<Integer, Course> courses = new HashMap<>();
    public Optional<Course> findById(int id) { return Optional.ofNullable(courses.get(id)); }
    public void save(Course course) { courses.put(course.id(), course); }
}
class ReadOnlyCourseRepository implements CourseReader {
    private final CourseReader source;
    ReadOnlyCourseRepository(CourseReader source) { this.source = source; }
    public Optional<Course> findById(int id) { return source.findById(id); }
}
