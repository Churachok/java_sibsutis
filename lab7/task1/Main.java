package lab7.task1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Main {
    static void save(Path path, String catalog) throws IOException { Files.writeString(path, catalog, StandardCharsets.UTF_8); }
    static String read(Path path) throws IOException { return Files.readString(path, StandardCharsets.UTF_8); }
    public static void main(String[] args) throws IOException {
        System.out.println("Ошибка чтения передаётся вызывающему коду. Отсутствующий файл и каталог вместо файла не считаются успешной загрузкой.");
        Path directory = Files.createTempDirectory("lab7-task1-");
        Path path = directory.resolve("courses.txt");
        save(path, "1;Основы Java;40\n2;Базы данных;32\n");
        System.out.print(read(path));
        Path missing = directory.resolve("missing.txt");
        try { read(missing); }
        catch (NoSuchFileException e) { System.out.println("Каталог не загружен: файл отсутствует"); }
        try { read(directory); }
        catch (IOException e) { System.out.println("Каталог не загружен: выбран каталог вместо файла"); }
    }
}
