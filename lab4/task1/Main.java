package lab4.task1;

public class Main {
    static void first(boolean fixed) { second(fixed); }
    static void second(boolean fixed) { third(fixed); }
    static void third(boolean fixed) { fourth(fixed); }
    static void fourth(boolean fixed) {
        String hours = fixed ? "40" : "сорок";
        System.out.println(Integer.parseInt(hours));
    }
    public static void main(String[] args) {
        System.out.println("Ошибка NumberFormatException возникает в fourth: сорок нельзя разобрать как int. Путь: main → first → second → third → fourth → parseInt. Исправляем исходное значение.");
        try { first(false); }
        catch (NumberFormatException e) { e.printStackTrace(System.out); }
        first(true);
    }
}
