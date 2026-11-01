package lab6.task4;

import java.util.Objects;

public class Main {
    static int requireHours(Integer value) {
        if (value == null) { throw new IllegalArgumentException("Часы обязательны"); }
        return value;
    }
    public static void main(String[] args) {
        System.out.println("Unboxing null вызывает NPE. Исправленный метод явно требует непустое значение. Integer сравниваем по equals, независимо от кеша объектов.");
        Integer missing = null;
        try {
            int broken = missing;
            System.out.println(broken);
        } catch (NullPointerException e) { System.out.println("NPE при unboxing"); }
        Integer small1 = 100, small2 = 100;
        Integer big1 = 1000, big2 = 1000;
        System.out.println("100: == " + (small1 == small2) + ", equals " + Objects.equals(small1, small2));
        System.out.println("1000: == " + (big1 == big2) + ", equals " + Objects.equals(big1, big2));
        try { requireHours(null); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        System.out.println(requireHours(40));
    }
}
