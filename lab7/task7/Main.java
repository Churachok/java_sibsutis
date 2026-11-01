package lab7.task7;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Main {
    static ImportResult importFile(Path path) throws IOException {
        List<Course> courses = new ArrayList<>();
        List<ImportError> errors = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int number = 0;
            while ((line = reader.readLine()) != null) {
                number++;
                try {
                    String[] fields = line.split(";", -1);
                    if (fields.length != 3) { throw new IllegalArgumentException("Нужно три поля"); }
                    Course course = new Course(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]));
                    if (!ids.add(course.id())) { throw new IllegalArgumentException("Повтор ID"); }
                    courses.add(course);
                } catch (IllegalArgumentException e) { errors.add(new ImportError(number, e.getMessage())); }
            }
        }
        return new ImportResult(errors.isEmpty() ? courses : List.of(), errors);
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Выбран all-or-nothing: при любой ошибке courses пуст. Ошибки содержат номера строк.");
        Path directory = Files.createTempDirectory("lab7-task7-");
        Path path = directory.resolve("import.txt");
        Files.writeString(path, "1;Java;40\n2;SQL;много\n3;Python;32\n1;Повтор;20\n", StandardCharsets.UTF_8);
        ImportResult result = importFile(path);
        System.out.println("Успех: " + result.success());
        System.out.println("Опубликованные курсы: " + result.courses());
        result.errors().forEach(System.out::println);
    }
}
record Course(int id, String title, int hours) {
    Course { if (id < 1 || title.isBlank() || hours < 1) { throw new IllegalArgumentException("Неверные данные курса"); } }
}
record ImportError(int line, String message) {}
record ImportResult(List<Course> courses, List<ImportError> errors) {
    ImportResult {
        courses = List.copyOf(courses); errors = List.copyOf(errors);
        if (!errors.isEmpty() && !courses.isEmpty()) { throw new IllegalArgumentException("Частичный каталог запрещён"); }
    }
    boolean success() { return errors.isEmpty(); }
}
