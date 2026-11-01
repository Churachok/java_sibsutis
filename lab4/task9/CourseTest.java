package lab4.task9;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    @Test
    void rejectsBelowMinimum() { assertThrows(IllegalArgumentException.class, () -> new Course(0)); }
    @Test
    void acceptsMinimum() { assertEquals(1, new Course(1).hours()); }
    @Test
    void acceptsMaximum() { assertEquals(1000, new Course(1000).hours()); }
    @Test
    void rejectsAboveMaximum() { assertThrows(IllegalArgumentException.class, () -> new Course(1001)); }
}
