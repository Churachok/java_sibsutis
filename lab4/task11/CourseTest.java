package lab4.task11;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    @Test
    void failedCompletionKeepsPreviousHours() {
        Course course = new Course(40);
        course.completeHours(10);

        HoursException error = assertThrows(HoursException.class, () -> course.completeHours(31));

        assertEquals("Недопустимое количество часов", error.getMessage());
        assertEquals(31, error.requested());
        assertEquals(10, course.completed());
    }
}
