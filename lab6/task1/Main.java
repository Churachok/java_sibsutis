package lab6.task1;

public class Main {
    static void compare(String name, String first, String second) {
        System.out.println(name + ": == " + (first == second) + ", equals " + first.equals(second));
    }
    public static void main(String[] args) {
        System.out.println("Ожидаются пары ==/equals: true/true, false/true, true/true, false/true, true/true. Текст сравниваем через equals, ссылки — через ==.");
        String literal = "Java";
        String copy = new String("Java");
        final String constant = "Ja";
        String runtime = args.length == 0 ? "Ja" : args[0];
        compare("Литералы", literal, "Java");
        compare("new String", literal, copy);
        compare("Compile-time", literal, constant + "va");
        compare("Runtime", literal, runtime + "va");
        compare("intern", literal, copy.intern());
    }
}
