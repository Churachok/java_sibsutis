package lab3.task12;

public class Main {
    static String describe(EnrollmentResult result) {
        return switch (result) {
            case Accepted accepted -> "Принят: " + accepted.courseId();
            case Rejected rejected -> "Отказ: " + rejected.reason();
            case WaitListed waiting -> "Очередь: " + waiting.position();
        };
    }
    public static void main(String[] args) {
        System.out.println("Если добавить ещё один разрешённый тип результата, компилятор потребует новую ветку switch, потому что default отсутствует.");
        System.out.println(describe(new Accepted(1)));
        System.out.println(describe(new Rejected("Нет мест")));
        System.out.println(describe(new WaitListed(2)));
    }
}
sealed interface EnrollmentResult permits Accepted, Rejected, WaitListed {}
record Accepted(int courseId) implements EnrollmentResult {}
record Rejected(String reason) implements EnrollmentResult {}
record WaitListed(int position) implements EnrollmentResult {}
