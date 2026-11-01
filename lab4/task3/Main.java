package lab4.task3;

public class Main {
    public static void main(String[] args) {
        System.out.println("ID хранятся в полях, но не выводятся в сообщении и toString. Исходная причина сохранена.");
        try { enroll(101, 7); }
        catch (EnrollmentRejectedException e) {
            System.out.println(e);
            System.out.println("Код: " + e.reasonCode());
            System.out.println("Причина сохранена: " + (e.getCause() != null));
        }
    }
    static void enroll(long studentId, long courseId) throws EnrollmentRejectedException {
        try { throw new IllegalStateException("Внутренняя ошибка хранилища"); }
        catch (IllegalStateException e) {
            throw new EnrollmentRejectedException(studentId, courseId, "STORAGE_UNAVAILABLE", e);
        }
    }
}
class EnrollmentRejectedException extends Exception {
    private final long studentId;
    private final long courseId;
    private final String reasonCode;
    EnrollmentRejectedException(long studentId, long courseId, String reasonCode, Throwable cause) {
        super("Запись отклонена: " + reasonCode, cause);
        this.studentId = studentId;
        this.courseId = courseId;
        this.reasonCode = reasonCode;
    }
    long studentId() { return studentId; }
    long courseId() { return courseId; }
    String reasonCode() { return reasonCode; }
    @Override
    public String toString() { return "EnrollmentRejectedException: " + getMessage(); }
}
