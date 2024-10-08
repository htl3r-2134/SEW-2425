package Collections_II;

import java.util.Comparator;
import java.util.TreeMap;

public class MySubnetComparator_1_2 {

    public static int compareIP (Subnet o1, Subnet o2) {
        String[] addrMask1 = o1.toString().split("/");
        String[] addrMask2 = o2.toString().split("/");
        String[] netmask1 = addrMask1[0].split("\\.");
        String[] netmask2 = addrMask2[0].split("\\.");

        int equals = 0;
        for (int i = 0; i < netmask1.length; i++) {
            if (netmask1[i].equals(netmask2[i])) {
                equals++;
            }
            if (equals == 4) {
                return 0;
            }
        }

        if (Integer.parseInt(netmask1[0]) > Integer.parseInt(netmask2[0])) {
            return 1;
        }
        return -1;
    }
    public static class MySubnetComparator implements Comparator<Subnet> {
        @Override
        public int compare(Subnet o1, Subnet o2) {
            return compareIP(o1, o2);
        }
    }

    public static class MySubnetComparator2 implements Comparator<Subnet> {
        @Override
        public int compare(Subnet o1, Subnet o2) {
            if (o1.getNetMask().getIP() == o2.getNetMask().getIP()) {
                return compareIP(o1, o2);
            }
            int[] mask1 = o1.getNetMask().getAsArray();
            int[] mask2 = o2.getNetMask().getAsArray();
            int mask1_zahl = 0;
            int mask2_zahl = 0;
            /*
            for (int i = 0; i < mask1.length; i++) {
                if (mask1[i] == 255) {
                    mask1_zahl++;
                }
            }
            for (int i = 0; i < mask2.length; i++) {
                if (mask2[i] == 255) {
                    mask2_zahl++;
                }
            }
             */
            for (int i = 0; i < 4; i++) {
                if (mask1[i] == mask2[i]) {
                    mask1_zahl++;
                    mask2_zahl++;
                } else if (mask1[i] > mask2[i]) {
                    mask1_zahl++;
                } else if (mask2[i] > mask1[i]) {
                    mask2_zahl++;
                }
            }
            if (mask1_zahl > mask2_zahl) {
                return -1;
            } else if (mask1_zahl < mask2_zahl) {
                return 1;
            }
            return 0;
        }


    }
}
