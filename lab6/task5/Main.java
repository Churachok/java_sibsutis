package lab6.task5;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("Конструктор из double сохраняет неточность двоичного числа. Для денежных значений используем строку; valueOf берёт строковое представление double.");
        BigDecimal fromDouble = new BigDecimal(0.1);
        BigDecimal fromValueOf = BigDecimal.valueOf(0.1);
        BigDecimal fromText = new BigDecimal("0.1");
        System.out.println("double: " + fromDouble);
        System.out.println("valueOf: " + fromValueOf);
        System.out.println("Строка: " + fromText);
        System.out.println("Три курса через double: " + fromDouble.multiply(BigDecimal.valueOf(3)));
        System.out.println("Три курса точно: " + fromText.multiply(BigDecimal.valueOf(3)));
        BigDecimal cost = new BigDecimal("199.90").add(new BigDecimal("250.15")).add(new BigDecimal("99.95"));
        System.out.println("Каталог из трёх курсов: " + cost);
    }
}
