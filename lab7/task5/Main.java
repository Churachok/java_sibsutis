package lab7.task5;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    static final int MAGIC = 0x43525345;
    static void write(Path path, List<Course> courses) throws IOException {
        try (DataOutputStream output = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(path)))) {
            output.writeInt(MAGIC);
            output.writeInt(1);
            output.writeInt(courses.size());
            for (Course course : courses) {
                output.writeInt(course.id()); output.writeUTF(course.title()); output.writeInt(course.hours());
            }
        }
    }
    static List<Course> read(Path path) throws IOException {
        try (DataInputStream input = new DataInputStream(new BufferedInputStream(Files.newInputStream(path)))) {
            if (input.readInt() != MAGIC) { throw new IOException("Неверный magic header"); }
            if (input.readInt() != 1) { throw new IOException("Неизвестная версия"); }
            int count = input.readInt();
            if (count < 0 || count > 10000) { throw new IOException("Неверное количество записей"); }
            List<Course> courses = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                int id = input.readInt(); String title = input.readUTF(); int hours = input.readInt();
                if (id < 1 || title.isBlank() || hours < 1) { throw new IOException("Неверная запись " + (i + 1)); }
                courses.add(new Course(id, title, hours));
            }
            if (input.read() != -1) { throw new IOException("Лишние байты после каталога"); }
            return List.copyOf(courses);
        } catch (EOFException e) { throw new IOException("Обрезанный файл", e); }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Формат содержит magic, version, count и записи. Неизвестная версия и обрезанный файл отклоняются, список возвращается только целиком.");
        Path directory = Files.createTempDirectory("lab7-task5-");
        Path valid = directory.resolve("courses.bin");
        List<Course> courses = List.of(new Course(1, "Java", 40), new Course(2, "SQL", 32));
        write(valid, courses);
        System.out.println("Совпадает: " + courses.equals(read(valid)));
        Path unknown = directory.resolve("unknown.bin");
        try (DataOutputStream output = new DataOutputStream(Files.newOutputStream(unknown))) {
            output.writeInt(MAGIC); output.writeInt(99); output.writeInt(0);
        }
        Path truncated = directory.resolve("truncated.bin");
        byte[] data = Files.readAllBytes(valid);
        Files.write(truncated, Arrays.copyOf(data, data.length - 2));
        for (Path path : List.of(unknown, truncated)) {
            try { read(path); throw new AssertionError("Повреждение не обнаружено"); }
            catch (IOException e) { System.out.println(path.getFileName() + ": " + e.getMessage()); }
        }
    }
}
record Course(int id, String title, int hours) {}
