package lab4.task8;

public class Main {
    public static void main(String[] args) {
        System.out.println("Тест: создать курс, выполнить 10 часов, проверить счётчик. Эти три действия соответствуют Arrange, Act, Assert.");
        Course course = new Course(40);
        course.completeHours(10);
        System.out.println(course.completedHours());
    }
}
class Course {
    private final int totalHours;
    private int completedHours;
    Course(int totalHours) {
        if (totalHours < 1) { throw new IllegalArgumentException("Нужны положительные часы"); }
        this.totalHours = totalHours;
    }
    void completeHours(int hours) {
        if (hours < 0 || hours > totalHours - completedHours) { throw new IllegalArgumentException("Недопустимые часы"); }
        completedHours += hours;
    }
    int completedHours() { return completedHours; }
}
