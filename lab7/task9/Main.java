package lab7.task9;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.CRC32;

public class Main {
    static final int MAGIC = 0x43485542;
    static void write(Path path, int version, List<Course> courses) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (DataOutputStream payload = new DataOutputStream(bytes)) {
            payload.writeInt(courses.size());
            for (Course course : courses) {
                payload.writeInt(course.id()); payload.writeUTF(course.title());
                if (version >= 2) { payload.writeInt(course.hours()); }
            }
        }
        byte[] data = bytes.toByteArray();
        CRC32 checksum = new CRC32(); checksum.update(data);
        try (DataOutputStream output = new DataOutputStream(Files.newOutputStream(path))) {
            output.writeInt(MAGIC); output.writeInt(version); output.writeInt(data.length);
            output.writeLong(checksum.getValue()); output.write(data);
        }
    }
    static List<Course> read(Path path) throws IOException {
        try (DataInputStream input = new DataInputStream(Files.newInputStream(path))) {
            if (input.readInt() != MAGIC) { throw new IOException("Неверный magic"); }
            int version = input.readInt();
            if (version != 1 && version != 2) { throw new IOException("Неизвестная schema version: " + version); }
            int length = input.readInt();
            if (length < 4 || length > 1000000) { throw new IOException("Недопустимый размер payload"); }
            long expected = input.readLong();
            byte[] data = input.readNBytes(length);
            if (data.length != length) { throw new EOFException("Обрезанный payload"); }
            if (input.read() != -1) { throw new IOException("Лишние байты"); }
            CRC32 checksum = new CRC32(); checksum.update(data);
            if (checksum.getValue() != expected) { throw new IOException("Checksum не совпала"); }
            List<Course> courses = new ArrayList<>();
            Set<Integer> ids = new HashSet<>();
            try (DataInputStream payload = new DataInputStream(new ByteArrayInputStream(data))) {
                int count = payload.readInt();
                if (count < 0 || count > 10000) { throw new IOException("Неверный count"); }
                for (int i = 0; i < count; i++) {
                    int id = payload.readInt(); String title = payload.readUTF();
                    int hours = version == 1 ? 40 : payload.readInt();
                    if (id < 1 || title.isBlank() || hours < 1 || !ids.add(id)) { throw new IOException("Неверная запись"); }
                    courses.add(new Course(id, title, hours));
                }
                if (payload.read() != -1) { throw new IOException("Лишние данные payload"); }
            }
            return List.copyOf(courses);
        } catch (EOFException e) { throw new IOException("Обрезанный файл", e); }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("CRC32 проверяется до публикации. Миграция v1 добавляет 40 часов; v2 хранит часы явно. Будущая версия отклоняется.");
        Path directory = Files.createTempDirectory("lab7-task9-");
        List<Course> original = List.of(new Course(1, "Java", 64));
        Path v1 = directory.resolve("v1.bin"); Path v2 = directory.resolve("v2.bin");
        write(v1, 1, original); write(v2, 2, original);
        System.out.println("Миграция v1, часы по умолчанию: " + read(v1));
        System.out.println("Чтение v2: " + read(v2));
        Path future = directory.resolve("future.bin"); write(future, 3, original);
        Path damaged = directory.resolve("damaged.bin");
        byte[] data = Files.readAllBytes(v2); data[data.length - 1] ^= 1; Files.write(damaged, data);
        for (Path path : List.of(future, damaged)) {
            try { read(path); throw new AssertionError("Ошибка не обнаружена"); }
            catch (IOException e) { System.out.println(path.getFileName() + ": " + e.getMessage()); }
        }
    }
}
record Course(int id, String title, int hours) {}
