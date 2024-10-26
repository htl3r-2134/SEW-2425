import java.util.regex.Pattern;

public class MyFirstRegExp {
    private static final String OCTETT = "([1-9]?\\d|1\\d\\d|2[0-4]\\d|25[0-5])";
    private static final Pattern IPV_4_PATTERN = Pattern.compile(OCTETT+"[.]"+OCTETT+"[.]"+OCTETT+"[.]"+OCTETT);
    private static final String NUMBER = "(-?\\d*\\.?\\d*e?-?\\d*)";
    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER);
    private static final String EVENZEROES = "(0{2}1*)+";
    private static final Pattern EVENZEROS_PATTERN = Pattern.compile(EVENZEROES);
    private static final String HASNODOUBLES = "^(?!.*(0{2}|1{2}))[01]+$";
    private static final Pattern HASNODOUBLES_PATTERN = Pattern.compile(HASNODOUBLES);
    private final static String CLASSNAME = "^([A-Z].*[^_])$|[A-Z]";
    private final static Pattern CLASSNAME_PATTERN = Pattern.compile(CLASSNAME);
    private final static String DATE = "(0?\\d|\\d{2})\\.\\s?\\d+\\.\\s?(\\d{2}|\\d{4})?";
    private final static Pattern DATE_PATTERN = Pattern.compile(DATE);
    private final static String FIRSTIP = ".*"+OCTETT+"[.]"+OCTETT+"[.]"+OCTETT+"[.]"+OCTETT+".*";
    private final static Pattern FIRSTIP_PATTERN = Pattern.compile(FIRSTIP);
    private final static String FIRSTDATE = ".*" + DATE + ".*";
    private final static Pattern FIRSTDATE_PATTERN = Pattern.compile(FIRSTDATE);

    public static boolean isIP(String ipAdress) {
       return IPV_4_PATTERN.matcher(ipAdress).matches();
    }


    public static boolean isNumber(String nummer) {
        return NUMBER_PATTERN.matcher(nummer).matches() && !nummer.equalsIgnoreCase(".");
    }


    public static boolean hasEvenZeros(String s) {
        return EVENZEROS_PATTERN.matcher(s).matches();
    }


    public static boolean hasNoDoubles(String s) {
        return HASNODOUBLES_PATTERN.matcher(s).matches();
    }

    public static boolean isClassName(String name) {
        return CLASSNAME_PATTERN.matcher(name).matches();
    }

    public static boolean isDate(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }

    public static String getFirstIp(String IP) {
        String first_ip = "";
        Pattern punkte = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.");
        if (FIRSTIP_PATTERN.matcher(IP).matches()) {
            String[] ips = IP.split("\\D+");
            for (int i = 0; i < ips.length + 1; i++) {
                if (!ips[i].isEmpty()) {
                    first_ip += punkte.matcher(first_ip).matches() ? ips[i] : ips[i] + ".";
                }
                if (IPV_4_PATTERN.matcher(first_ip).matches()) {
                    return first_ip;
                }
            }
        }
        return null;
    }

    public static String getFirstDate(String date) {
        String first_date = "";
        Pattern punkte = Pattern.compile("\\d+\\.\\s?\\d+\\.\\s?");
        if (FIRSTDATE_PATTERN.matcher(date).matches()) {
            String[] dates = date.split("\\D+");
            for (int i = 0; i < dates.length + 1; i++) {
                if (!dates[i].isEmpty()) {
                    first_date += punkte.matcher(first_date).matches() ? dates[i] : dates[i] + ".";
                }
                if (DATE_PATTERN.matcher(first_date).matches()) {
                    if (dates.length >= 4) {
                        if (dates[i + 1].length() == 3) {
                            dates[i + 1] = dates[i + 1].replace(dates[i + 1], dates[i + 1].substring(0, 2));
                            first_date += dates[i + 1];
                        } else if (dates[i + 1].length() == 4) {
                            first_date += dates[i + 1];
                        }
                    }
                    return first_date;
                }
            }
        }
        return null;
    }
}
