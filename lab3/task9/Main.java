package lab3.task9;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("Сервис знает только EnrollmentPolicy. Новая политика добавляется без изменения сервиса.");
        Student student = new Student(19, Set.of("JAVA-100"));
        Course course = new Course("JAVA-100", 1, 2);
        EnrollmentPolicy[] policies = {new AgePolicy(18), new PrerequisitesPolicy(), new CapacityPolicy()};
        for (EnrollmentPolicy policy : policies) {
            System.out.println(new EnrollmentService(policy).enroll(student, course));
        }
    }
}

record Student(int age, Set<String> completed) {}
record Course(String prerequisite, int enrolled, int capacity) {}
interface EnrollmentPolicy { boolean allowed(Student student, Course course); }
class AgePolicy implements EnrollmentPolicy {
    private final int minAge;
    AgePolicy(int minAge) { this.minAge = minAge; }
    public boolean allowed(Student student, Course course) { return student.age() >= minAge; }
}
class PrerequisitesPolicy implements EnrollmentPolicy {
    public boolean allowed(Student student, Course course) { return student.completed().contains(course.prerequisite()); }
}
class CapacityPolicy implements EnrollmentPolicy {
    public boolean allowed(Student student, Course course) { return course.enrolled() < course.capacity(); }
}
class EnrollmentService {
    private final EnrollmentPolicy policy;
    EnrollmentService(EnrollmentPolicy policy) { this.policy = policy; }
    boolean enroll(Student student, Course course) { return policy.allowed(student, course); }
}
