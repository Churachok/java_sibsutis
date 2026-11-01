package lab6.task2;

public class Main {
    static String withPlus(int count) {
        String result = "";
        for (int i = 1; i <= count; i++) { result += "Курс " + i + "\n"; }
        return result;
    }
    static String withBuilder(int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= count; i++) { result.append("Курс ").append(i).append('\n'); }
        return result.toString();
    }
    public static void main(String[] args) {
        System.out.println("String неизменяем: плюс создаёт новый результат. StringBuilder накапливает текст в буфере. Это один учебный замер, не JMH.");
        long start = System.nanoTime();
        String first = withPlus(10000);
        long middle = System.nanoTime();
        String second = withBuilder(10000);
        long finish = System.nanoTime();
        System.out.println("Результаты совпали: " + first.equals(second));
        System.out.println("Символов: " + first.length());
        System.out.println("Плюс: " + (middle - start) + " нс");
        System.out.println("Builder: " + (finish - middle) + " нс");
    }
}
