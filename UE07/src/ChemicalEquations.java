import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChemicalEquations {

    public static HashMap<String, Integer> getMolekuele(String s) {
        HashMap<String, Integer> molekuele = new HashMap<>();

        Pattern multiplikationsfaktor = Pattern.compile("(\\d+)\\s.+");
        Matcher matcherMult = multiplikationsfaktor.matcher(s.trim());
        Pattern element = Pattern.compile("([A-Z][a-z]?)(\\d*)?");
        Matcher matcherElement = element.matcher(s.trim());

        if (s.contains("+")) {
            for (int i = 0; i < s.split("\\+").length; i++) {
                matcherElement = element.matcher(s.split("\\+")[i].trim());
                matcherMult = multiplikationsfaktor.matcher(s.split("\\+")[i].trim());
                put(molekuele, matcherMult, matcherElement);
            }
        } else {
            put(molekuele, matcherMult, matcherElement);
        }
        return molekuele;
    }

    private static void put(HashMap<String, Integer> molekuele, Matcher matcherMult, Matcher matcherElement) {
        while (matcherElement.find()) {
            int wert = matcherElement.group(2).isEmpty() ? (matcherMult.matches() ? Integer.parseInt(matcherMult.group(1)) : 1) : matcherMult.matches() ? (Integer.parseInt(matcherElement.group(2)) * Integer.parseInt(matcherMult.group(1))) : Integer.parseInt(matcherElement.group(2));
            for (Map.Entry<String, Integer> o : molekuele.entrySet()) {
                wert = o.getKey().equals(matcherElement.group(1)) ? wert + o.getValue() : wert;
            }
            molekuele.put(matcherElement.group(1), wert);
        }
    }

    public static boolean analyze(String s) {
        return s.contains("->") && getMolekuele(s.split("->")[0].trim()).equals(getMolekuele(s.split("->")[1].trim()));
    }
}
