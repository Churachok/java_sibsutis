package lab4.task2;

public class Main {
    public static void main(String[] args) {
        String[] answers = {
            "1. Отрицательное число часов: unchecked IllegalArgumentException, ошибка вызывающего кода",
            "2. Файл импорта отсутствует: checked IOException, пользователь может выбрать другой",
            "3. Завершение уже закрытого курса: unchecked IllegalStateException, нарушено состояние",
            "4. Сетевая операция не удалась: checked IOException, можно повторить позже",
            "5. В обязательной конфигурации нет адреса: unchecked IllegalStateException, запуск прекращается",
            "6. Не хватает памяти: OutOfMemoryError, обычное восстановление не обещаем",
            "7. Курс по ID не найден: Optional.empty, ожидаемое отсутствие",
            "8. Нет мест на курсе: типизированный Rejected, обычный бизнес-результат",
            "9. Неверная дата в анкете: типизированная ошибка поля, пользователь исправит ввод",
            "10. Нет доступа к выбранному файлу: checked AccessDeniedException, можно выбрать другой"
        };
        for (String answer : answers) { System.out.println(answer); }
    }
}
