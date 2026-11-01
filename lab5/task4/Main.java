package lab5.task4;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("add/remove/element при невозможности операции бросают исключение; offer/poll/peek возвращают false или null. ArrayDeque хранит FIFO и не принимает null.");
        Queue<String> waitlist = new ArrayDeque<>();
        waitlist.offer("Анна");
        waitlist.add("Иван");
        System.out.println("Следующий: " + waitlist.peek());
        while (!waitlist.isEmpty()) { System.out.println("Принят: " + waitlist.poll()); }
        System.out.println("Пустая очередь: " + waitlist.peek() + ", " + waitlist.poll());
        try { waitlist.remove(); }
        catch (NoSuchElementException e) { System.out.println("remove: очередь пуста"); }
        try { waitlist.element(); }
        catch (NoSuchElementException e) { System.out.println("element: очередь пуста"); }
    }
}
