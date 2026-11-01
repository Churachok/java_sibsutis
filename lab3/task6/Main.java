package lab3.task6;

public class Main {
    public static void main(String[] args) {
        System.out.println("send(String) перегружает send(Object), поэтому через Sender вызывается базовый метод. Для override нужны те же параметры, доступ не уже public и совместимый результат.");
        Sender wrong = new WrongSender();
        wrong.send("Текст");
        Sender right = new RightSender();
        right.send("Текст");
        System.out.println(right.copy().getClass().getSimpleName());
    }
}

class Sender {
    public void send(Object text) { System.out.println("Базовый метод"); }
    public Sender copy() { return new Sender(); }
}
class WrongSender extends Sender {
    public void send(String text) { System.out.println("Случайная перегрузка"); }
}
class RightSender extends Sender {
    @Override
    public void send(Object text) { System.out.println("Переопределённый метод: " + text); }
    @Override
    public RightSender copy() { return new RightSender(); }
}
