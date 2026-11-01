package lab4.task5;

public class Main {
    static void example(boolean failUse, boolean failClose) {
        try (TestResource resource = new TestResource(failClose)) {
            resource.use(failUse);
        } catch (IllegalStateException e) {
            System.out.println("Основная ошибка: " + e.getMessage());
            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println("Подавленная: " + suppressed.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("При ошибках use и close основной остаётся ошибка use, а ошибка закрытия попадает в suppressed.");
        example(false, false);
        example(true, false);
        example(true, true);
    }
}
class TestResource implements AutoCloseable {
    private final boolean failClose;
    TestResource(boolean failClose) { this.failClose = failClose; System.out.println("open"); }
    void use(boolean fail) {
        System.out.println("use");
        if (fail) { throw new IllegalStateException("Ошибка use"); }
    }
    public void close() {
        System.out.println("close");
        if (failClose) { throw new IllegalStateException("Ошибка close"); }
    }
}
