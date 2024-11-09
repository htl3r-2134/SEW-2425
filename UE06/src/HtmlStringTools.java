import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlStringTools {
    public static Map<String, Double> getHtmlTagStatistic(String filename) throws IOException {
        Map<String, Double> htmlTagStatistics = new HashMap<>();
        BufferedReader in = Files.newBufferedReader(Path.of(filename), StandardCharsets.UTF_8);
        Pattern htmlTag = Pattern.compile("<([a-z]*[A-Z]*[0-9]*\\s*)(\\s|>)");
        int totalTags = 0;
        String line;

        while ((line = in.readLine()) != null) {
            Matcher matcher = htmlTag.matcher(line);
            while (matcher.find()) {
                Double newValue = 1.0;
                for (Map.Entry<String, Double> o : htmlTagStatistics.entrySet()) {
                    if (matcher.group(1).toLowerCase().equals(o.getKey())) {
                        newValue += o.getValue();
                    }
                }
                totalTags++;
                htmlTagStatistics.put(matcher.group(1).toLowerCase(), newValue);
            }
        }
        for (Map.Entry<String, Double> o : htmlTagStatistics.entrySet()) {
            htmlTagStatistics.replace(o.getKey(), (o.getValue() / totalTags));
        }
        return htmlTagStatistics;
    }

    public static Set<String> getAbsolutLinks(String filename) throws IOException {
        HashSet<String> allLinks = new HashSet<>();
        BufferedReader in = Files.newBufferedReader(Path.of(filename), StandardCharsets.UTF_8);
        Pattern getLinks = Pattern.compile("\"(.+)\"");
        String line;

        while ((line = in.readLine()) != null) {
            Matcher matcher = getLinks.matcher(line);
            while (matcher.find()) {
                allLinks.add(matcher.group(1));
            }
        }
        return allLinks;
    }

    public static Set<String> getDomains(String filename) throws IOException {
        HashSet<String> allDomains = new HashSet<>();
        BufferedReader in = Files.newBufferedReader(Path.of(filename), StandardCharsets.UTF_8);
        Pattern getDomains = Pattern.compile("//(?:www\\.)?([a-zA-Z0-9.-]+)");
        String line;

        while ((line = in.readLine()) != null) {
            Matcher matcher = getDomains.matcher(line);
            while (matcher.find()) {
                allDomains.add(matcher.group(1));
            }
        }
        return allDomains;
    }
}
