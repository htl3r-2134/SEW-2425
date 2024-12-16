import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

public class LambdasTest {

    // Helper method: To capture console output
    private String captureOutput(Runnable task) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        try {
            task.run();
        } finally {
            System.setOut(originalOut);
        }
        return outContent.toString();
    }

    // Task 1: print(Collection<?> collection)
    @Test
    void testPrint() {
        Collection<String> collection = List.of("A", "B", "C");
        assertDoesNotThrow(() -> Lambdas.print(collection));
    }

    @Test
    void testPrintWithStrings() {
        Collection<String> collection = List.of("A", "B", "C");
        String output = captureOutput(() -> Lambdas.print(collection));
        assertEquals("A\nB\nC\n", output);
    }

    @Test
    void testPrintWithEmptyCollection() {
        Collection<String> collection = List.of();
        String output = captureOutput(() -> Lambdas.print(collection));
        assertEquals("", output);
    }

    @Test
    void testPrintWithNullElements() {
        Collection<String> collection = Arrays.asList("A", null, "B");
        String output = captureOutput(() -> Lambdas.print(collection));
        assertEquals("A\nnull\nB\n", output);
    }

    @Test
    void testPrintWithMixedTypes() {
        Collection<Object> collection = Arrays.asList("String", 42, 3.14, true);
        String output = captureOutput(() -> Lambdas.print(collection));
        assertEquals("String\n42\n3.14\ntrue\n", output);
    }

    @Test
    void testPrintWithLargeCollection() {
        Collection<Integer> collection = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            collection.add(i);
        }
        String output = captureOutput(() -> Lambdas.print(collection));
        assertEquals("0\n1\n2\n3\n4\n", output);
    }

    // Task 2: filterNouns(Collection<String> words)
    @Test
    void testFilterNounsOnlyCapitalized() {
        Collection<String> input = List.of("Hugo", "House", "123", "car", "");
        Collection<String> expected = List.of("Hugo", "House");
        assertEquals(expected, Lambdas.filterNouns(new ArrayList<>(input)));
    }

    @Test
    void testFilterNounsEmptyList() {
        Collection<String> input = List.of();
        Collection<String> expected = List.of();
        assertEquals(expected, Lambdas.filterNouns(new ArrayList<>(input)));
    }

    @Test
    void testFilterNounsSimpleCase() {
        Collection<String> input = new ArrayList<>(List.of("Hugo", "HTL"));
        Collection<String> expected = List.of("Hugo", "HTL");
        assertEquals(expected, Lambdas.filterNouns(input));
    }

    @Test
    void testFilterNounsWithNumbers() {
        Collection<String> input = new ArrayList<>(List.of("Hugo", "HTL", "123"));
        Collection<String> expected = List.of("Hugo", "HTL");
        assertEquals(expected, Lambdas.filterNouns(input));
    }

    @Test
    void testFilterNounsWithInvalidCharacters() {
        Collection<String> input = new ArrayList<>(List.of("Hugo", "HTL", "a-b"));
        Collection<String> expected = List.of("Hugo", "HTL");
        assertEquals(expected, Lambdas.filterNouns(input));
    }

    @Test
    void testFilterNounsWithNullValues() {
        Collection<String> input = new ArrayList<>(Arrays.asList(null, "Hugo"));
        Collection<String> expected = List.of("Hugo");
        assertEquals(expected, Lambdas.filterNouns(input));
    }

    @Test
    void testFilterNounsComplexCase() {
        Collection<String> input = new ArrayList<>(Arrays.asList(null, "Hugo", "HTL", "HTL3R", "", "House-Boat", "SEW", "SoftwareDevelopment"));
        Collection<String> expected = List.of("Hugo", "HTL", "SEW", "SoftwareDevelopment");
        assertEquals(expected, Lambdas.filterNouns(input));
    }

    // Task 3: multiply(List<Double> numbers, double factor)
    @Test
    void testMultiplyPositiveFactor() {
        List<Double> input = List.of(1.0, 2.0, 3.0);
        List<Double> expected = List.of(2.0, 4.0, 6.0);
        assertEquals(expected, Lambdas.multiply(new ArrayList<>(input), 2.0));
    }

    @Test
    void testMultiplyZeroFactor() {
        List<Double> input = List.of(1.0, 2.0, 3.0);
        List<Double> expected = List.of(0.0, 0.0, 0.0);
        assertEquals(expected, Lambdas.multiply(new ArrayList<>(input), 0.0));
    }

    @Test
    void testMultiplyWithFactorTwo() {
        List<Double> input = new ArrayList<>(List.of(1.0, 2.0, 3.0, 45.0));
        List<Double> expected = List.of(2.0, 4.0, 6.0, 90.0);
        assertEquals(expected, Lambdas.multiply(input, 2));
    }

    @Test
    void testMultiplyWithFactorEleven() {
        List<Double> input = new LinkedList<>(List.of(1.0, 2.0, 3.0, 45.0));
        List<Double> expected = List.of(11.0, 22.0, 33.0, 495.0);
        assertEquals(expected, Lambdas.multiply(input, 11));
    }

    // Task 4: applyFunction(List<Double> numbers, UnaryOperator<Double> operation)
    @Test
    void testApplyFunctionSquare() {
        List<Double> input = List.of(1.0, 2.0, 3.0);
        UnaryOperator<Double> square = n -> n * n;
        List<Double> expected = List.of(1.0, 4.0, 9.0);
        assertEquals(expected, Lambdas.applyFunction(new ArrayList<>(input), square));
    }

    @Test
    void testApplyFunctionNegate() {
        List<Double> input = List.of(1.0, -2.0, 3.0);
        UnaryOperator<Double> negate = n -> -n;
        List<Double> expected = List.of(-1.0, 2.0, -3.0);
        assertEquals(expected, Lambdas.applyFunction(new ArrayList<>(input), negate));
    }

    @Test
    void testApplyFunctionWithMultiplicationByTwo() {
        List<Double> input = new LinkedList<>(List.of(1.0, 2.0, 3.0, 45.0));
        UnaryOperator<Double> operation = n -> n * 2;
        List<Double> expected = List.of(2.0, 4.0, 6.0, 90.0);
        assertEquals(expected, Lambdas.applyFunction(input, operation));
    }

    @Test
    void testApplyFunctionWithMultiplicationAndAddition() {
        List<Double> input = new ArrayList<>(List.of(1.0, 2.0, 3.0, 45.0));
        UnaryOperator<Double> operation = n -> n * 2 + 1;
        List<Double> expected = List.of(3.0, 5.0, 7.0, 91.0);
        assertEquals(expected, Lambdas.applyFunction(input, operation));
    }

    // Task 5: sortNumerically(String... elements)
    @Test
    void testSortNumericallyMixedInput() {
        String[] input = {"12", "Wappler", "8", "-5", "8", "Hugo", "-9", "-10", "Hugo", "-10", "7", "*"};
        List<String> expected = List.of("-10", "-10", "-9", "-5", "7", "8", "8", "12", "*", "Hugo", "Hugo", "Wappler");
        assertEquals(expected, Lambdas.sortNumerically(input));
    }

    @Test
    void testSortNumericallyOnlyStrings() {
        String[] input = {"B", "A"};
        List<String> expected = List.of("A", "B");
        assertEquals(expected, Lambdas.sortNumerically(input));
    }

    @Test
    void testSortNumericallyOnlyNumbers() {
        String[] input = {"10", "2", "-1"};
        List<String> expected = List.of("-1", "2", "10");
        assertEquals(expected, Lambdas.sortNumerically(input));
    }

    @Test
    void testSortNumericallySpecialCharacters() {
        String[] input = {"!", "Z", "1"};
        List<String> expected = List.of("1", "!", "Z");
        assertEquals(expected, Lambdas.sortNumerically(input));
    }



}
