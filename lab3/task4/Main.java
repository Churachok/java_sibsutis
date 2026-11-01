package lab3.task4;

public class Main {
    public static void main(String[] args) {
        System.out.println("final у send не даёт пропустить проверку адреса. Наследники меняют только doSend.");
        AbstractNotificationSender email = new EmailSender();
        email.send("student@example.com", "Курс начался");
        new ConsoleSender().send("console", "Курс начался");
        try {
            email.send(" ", "Текст");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}

abstract class AbstractNotificationSender {
    public final void send(String address, String text) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Пустой адрес");
        }
        doSend(address, text);
    }
    protected abstract void doSend(String address, String text);
}
class EmailSender extends AbstractNotificationSender {
    @Override
    protected void doSend(String address, String text) { System.out.println("Email " + address + ": " + text); }
}
class ConsoleSender extends AbstractNotificationSender {
    @Override
    protected void doSend(String address, String text) { System.out.println(address + ": " + text); }
}
