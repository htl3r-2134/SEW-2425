import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;

public class Lambdas {

    public static void print(Collection<?> collection) {
        collection.forEach(System.out::println);
    }

    public static Collection<String> filterNouns(Collection<String> words) {
        words.removeIf(s -> s == null || s.isEmpty() || Character.isLowerCase(s.charAt(0)) || !s.matches("\\p{IsAlphabetic}+"));
        return words;
    }

    public static List<Double> multiply(List<Double> numbers, double factor) {
        numbers.replaceAll(s -> s * factor);
        return numbers;
    }

    public static List<Double> applyFunction(List<Double> numbers, UnaryOperator<Double> operation) {
        numbers.replaceAll(operation);
        return numbers;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<String> sortNumerically(String... elements) {
        List<String> list = new ArrayList<>(List.of(elements));
        list.sort((a, b) -> {
            boolean aIsNr = isInteger(a);
            boolean bIsNr = isInteger(b);

            if (aIsNr && bIsNr) {
                return Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
            }

            if (!aIsNr && !bIsNr) {
                return a.compareTo(b);
            }

            return aIsNr ? -1 : 1;
        } );

        return list;
    }
}