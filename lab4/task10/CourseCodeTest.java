package lab4.task10;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class CourseCodeTest {
    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource(value = {" java-101 |JAVA-101", "sql-002|SQL-002", "JAVA-999|JAVA-999"}, delimiter = '|', ignoreLeadingAndTrailingWhitespace = false)
    void normalizesCode(String input, String expected) {
        assertEquals(expected, new CourseCode(input).value());
    }
    @ParameterizedTest(name = "{0} -> IllegalArgumentException")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "JAVA-1", "ЯВА-101", "JAVA-101-extra", "JAVA-１２３"})
    void rejectsInvalidCode(String input) {
        assertThrows(IllegalArgumentException.class, () -> new CourseCode(input));
    }
}
