package lab7.task2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Считаем char без разделителей строк. Байты зависят от кодировки: русская буква обычно занимает два байта UTF-8 и один char; некоторые символы занимают два char.");
        Path directory = Files.createTempDirectory("lab7-task2-");
        Path source = directory.resolve("source.txt");
        Path target = directory.resolve("copy.txt");
        Files.writeString(source, "Java\nПривет\n", StandardCharsets.UTF_8);
        int lines = 0, characters = 0;
        try (BufferedReader reader = Files.newBufferedReader(source, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines++;
                characters += line.length();
                writer.write(line);
                writer.newLine();
            }
        }
        System.out.println("Строк: " + lines + ", char без разделителей: " + characters);
        System.out.println("Байт исходного файла: " + Files.size(source));
        System.out.print(Files.readString(target, StandardCharsets.UTF_8));
    }
}
