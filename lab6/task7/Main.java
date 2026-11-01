package lab6.task7;

import java.util.*;
import java.util.random.RandomGenerator;

public class Main {
    static double[] sample(RandomGenerator random, int size) {
        if (size < 1) { throw new IllegalArgumentException("Нужна непустая выборка"); }
        double[] values = new double[size];
        for (int i = 0; i < size; i++) { values[i] = random.nextDouble(0, 100); }
        return values;
    }
    static SampleStats stats(double[] values) {
        if (values.length == 0) { throw new IllegalArgumentException("Пустая выборка"); }
        double min = values[0], max = values[0], sum = 0;
        for (double value : values) { min = Math.min(min, value); max = Math.max(max, value); sum += value; }
        double average = sum / values.length;
        double squares = 0;
        for (double value : values) { squares += Math.pow(value - average, 2); }
        return new SampleStats(min, max, Math.round(average * 100) / 100.0, Math.sqrt(squares / values.length));
    }
    public static void main(String[] args) {
        System.out.println("Одинаковый Random(42) даёт одинаковую выборку. В обычном запуске источник без фиксированного seed передаётся отдельно.");
        double[] first = sample(new Random(42), 10);
        double[] second = sample(new Random(42), 10);
        System.out.println("Seed воспроизводится: " + Arrays.equals(first, second));
        System.out.println(stats(first));
        System.out.println("Обычный источник: " + stats(sample(RandomGenerator.getDefault(), 10)));
    }
}
record SampleStats(double min, double max, double roundedAverage, double standardDeviation) {}
