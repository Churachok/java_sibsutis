package lab7.task6;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Main {
    static void replace(Path target, String text) throws IOException {
        Path absolute = target.toAbsolutePath();
        Path temporary = Files.createTempFile(absolute.getParent(), "courses-", ".tmp");
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(temporary, StandardCharsets.UTF_8)) {
                writer.write(text);
                writer.flush();
            }
            try {
                Files.move(temporary, absolute, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Файл заменён атомарно");
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporary, absolute, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Файл заменён без гарантии атомарности");
            }
        } finally { Files.deleteIfExists(temporary); }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Сначала полностью записываем и закрываем временный файл в том же каталоге. Если атомарный move недоступен, выполняем обычную замену без гарантии атомарности.");
        Path directory = Files.createTempDirectory("lab7-task6-");
        Path target = directory.resolve("courses.txt");
        Files.writeString(target, "Старый каталог", StandardCharsets.UTF_8);
        replace(target, "1;Java;40\n2;SQL;32\n");
        System.out.print(Files.readString(target, StandardCharsets.UTF_8));
    }
}
