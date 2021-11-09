package application;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Play_C2_Streams {
    public static void main(String[] args) {
        Stream<String> names = List.of("Hendricks", "Raymond", "Pena", "Gonzalez",
                "Nielsen", "Hamilton", "Graham", "Gill", "Vance", "Howe", "Ray", "Talley",
                "Brock", "Hall", "Gomez", "Bernard", "Witt", "Joyner", "Rutledge", "Petty",
                "Strong", "Soto", "Duncan", "Lott", "Case", "Richardson", "Crane", "Cleveland",
                "Casey", "Buckner", "Hardin", "Marquez", "Navarro").stream();
        names.sorted().sorted(Comparator.comparingInt(String::length)).forEach(n -> System.out.println(n + ","));
        names = List.of("Hendricks", "Raymond", "Pena", "Gonzalez",
                "Nielsen", "Hamilton", "Graham", "Gill", "Vance", "Howe", "Ray", "Talley",
                "Brock", "Hall", "Gomez", "Bernard", "Witt", "Joyner", "Rutledge", "Petty",
                "Strong", "Soto", "Duncan", "Lott", "Case", "Richardson", "Crane", "Cleveland",
                "Casey", "Buckner", "Hardin", "Marquez", "Navarro").stream();
        List<String> namen = names.filter(s -> s.endsWith("ez")).toList();
        namen.forEach(n -> System.out.print(n + ", "));
    }
}
