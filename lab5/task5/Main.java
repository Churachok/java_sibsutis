package lab5.task5;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Нельзя менять список напрямую во время обхода итератором. Используем iterator.remove или removeIf. Полная копия здесь зря расходовала бы память.");
        List<Integer> wrong = new ArrayList<>(List.of(1, 2, 3, 4));
        try {
            for (int value : wrong) { if (value == 1) { wrong.remove(Integer.valueOf(value)); } }
        } catch (ConcurrentModificationException e) { System.out.println(e.getClass().getSimpleName()); }
        List<Integer> first = new ArrayList<>(List.of(1, 2, 3, 4));
        Iterator<Integer> iterator = first.iterator();
        while (iterator.hasNext()) { if (iterator.next() % 2 == 0) { iterator.remove(); } }
        List<Integer> second = new ArrayList<>(List.of(1, 2, 3, 4));
        second.removeIf(value -> value % 2 == 0);
        System.out.println(first);
        System.out.println(second);
    }
}
