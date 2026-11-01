package lab3.task7;

public class Main {
    public static void main(String[] args) {
        System.out.println("Внешний декоратор охватывает работу внутреннего. Metrics снаружи Logging измеряет ещё и логирование; Metrics внутри Retry считает каждую попытку.");
        System.out.println("Logging снаружи:");
        new LoggingSender(new MetricsSender(new EmailSender())).send("Привет");
        System.out.println("Metrics снаружи:");
        new MetricsSender(new LoggingSender(new EmailSender())).send("Привет");
        System.out.println("Повтор с метриками каждой попытки:");
        new LoggingSender(new RetrySender(new MetricsSender(new UnstableEmailSender()), 2)).send("Повторное сообщение");
    }
}

interface NotificationSender { void send(String text); }
class EmailSender implements NotificationSender {
    public void send(String text) { System.out.println("Email: " + text); }
}
class LoggingSender implements NotificationSender {
    private final NotificationSender next;
    LoggingSender(NotificationSender next) { this.next = next; }
    public void send(String text) {
        System.out.println("Начало логирования");
        try { next.send(text); }
        finally { System.out.println("Конец логирования"); }
    }
}
class MetricsSender implements NotificationSender {
    private final NotificationSender next;
    private int calls;
    MetricsSender(NotificationSender next) { this.next = next; }
    public void send(String text) {
        calls++;
        System.out.println("Начало измерения, вызов " + calls);
        long start = System.nanoTime();
        try { next.send(text); }
        finally { System.out.println("Время: " + (System.nanoTime() - start) + " нс"); }
    }
}
class RetrySender implements NotificationSender {
    private final NotificationSender next;
    private final int attempts;
    RetrySender(NotificationSender next, int attempts) {
        if (attempts < 1) { throw new IllegalArgumentException("Нужна хотя бы одна попытка"); }
        this.next = next; this.attempts = attempts;
    }
    public void send(String text) {
        for (int attempt = 1; attempt <= attempts; attempt++) {
            try { next.send(text); return; }
            catch (IllegalStateException e) {
                if (attempt == attempts) { throw e; }
                System.out.println("Повтор после временного сбоя");
            }
        }
    }
}
class UnstableEmailSender implements NotificationSender {
    private boolean first = true;
    public void send(String text) {
        if (first) { first = false; throw new IllegalStateException("Временный сбой отправки"); }
        System.out.println("Email: " + text);
    }
}
