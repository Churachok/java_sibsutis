package lab5.task6;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        System.out.println("Result содержит либо значение, либо ошибку. Присвоить Result<String> переменной Result<Integer> компилятор не разрешит.");
        Result<String> name = Result.success("Java");
        Result<Integer> count = Result.success(40);
        Result<Integer> failure = Result.failure("Нет числа");
        String title = name.value();
        int hours = count.value();
        System.out.println(title + " " + hours);
        System.out.println(failure.error());
    }
}
class Result<T> {
    private final T value;
    private final String error;
    private Result(T value, String error) { this.value = value; this.error = error; }
    static <T> Result<T> success(T value) { return new Result<>(Objects.requireNonNull(value), null); }
    static <T> Result<T> failure(String error) {
        if (error == null || error.isBlank()) { throw new IllegalArgumentException("Нужна ошибка"); }
        return new Result<>(null, error);
    }
    boolean isSuccess() { return error == null; }
    T value() {
        if (!isSuccess()) { throw new IllegalStateException("Результат содержит ошибку"); }
        return value;
    }
    String error() {
        if (isSuccess()) { throw new IllegalStateException("Результат успешен"); }
        return error;
    }
}
