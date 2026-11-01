package lab4.task4;

public class Main {
    static int example(boolean fail) {
        try {
            System.out.println("try");
            if (fail) { throw new IllegalArgumentException("Ошибка"); }
            return 1;
        } catch (IllegalArgumentException e) {
            System.out.println("catch");
            return 2;
        } finally {
            System.out.println("finally");
        }
    }
    static void uncaught() {
        try {
            System.out.println("try с throw");
            throw new IllegalStateException("Не перехвачено внутри");
        } finally {
            System.out.println("finally перед выходом с ошибкой");
        }
    }
    public static void main(String[] args) {
        System.out.println("Ожидается: try → finally → 1; try → catch → finally → 2. Return из finally мог бы заменить результат или скрыть исключение.");
        System.out.println(example(false));
        System.out.println(example(true));
        try { uncaught(); }
        catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }
}
