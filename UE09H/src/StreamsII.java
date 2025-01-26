import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class StreamsII {
    public static Stream<BigInteger> fibonacciSequence() {
        return Stream.iterate(new BigInteger[]{BigInteger.ONE, BigInteger.ONE},
                        pair -> new BigInteger[]{pair[1], pair[0].add(pair[1])})
                .map(pair -> pair[0]);
    }

    public static Stream<Path> greatestFiles(Path directory, int fileLimit) {
        try {
            return Files.walk(directory)
                    .filter(Files::isRegularFile)
                    .sorted((a, b) -> Long.compare(b.toFile().length(), a.toFile().length()))
                    .limit(fileLimit)
                    .map(Path::toAbsolutePath);
        } catch (IOException ex) {
            return Stream.empty();
        }
    }

    public static Map<Character, Long> getCharStatistic(List<String> inputStrings) {
        return inputStrings.stream()
                .flatMapToInt(String::chars)
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


    private static final Map<Character, String> morseCodeMap = new HashMap<>();
    static {
        morseCodeMap.put('A', ".-");
        morseCodeMap.put('B', "-...");
        morseCodeMap.put('C', "-.-.");
        morseCodeMap.put('D', "-..");
        morseCodeMap.put('E', ".");
        morseCodeMap.put('F', "..-.");
        morseCodeMap.put('G', "--.");
        morseCodeMap.put('H', "....");
        morseCodeMap.put('I', "..");
        morseCodeMap.put('J', ".---");
        morseCodeMap.put('K', "-.-");
        morseCodeMap.put('L', ".-..");
        morseCodeMap.put('M', "--");
        morseCodeMap.put('N', "-.");
        morseCodeMap.put('O', "---");
        morseCodeMap.put('P', ".--.");
        morseCodeMap.put('Q', "--.-");
        morseCodeMap.put('R', ".-.");
        morseCodeMap.put('S', "...");
        morseCodeMap.put('T', "-");
        morseCodeMap.put('U', "..-");
        morseCodeMap.put('V', "...-");
        morseCodeMap.put('W', ".--");
        morseCodeMap.put('X', "-..-");
        morseCodeMap.put('Y', "-.--");
        morseCodeMap.put('Z', "--..");
        morseCodeMap.put('0', "-----");
        morseCodeMap.put('1', ".----");
        morseCodeMap.put('2', "..---");
        morseCodeMap.put('3', "...--");
        morseCodeMap.put('4', "....-");
        morseCodeMap.put('5', ".....");
        morseCodeMap.put('6', "-....");
        morseCodeMap.put('7', "--...");
        morseCodeMap.put('8', "---..");
        morseCodeMap.put('9', "----.");
        morseCodeMap.put(' ', "/");
        morseCodeMap.put('Ä', ".-.-");
        morseCodeMap.put('Ö', "---.");
        morseCodeMap.put('Ü', "..--");
        morseCodeMap.put('ß', "...--..");
    }


    public static String toMorseCode(String text) {
        return "/" + Stream.of(text.toUpperCase().split(" ")) // Text in Wörter aufteilen
                .map(word -> Stream.of(word.split("")) // Jedes Wort in Buchstaben aufteilen
                        .map(c -> morseCodeMap.get(c.charAt(0))) // Morse-Code für jeden Buchstaben
                        .filter(morseChar -> morseChar != null) // Null-Werte filtern
                        .collect(Collectors.joining("/"))) // Buchstaben mit "/" verbinden
                .collect(Collectors.joining("//")) // Wörter mit "//" verbinden
                + "///"; // Schrägstrich am Ende hinzufügen
    }

    public static String getMostCommonIp(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .map(StreamsII::extractIp)
                .filter(ip -> ip != null)
                .collect(Collectors.groupingBy(ip -> ip, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    private static String extractIp(String line) {
        String ipPattern = "(\\d{1,3}(\\.\\d{1,3}){3})";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(ipPattern);
        java.util.regex.Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static String getMostCommonPage(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .map(StreamsII::extractPage)
                .filter(page -> page != null)
                .collect(Collectors.groupingBy(page -> page, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private static String extractPage(String line) {
        String urlPattern = "(http[s]?://[^\\s]+)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(urlPattern);
        java.util.regex.Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static long countDownloadBytes(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .mapToLong(StreamsII::extractBytes)
                .sum();
    }


    private static long extractBytes(String line) {
        String[] parts = line.split(" ");

        if (parts.length > 1) {
            String lastPart = parts[parts.length - 1];
            try {
                return Long.parseLong(lastPart);
            } catch (NumberFormatException e) {
                if (parts.length > 2) {
                    String secondLastPart = parts[parts.length - 2];
                    try {
                        return Long.parseLong(secondLastPart);
                    } catch (NumberFormatException ex) {
                        return 0;
                    }
                }
            }
        }
        return 0;
    }

    public static void printStudentStatistics(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        Map<String, Long> departmentCount = new HashMap<>();
        Map<Integer, Long> monthCount = new HashMap<>();
        Map<String, Long> birthDateCount = new HashMap<>();

        lines.stream()
                .skip(1)
                .map(line -> line.split(";"))
                .filter(parts -> parts.length >= 4)
                .forEach(parts -> {
                    String department = parts[1].trim();
                    String birthDate = parts[2].trim();

                    if (!department.isEmpty()) {
                        departmentCount.put(department, departmentCount.getOrDefault(department, 0L) + 1);
                    }

                    try {
                        String[] dateParts = birthDate.split("-");
                        if (dateParts.length == 3) {
                            int month = Integer.parseInt(dateParts[1]);
                            if (month >= 1 && month <= 12) {
                                monthCount.put(month, monthCount.getOrDefault(month, 0L) + 1);
                            } else {
                                System.err.println("Ungültiger Monat in Geburtsdatum: " + birthDate);
                            }
                        } else {
                            System.err.println("Ungültiges Geburtsdatum-Format: " + birthDate);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Fehler beim Parsen des Geburtsmonats: " + birthDate);
                    }

                    birthDateCount.put(birthDate, birthDateCount.getOrDefault(birthDate, 0L) + 1);
                });

        long totalClasses = departmentCount.size();
        long totalStudents = departmentCount.values().stream().mapToLong(Long::longValue).sum();

        double averageClassSize = (double) totalStudents / totalClasses;

        System.out.println("Anzahl Schüler pro Abteilung:");
        departmentCount.forEach((dept, count) -> System.out.println(dept + ": " + count));

        System.out.println("Anzahl Klassen: " + totalClasses);
        System.out.printf("Durchschnittliche Klassengröße: %.2f%n", averageClassSize);

        System.out.println("Schüler pro Geburtsmonat:");
        for (int month = 1; month <= 12; month++) {
            System.out.println("Monat " + month + ": " + monthCount.getOrDefault(month, 0L));
        }

        long classesWithSameBirthDate = birthDateCount.values().stream()
                .filter(count -> count >= 2)
                .count();
        System.out.println("Klassen mit mind. 2 Schülern gleichen Geburtsdatums: " + classesWithSameBirthDate);
    }
}
