package lab4.task8;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseTest {
    @Test
    void completeHoursAddsPassedHours() {
        Course course = new Course(40);

        course.completeHours(10);

        assertEquals(10, course.completedHours());
    }
}
