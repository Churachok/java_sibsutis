package lab8.task3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Нейтральный элемент суммы — 0: 0+x=x. Сложение ассоциативно в допустимом диапазоне. Identity=1 добавляет лишнее значение в каждой части parallel-потока.");
        List<Integer> durations = List.of(10, 20, 30, 40);
        System.out.println("Правильная сумма: " + durations.stream().reduce(0, Integer::sum));
        System.out.println("Неверная identity=1: " + durations.stream().reduce(1, Integer::sum));
        System.out.println("Та же ошибка в parallel: " + durations.parallelStream().reduce(1, Integer::sum));
        List<Hours> values = List.of(new Hours(10), new Hours(20), new Hours(30));
        Hours total = values.stream().reduce(new Hours(0), Hours::plus);
        System.out.println("Value objects: " + total);
    }
}
record Hours(int value) {
    Hours { if (value < 0) { throw new IllegalArgumentException("Отрицательные часы"); } }
    Hours plus(Hours other) { return new Hours(Math.addExact(value, other.value)); }
}
