package lab8.task7;

import java.math.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    static Report report(Stream<Event> events, Map<String, Course> catalog) {
        Builder builder = events.collect(() -> new Builder(catalog), Builder::add, Builder::merge);
        return builder.finish();
    }
    static Stream<Event> sample(long count) {
        Stream<Event> initial = Stream.of(new Event("Анна", "JAVA-101", Kind.ENROLLED),
            new Event("Анна", "JAVA-101", Kind.COMPLETED), new Event("Анна", "JAVA-101", Kind.COMPLETED),
            new Event("Иван", "SQL-101", Kind.COMPLETED), new Event("Олег", "UNKNOWN", Kind.ENROLLED),
            new Event("Мария", "JAVA-101", Kind.ENROLLED), new Event("Пётр", "JAVA-301", Kind.ENROLLED),
            new Event("", "JAVA-101", Kind.ENROLLED));
        Stream<Event> generated = LongStream.range(0, count).mapToObj(i -> {
            String code = i / 2 % 3 == 0 ? "JAVA-101" : i / 2 % 3 == 1 ? "SQL-101" : "JAVA-301";
            return new Event("S" + (i / 2 % 10000), code, i % 2 == 0 ? Kind.ENROLLED : Kind.COMPLETED);
        });
        return Stream.concat(initial, generated);
    }
    public static void main(String[] args) {
        System.out.println("События читаются один раз. Считаем уникальные пары студент/курс, повторы не увеличивают completion rate. Top-3: доля завершений, затем число завершивших, затем код. Храним студентов, пары и до 10 описаний ошибок. Stream удобен для collect и сортировки, цикл — для проверки событий и нескольких счётчиков. Внешние данные не изменяются.");
        Map<String, Course> catalog = Map.of("JAVA-101", new Course("JAVA-101", Level.BEGINNER),
            "SQL-101", new Course("SQL-101", Level.BEGINNER), "JAVA-301", new Course("JAVA-301", Level.ADVANCED));
        Report result;
        try (Stream<Event> events = sample(100000)) { result = report(events, catalog); }
        System.out.println("Уникальных студентов: " + result.students().size());
        System.out.println("Первые имена: " + result.students().stream().limit(5).toList());
        System.out.println("Курсы: " + result.courses());
        System.out.println("Top-3 по completion rate: " + result.top3());
        System.out.println("Регистрации по level: " + result.byLevel());
        System.out.println("Аномалий: " + result.anomalyCount() + ", первые по тексту: " + result.anomalies());
        List<Event> small = sample(20).toList();
        List<Event> reverse = new ArrayList<>(small); Collections.reverse(reverse);
        Report first = report(small.stream(), catalog);
        Report second = report(reverse.stream(), catalog);
        Report parallel = report(small.parallelStream(), catalog);
        System.out.println("Порядок и разбиение не влияют: " + (first.equals(second) && first.equals(parallel)));
        if (!first.equals(second) || !first.equals(parallel)) { throw new AssertionError("Отчёт недетерминирован"); }
    }
}
enum Level { BEGINNER, INTERMEDIATE, ADVANCED }
enum Kind { ENROLLED, COMPLETED }
record Course(String code, Level level) {}
record Event(String studentId, String courseCode, Kind kind) {}
record EnrollmentKey(String studentId, String courseCode) {}
record CourseStats(String code, long enrolled, long completed) {
    BigDecimal rate() {
        return enrolled == 0 ? BigDecimal.ZERO.setScale(4) : BigDecimal.valueOf(completed).divide(BigDecimal.valueOf(enrolled), 4, RoundingMode.HALF_UP);
    }
    @Override
    public String toString() { return code + " " + completed + "/" + enrolled + " = " + rate(); }
}
record Report(Set<String> students, List<CourseStats> courses, List<CourseStats> top3,
              Map<Level, Long> byLevel, long anomalyCount, List<String> anomalies) {
    Report {
        students = Collections.unmodifiableSet(new TreeSet<>(students));
        courses = List.copyOf(courses); top3 = List.copyOf(top3);
        EnumMap<Level, Long> levels = new EnumMap<>(Level.class); levels.putAll(byLevel);
        byLevel = Collections.unmodifiableMap(levels); anomalies = List.copyOf(anomalies);
    }
}
class Counts {
    long enrolled, completed;
    void merge(Counts other) { enrolled += other.enrolled; completed += other.completed; }
}
class Builder {
    private final Map<String, Course> catalog;
    private final Set<String> students = new TreeSet<>();
    private final Map<EnrollmentKey, Counts> states = new HashMap<>();
    private final TreeSet<String> anomalies = new TreeSet<>();
    private long anomalyCount;
    Builder(Map<String, Course> catalog) { this.catalog = Map.copyOf(catalog); }
    private void anomaly(String message, long count) {
        anomalyCount += count;
        anomalies.add(message);
        if (anomalies.size() > 10) { anomalies.pollLast(); }
    }
    void add(Event event) {
        if (event == null || event.studentId() == null || event.studentId().isBlank() || event.courseCode() == null || event.kind() == null) {
            anomaly("Некорректное событие", 1); return;
        }
        if (!catalog.containsKey(event.courseCode())) { anomaly("Неизвестный курс: " + event.courseCode(), 1); return; }
        students.add(event.studentId());
        Counts counts = states.computeIfAbsent(new EnrollmentKey(event.studentId(), event.courseCode()), key -> new Counts());
        if (event.kind() == Kind.ENROLLED) { counts.enrolled++; } else { counts.completed++; }
    }
    void merge(Builder other) {
        students.addAll(other.students);
        other.states.forEach((key, value) -> states.computeIfAbsent(key, ignored -> new Counts()).merge(value));
        anomalyCount += other.anomalyCount;
        anomalies.addAll(other.anomalies);
        while (anomalies.size() > 10) { anomalies.pollLast(); }
    }
    Report finish() {
        Map<String, long[]> totals = new TreeMap<>();
        for (String code : catalog.keySet()) { totals.put(code, new long[2]); }
        Map<Level, Long> levels = new EnumMap<>(Level.class);
        for (Level level : Level.values()) { levels.put(level, 0L); }
        for (Map.Entry<EnrollmentKey, Counts> entry : states.entrySet()) {
            EnrollmentKey key = entry.getKey(); Counts counts = entry.getValue();
            long[] total = totals.get(key.courseCode());
            if (counts.enrolled > 0) {
                total[0]++;
                if (counts.completed > 0) { total[1]++; }
                levels.merge(catalog.get(key.courseCode()).level(), 1L, Long::sum);
            } else if (counts.completed > 0) { anomaly("Завершение без регистрации: " + key, 1); }
            if (counts.enrolled > 1) { anomaly("Повтор регистрации: " + key, counts.enrolled - 1); }
            if (counts.completed > 1) { anomaly("Повтор завершения: " + key, counts.completed - 1); }
        }
        List<CourseStats> courses = totals.entrySet().stream()
            .map(entry -> new CourseStats(entry.getKey(), entry.getValue()[0], entry.getValue()[1])).toList();
        Comparator<CourseStats> ranking = (first, second) -> {
            BigDecimal left = BigDecimal.valueOf(first.completed()).multiply(BigDecimal.valueOf(Math.max(1, second.enrolled())));
            BigDecimal right = BigDecimal.valueOf(second.completed()).multiply(BigDecimal.valueOf(Math.max(1, first.enrolled())));
            int rateComparison = right.compareTo(left);
            if (rateComparison != 0) { return rateComparison; }
            int completedComparison = Long.compare(second.completed(), first.completed());
            return completedComparison != 0 ? completedComparison : first.code().compareTo(second.code());
        };
        List<CourseStats> top = courses.stream().sorted(ranking).limit(3).toList();
        return new Report(students, courses, top, levels, anomalyCount, new ArrayList<>(anomalies));
    }
}
