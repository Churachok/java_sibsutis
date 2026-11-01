package lab6.task3;

import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("matches проверяет всю строку. Разрешены заглавные ASCII-буквы и три ASCII-цифры; Unicode-цифры и пробелы отклоняются.");
        String[] inputs = {"JAVA-101", "SQL-002", "java-101", "JAVA-1", " JAVA-101", "JAVA-101 ", "xJAVA-101x", "ЯВА-101", "JAVA-１２３", "JAVA-١٠١"};
        System.out.println("Вход | Допустим | prefix | number");
        for (String input : inputs) {
            try {
                CourseCode code = CourseCode.parse(input);
                System.out.println("[" + input + "] | да | " + code.prefix() + " | " + code.number());
            } catch (IllegalArgumentException e) { System.out.println("[" + input + "] | нет | - | -"); }
        }
    }
}
record CourseCode(String prefix, int number) {
    private static final Pattern PATTERN = Pattern.compile("(?<prefix>[A-Z]{2,10})-(?<number>[0-9]{3})");
    static CourseCode parse(String value) {
        if (value == null) { throw new IllegalArgumentException("Пустой код"); }
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.matches()) { throw new IllegalArgumentException("Неверный код"); }
        return new CourseCode(matcher.group("prefix"), Integer.parseInt(matcher.group("number")));
    }
}
