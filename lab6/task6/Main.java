package lab6.task6;

import java.math.*;
import java.util.*;

public class Main {
    static List<BigDecimal> allocate(List<BigDecimal> prices, BigDecimal discount, RoundingMode mode) {
        Objects.requireNonNull(mode);
        List<BigDecimal> amounts = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO.setScale(2);
        for (BigDecimal price : prices) {
            BigDecimal amount = price.setScale(2, RoundingMode.UNNECESSARY);
            if (amount.signum() < 0) { throw new IllegalArgumentException("Отрицательная цена"); }
            amounts.add(amount);
            total = total.add(amount);
        }
        BigDecimal target = discount.setScale(2, mode);
        if (target.signum() < 0 || target.compareTo(total) > 0) { throw new IllegalArgumentException("Неверная скидка"); }
        List<BigDecimal> result = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO.setScale(2);
        for (BigDecimal amount : amounts) {
            BigDecimal part = total.signum() == 0 ? BigDecimal.ZERO.setScale(2) : target.multiply(amount).divide(total, 2, mode);
            result.add(part);
            sum = sum.add(part);
        }
        int cents = target.subtract(sum).movePointRight(2).intValueExact();
        for (int i = 0; cents != 0; i = (i + 1) % amounts.size()) {
            int step = Integer.signum(cents);
            BigDecimal adjusted = result.get(i).add(BigDecimal.valueOf(step, 2));
            if (adjusted.signum() >= 0 && adjusted.compareTo(amounts.get(i)) <= 0) {
                result.set(i, adjusted);
                cents -= step;
            }
        }
        return List.copyOf(result);
    }
    public static void main(String[] args) {
        System.out.println("Скидка делится пропорционально ценам. Округление задаётся параметром, оставшиеся копейки распределяются по порядку позиций.");
        List<BigDecimal> prices = List.of(new BigDecimal("1.00"), new BigDecimal("1.00"), new BigDecimal("1.00"));
        for (String discount : List.of("0.01", "0.02", "1.00")) {
            List<BigDecimal> parts = allocate(prices, new BigDecimal(discount), RoundingMode.HALF_UP);
            BigDecimal sum = parts.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            System.out.println(parts + ", сумма " + sum + ", ожидалось " + discount);
        }
    }
}
