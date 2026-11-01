package lab7.task8;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Main {
    static ImportStats importFile(Path path, int detailLimit, long errorLimit) throws IOException {
        if (detailLimit < 0 || errorLimit < 1) { throw new IllegalArgumentException("Неверные пределы"); }
        long lines = 0, valid = 0, errors = 0, hours = 0;
        boolean cancelled = false;
        List<String> details = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines++;
                try {
                    String[] fields = line.split(";", -1);
                    if (fields.length != 3) { throw new IllegalArgumentException("Нужно три поля"); }
                    int id = Integer.parseInt(fields[0]);
                    int duration = Integer.parseInt(fields[2]);
                    if (id < 1 || fields[1].isBlank() || duration < 1) { throw new IllegalArgumentException("Неверные данные"); }
                    valid++;
                    hours += duration;
                } catch (IllegalArgumentException e) {
                    errors++;
                    if (details.size() < detailLimit) { details.add("Строка " + lines + ": " + e.getMessage()); }
                    if (errors >= errorLimit) { cancelled = true; break; }
                }
            }
        }
        return new ImportStats(lines, valid, errors, hours, cancelled, List.copyOf(details));
    }
    static void generate(Path path, int count, boolean damaged) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (int i = 1; i <= count; i++) {
                writer.write(damaged && i % 3 == 0 ? "bad" : i + ";Курс " + i + ";40");
                writer.write('\n');
            }
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Читаем по одной записи, не храним весь файл. Подробности сохраняем для первых трёх ошибок, на пятой останавливаемся. Для большого примера передайте --large.");
        Path directory = Files.createTempDirectory("lab7-task8-");
        Path large = directory.resolve("large.txt");
        int size = Arrays.asList(args).contains("--large") ? 1000000 : 20000;
        generate(large, size, false);
        System.out.println("Размер файла: " + Files.size(large) + " байт");
        System.out.println(importFile(large, 3, 5));
        Path damaged = directory.resolve("damaged.txt");
        generate(damaged, 100, true);
        System.out.println(importFile(damaged, 3, 5));
    }
}
record ImportStats(long lines, long valid, long errors, long totalHours, boolean cancelled, List<String> details) {}
