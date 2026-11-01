package lab4.task11;

public class Main {
    public static void main(String[] args) {
        System.out.println("Проверка выполняется до изменения счётчика. После отказа прежнее состояние должно сохраниться.");
        Course course = new Course(40);
        course.completeHours(10);
        try { course.completeHours(31); }
        catch (HoursException e) { System.out.println(e.getMessage() + ", запрошено: " + e.requested()); }
        System.out.println("Выполнено: " + course.completed());
    }
}
class Course {
    private final int total;
    private int completed;
    Course(int total) {
        if (total < 1) { throw new IllegalArgumentException("Неверная продолжительность"); }
        this.total = total;
    }
    void completeHours(int hours) {
        if (hours < 0 || hours > total - completed) { throw new HoursException(hours); }
        completed += hours;
    }
    int completed() { return completed; }
}
class HoursException extends IllegalArgumentException {
    private final int requested;
    HoursException(int requested) { super("Недопустимое количество часов"); this.requested = requested; }
    int requested() { return requested; }
}
