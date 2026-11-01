package lab4.task6;

public class Main {
    static int parse(String text) { return Integer.parseInt(text); }
    static int importRecord(int number, String text) throws CourseImportException {
        try { return parse(text); }
        catch (NumberFormatException e) { throw new CourseImportException(number, e); }
    }
    public static void main(String[] args) throws CourseImportException {
        System.out.println("Parser разбирает число. Уровень импорта добавляет номер записи и сохраняет исходную причину.");
        System.out.println(importRecord(1, "40"));
        try { importRecord(2, "сорок"); }
        catch (CourseImportException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause().getClass().getSimpleName());
        }
    }
}
class CourseImportException extends Exception {
    private final int recordNumber;
    CourseImportException(int recordNumber, NumberFormatException cause) {
        super("Не удалось прочитать число в записи " + recordNumber, cause);
        this.recordNumber = recordNumber;
    }
    int recordNumber() { return recordNumber; }
}
