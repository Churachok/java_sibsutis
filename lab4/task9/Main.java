package lab4.task9;

public class Main {
    public static void main(String[] args) {
        System.out.println("Границы примера: 1–1000 часов. Проверяем 0, 1, 1000, 1001: обычное значение не обнаружит ошибку на границе.");
        for (int hours : new int[]{0, 1, 1000, 1001}) {
            try { System.out.println(new Course(hours).hours()); }
            catch (IllegalArgumentException e) { System.out.println(hours + ": " + e.getMessage()); }
        }
    }
}
record Course(int hours) {
    Course {
        if (hours < 1 || hours > 1000) { throw new IllegalArgumentException("Часы должны быть от 1 до 1000"); }
    }
}
