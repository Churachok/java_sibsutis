package lab3.task11;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        System.out.println("В первом сравнении нарушена симметрия. getClass исправляет её, а композиция убирает наследование. final value class проще: наследник не изменит правила равенства.");
        Point point = new Point(1, 2);
        Point colored = new ColoredPoint(1, 2, "red");
        System.out.println(point.equals(colored) + " " + colored.equals(point));
        StrictPoint strict = new StrictPoint(1, 2);
        StrictPoint strictColored = new StrictColoredPoint(1, 2, "red");
        System.out.println(strict.equals(strictColored) + " " + strictColored.equals(strict));
        ValuePoint value = new ValuePoint(1, 2);
        ColorValue colorValue = new ColorValue(value, "red");
        System.out.println(value.equals(colorValue) + " " + colorValue.equals(value));
        System.out.println(colorValue.equals(new ColorValue(new ValuePoint(1, 2), "red")));
    }
}

class Point {
    final int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }
    @Override
    public boolean equals(Object other) { return other instanceof Point p && x == p.x && y == p.y; }
    @Override
    public int hashCode() { return Objects.hash(x, y); }
}
class ColoredPoint extends Point {
    final String color;
    ColoredPoint(int x, int y, String color) { super(x, y); this.color = color; }
    @Override
    public boolean equals(Object other) {
        return other instanceof ColoredPoint p && super.equals(p) && color.equals(p.color);
    }
    @Override
    public int hashCode() { return Objects.hash(x, y, color); }
}
class StrictPoint {
    final int x, y;
    StrictPoint(int x, int y) { this.x = x; this.y = y; }
    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) { return false; }
        StrictPoint p = (StrictPoint) other;
        return x == p.x && y == p.y;
    }
    @Override
    public int hashCode() { return Objects.hash(x, y); }
}
class StrictColoredPoint extends StrictPoint {
    final String color;
    StrictColoredPoint(int x, int y, String color) { super(x, y); this.color = color; }
    @Override
    public boolean equals(Object other) {
        return super.equals(other) && color.equals(((StrictColoredPoint) other).color);
    }
    @Override
    public int hashCode() { return Objects.hash(x, y, color); }
}
record ValuePoint(int x, int y) {}
record ColorValue(ValuePoint point, String color) {}
