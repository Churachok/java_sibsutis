package lab3.task2;

public class Main {
    public static void main(String[] args) {
        System.out.println("Ожидаются Email String, Email String, Console String, Базовый Object, Базовый Object. Перегрузка выбирается по типам при компиляции, переопределение — по объекту при запуске.");
        NotificationSender first = new EmailSender();
        EmailSender second = new EmailSender();
        NotificationSender third = new ConsoleSender();
        first.send("Привет");
        second.send("Привет");
        third.send("Привет");
        Object text = "Привет";
        first.send(text);
        second.send(text);
    }
}

class NotificationSender {
    void send(Object text) { System.out.println("Базовый Object: " + text); }
    void send(String text) { System.out.println("Базовый String: " + text); }
}

class EmailSender extends NotificationSender {
    @Override
    void send(String text) { System.out.println("Email String: " + text); }
}

class ConsoleSender extends NotificationSender {
    @Override
    void send(String text) { System.out.println("Console String: " + text); }
}
