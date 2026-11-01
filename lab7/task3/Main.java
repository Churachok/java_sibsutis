package lab7.task3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("При ошибке ресурсы закрываются в обратном порядке: сначала writer, потом reader.");
        Path directory = Files.createTempDirectory("lab7-task3-");
        Path source = directory.resolve("source.txt");
        Files.writeString(source, "Java\nSQL\n", StandardCharsets.UTF_8);
        List<String> closed = new ArrayList<>();
        try (LoggedReader reader = new LoggedReader(source, closed);
             LoggedWriter writer = new LoggedWriter(directory.resolve("partial.txt"), closed)) {
            writer.write(reader.readLine());
            throw new IOException("Сбой в середине обработки");
        } catch (IOException e) { System.out.println("Обработка не завершена: " + e.getMessage()); }
        System.out.println("Порядок закрытия: " + closed);
        if (!closed.equals(List.of("writer", "reader"))) { throw new AssertionError("Ресурсы не закрыты по порядку"); }
    }
}
class LoggedReader extends BufferedReader {
    private final List<String> closed;
    LoggedReader(Path path, List<String> closed) throws IOException { super(Files.newBufferedReader(path, StandardCharsets.UTF_8)); this.closed = closed; }
    @Override
    public void close() throws IOException { try { super.close(); } finally { closed.add("reader"); } }
}
class LoggedWriter extends BufferedWriter {
    private final List<String> closed;
    LoggedWriter(Path path, List<String> closed) throws IOException { super(Files.newBufferedWriter(path, StandardCharsets.UTF_8)); this.closed = closed; }
    @Override
    public void close() throws IOException { try { super.close(); } finally { closed.add("writer"); } }
}
