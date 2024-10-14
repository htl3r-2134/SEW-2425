import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.util.List;
import java.util.Map;

class LongCounterTest {

    @Test
    void testPutAndGetValues() {
        LongCounter<String> c1 = new LongCounter<>();
        c1.put("hugo");
        c1.put("hugo");
        c1.put("detlef");
        c1.put("detlef");
        c1.put("detlef");
        c1.put("detlef");
        c1.put("detlef");
        c1.put("hugo", 4);

        Assertions.assertEquals(2, c1.size());
        Assertions.assertTrue(c1.containsKey("hugo"));
        Assertions.assertEquals(6L,c1.get("hugo"));
        Assertions.assertEquals(5L,c1.get("detlef"));
    }

    @Test
    void testSubtractValues() {
        LongCounter<String> c1 = new LongCounter<>();
        c1.put("hugo");
        c1.put("hugo");
        c1.put("detlef");
        c1.put("detlef");
        c1.put("detlef");
        c1.put("detlef");
        c1.put("detlef");
        c1.put("hugo", 4);

        LongCounter<String> c2 = new LongCounter<>();
        c2.put("hugo");
        c2.put("hugo");
        c2.put("franz");
        c2.put("franz", 4);
        c2.put("walter", 2);

        c1.subtractAll(c2);

        Assertions.assertEquals(4L, c1.size());
        Assertions.assertEquals(5L,c1.get("detlef"));
        Assertions.assertEquals(-5L,c1.get("franz"));
        Assertions.assertEquals(-2L,c1.get("walter"));
    }

    @Test
    void testMostAndLessCommon() {
        LongCounter<String> c1 = new LongCounter<>();
        c1.put("hugo");
        c1.put("hugo");
        c1.put("detlef");

        Assertions.assertEquals(1, c1.mostCommon(1).size());
        Assertions.assertEquals("hugo", c1.mostCommon(1).get(0).getKey());
        Assertions.assertEquals("detlef", c1.lessCommon(1).get(0).getKey());
    }


    @Test
    void testStringCounterFromInput() {
        LongCounter<Character> cS = LongCounter.fromString("Hallo Welt? Wie geht es Dir?");
        cS.putAll(LongCounter.fromString("Super, warum fragst Du?"));
        Assertions.assertEquals('w', cS.lessCommon(1).get(0).getKey());
        Assertions.assertEquals(5L, cS.mostCommon(2).get(1).getValue());
    }


    @Test
    void testClearZeros() {
        LongCounter<Character> s = LongCounter.fromString("anna");
        Assertions.assertEquals(2, s.size());
        s.subtractAll(LongCounter.fromString("anna"));
        Assertions.assertEquals(2, s.size());
        s.clearZeros();
        Assertions.assertEquals(0, s.size());
    }

    @Test
    void testEmptyCounter() {
        LongCounter<String> emptyCounter = new LongCounter<>();
        Assertions.assertTrue(emptyCounter.isEmpty());
        Assertions.assertEquals(0, emptyCounter.size());
    }

    @Test
    void testNegativeValues() {
        LongCounter<String> c = new LongCounter<>();
        c.put("negative", -5);
        Assertions.assertEquals(-5, c.get("negative"));
    }

    @Test
    void testPutAll() {
        LongCounter<String> c1 = new LongCounter<>();
        c1.put("apple", 3);
        c1.put("banana", 2);

        LongCounter<String> c2 = new LongCounter<>();
        c2.put("apple", 1);
        c2.put("orange", 4);

        c1.putAll(c2);

        Assertions.assertEquals(4, c1.get("apple"));
        Assertions.assertEquals(2, c1.get("banana"));
        Assertions.assertEquals(4, c1.get("orange"));
    }

    @Test
    void testLargeInput() {
        LongCounter<Integer> largeCounter = new LongCounter<>();
        for (int i = 0; i < 10000; i++) {
            largeCounter.put(i);
        }
        Assertions.assertEquals(10000, largeCounter.size());
    }

    @Test
    void testSubtractAllWithMissingKeys() {
        LongCounter<String> c1 = new LongCounter<>();
        c1.put("existing", 5);

        LongCounter<String> c2 = new LongCounter<>();
        c2.put("missing", 3);

        c1.subtractAll(c2);

        Assertions.assertEquals(5, c1.get("existing"));
        Assertions.assertEquals(-3, c1.get("missing"));
    }

    @Test
    void testMostCommonWithTies() {
        LongCounter<String> c = new LongCounter<>();
        c.put("apple", 3);
        c.put("banana", 3);
        c.put("cherry", 1);

        List<Map.Entry<String, Long>> mostCommon = c.mostCommon(2);
        Assertions.assertEquals(2, mostCommon.size());
        Assertions.assertTrue(mostCommon.get(0).getValue() == 3 && mostCommon.get(1).getValue() == 3);
    }

    @Test
    void testSubtractResultingInZero() {
        LongCounter<String> c1 = new LongCounter<>();
        c1.put("key", 5);

        LongCounter<String> c2 = new LongCounter<>();
        c2.put("key", 5);

        c1.subtractAll(c2);
        Assertions.assertEquals(0, c1.get("key"));
    }

    @Test
    void testPutWithNegativeIncrement() {
        LongCounter<String> c = new LongCounter<>();
        c.put("negative", -10);
        Assertions.assertEquals(-10, c.get("negative"));
    }

    @Test
    void testSubtractAllWithNonExistingKeys() {
        LongCounter<String> c1 = new LongCounter<>();
        c1.put("key1", 10);

        LongCounter<String> c2 = new LongCounter<>();
        c2.put("key2", 5);

        c1.subtractAll(c2);
        Assertions.assertEquals(10, c1.get("key1"));
        Assertions.assertEquals(-5, c1.get("key2"));
    }

    @Test
    void testClearZerosWithMultipleEntries() {
        LongCounter<String> c = new LongCounter<>();
        c.put("apple", 0);
        c.put("banana", 5);
        c.put("cherry", 0);

        c.clearZeros();
        Assertions.assertEquals(1, c.size());
        Assertions.assertTrue(c.containsKey("banana"));
    }

    @Test
    void testLargeSubtractionScenario() {
        LongCounter<String> c1 = new LongCounter<>();
        c1.put("apple", Long.MAX_VALUE);

        LongCounter<String> c2 = new LongCounter<>();
        c2.put("apple", Long.MAX_VALUE - 1);

        c1.subtractAll(c2);
        Assertions.assertEquals(1, c1.get("apple"));
    }

    @Test
    void testFromStringWithSpecialCharacters() {
        LongCounter<Character> c = LongCounter.fromString("!@#$%^&*()");
        Assertions.assertEquals(10, c.size());
        Assertions.assertEquals(1, c.get('!'));
        Assertions.assertEquals(1, c.get('@'));
    }

    @Test
    void testMostCommonExceedingLimit() {
        LongCounter<String> c = new LongCounter<>();
        c.put("apple", 1);
        c.put("banana", 2);
        c.put("cherry", 3);

        List<Map.Entry<String, Long>> result = c.mostCommon(5);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void testPutAllWithDuplicateKeys() {
        LongCounter<String> c1 = new LongCounter<>();
        c1.put("apple", 2);

        LongCounter<String> c2 = new LongCounter<>();
        c2.put("apple", 3);

        c1.putAll(c2);
        Assertions.assertEquals(5, c1.get("apple"));
    }

    @Test
    void testSubtractAllWithLargeNegativeResults() {
        LongCounter<String> c1 = new LongCounter<>();
        c1.put("apple", 1);

        LongCounter<String> c2 = new LongCounter<>();
        c2.put("apple", 100);

        c1.subtractAll(c2);
        Assertions.assertEquals(-99, c1.get("apple"));
    }

}