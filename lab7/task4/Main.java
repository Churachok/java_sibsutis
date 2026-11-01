package lab7.task4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Stream;

public class Main {
    static Stream<String> wrong(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) { return lines; }
    }
    static long countNonBlank(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            return lines.filter(line -> !line.isBlank()).count();
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Возвращённый из try stream уже закрыт. Терминальная операция должна выполняться до закрытия ресурса; здесь файлом владеет метод.");
        Path directory = Files.createTempDirectory("lab7-task4-");
        Path path = directory.resolve("courses.txt");
        Files.writeString(path, "Java\n\nSQL\n", StandardCharsets.UTF_8);
        try (Stream<String> lines = wrong(path)) { System.out.println(lines.count()); }
        catch (IllegalStateException e) { System.out.println("Ошибка: возвращён закрытый stream"); }
        System.out.println("Непустых строк: " + countNonBlank(path));
    }
}
