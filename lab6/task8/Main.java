package lab6.task8;

import java.time.LocalDate;
import java.util.*;

public class Main {
    static List<LocalDate> moduleStarts(LocalDate start, int count) {
        if (count < 1) { throw new IllegalArgumentException("Нужны модули"); }
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < count; i++) { dates.add(start.plusWeeks(i)); }
        return List.copyOf(dates);
    }
    public static void main(String[] args) {
        System.out.println("Модули начинаются раз в неделю и длятся пять учебных дней. LocalDate не меняется: результат plusDays или plusWeeks нужно сохранять.");
        LocalDate start = LocalDate.of(2024, 1, 29);
        List<LocalDate> modules = moduleStarts(start, 5);
        LocalDate lastDay = modules.get(modules.size() - 1).plusDays(4);
        System.out.println(modules);
        System.out.println("Последний учебный день: " + lastDay);
        System.out.println("Исходная дата: " + start);
        for (LocalDate day = start; !day.isAfter(lastDay); day = day.plusDays(1)) {
            if (day.getMonthValue() == 2 && day.getDayOfMonth() == 29) { System.out.println("В курсе есть високосный день: " + day); }
        }
        System.out.println("Год високосный: " + start.isLeapYear());
    }
}
