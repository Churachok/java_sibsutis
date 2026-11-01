package lab3.task10;

public class Main {
    public static void main(String[] args) {
        System.out.println("Без override у Both будет конфликт двух default-методов name. Нужно явно выбрать или объединить реализации."); System.out.println(new Both().name()); }
}
interface First { default String name() { return "Первый"; } }
interface Second { default String name() { return "Второй"; } }
class Both implements First, Second {
    @Override
    public String name() { return First.super.name() + " и " + Second.super.name(); }
}
