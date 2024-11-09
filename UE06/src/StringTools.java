import org.junit.platform.engine.support.discovery.SelectorResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {
    public static String removeMultiSpaces(String s) {
        return s.replaceAll("\\s+", " ");
    }

    public static Object[] splitToLines(String s) {
        return s.split("\\r.|\\n.|\\r+\\n+");
    }

    public static List<String> splitToLines(String s, int maxLen) {
        List<String> splitLines = new ArrayList<>();
        Pattern line = Pattern.compile(".{1," + maxLen + "}(\\s|$)");
        Matcher matcher = line.matcher(s);
        while (matcher.find()) {
            splitLines.add(matcher.group().trim());
        }
        return splitLines;
    }
}
