import java.util.*;

public class Subnet {
    public static void main(String[] args) {
        Subnet subnet24 = new Subnet("192.168.1.0/24");
        Subnet subnet16 = new Subnet("192.168.0.0/16");
        Subnet subnet8 = new Subnet("10.0.0.0/8");
        Set<IPAddress> hashSet = new HashSet<>();
        Set<IPAddress> treeset = new TreeSet<>();
        System.out.println("Subnet /24");
        testFullSubnetSet(subnet24, hashSet);
        testFullSubnetTree(subnet24, treeset);
        System.out.println("Subnet /16");
        testFullSubnetSet(subnet16, hashSet);
        testFullSubnetTree(subnet16, treeset);
        System.out.println("Subnet /8");
        testFullSubnetSet(subnet8, hashSet);
        testFullSubnetTree(subnet8, treeset);

        /*Subnet a = new Subnet("192.168.0.0/24");
        int startwert = a.getNet().getAsInt();
        int numberhosts = a.getNumberOfHosts();

        for (int i = 0; i < numberhosts; i++) {
            int currentaddress = startwert + i;

            int[] ip = new int[4];
            ip[0] = currentaddress >>> 24 & 0xff;
            ip[1] = currentaddress >>> 16 & 0xff;
            ip[2] = currentaddress >>> 8 & 0xff;
            ip[3] = currentaddress & 0xff;
            System.out.println(ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3]);
        }

         */
    }

    private final IPAddress addr;
    private IPAddress mask;
    private int praefix;

    public Subnet (String ip) {
        String[] addrmask = ip.split("/");
        addr = new IPAddress(addrmask[0]);
        praefix = Integer.parseInt(addrmask[1]);

        if (addrmask[1].equals("0")) {
            mask = new IPAddress("0.0.0.0");
        }

        if (addrmask.length != 2) {
            throw new IllegalArgumentException("no subnetmask");
        }

        if (addrmask[1].length() < 3) {
            String[] ipaddress = addrmask[0].split("\\.");
            for (int i = 0; i < ipaddress.length; i++) {
                if (Integer.parseInt(addrmask[1]) == 8 && i == 2) {
                    if (Integer.parseInt(ipaddress[i]) != 0) {
                        throw new IllegalArgumentException("subnet is /8 so it should be: " + ipaddress[0] + ".0.0.0");
                    }
                }

                if (Integer.parseInt(addrmask[1]) == 16 && i == 3) {
                    if (Integer.parseInt(ipaddress[i]) != 0) {
                        throw new IllegalArgumentException("subnet is /16 so it should be: " + ipaddress[0] + ipaddress[1] + ".0.0");
                    }
                }

                if (Integer.parseInt(addrmask[1]) == 24 && i == 4) {
                    if (Integer.parseInt(ipaddress[i]) != 0) {
                        throw new IllegalArgumentException("subnet is /24 so it should be: " + ipaddress[0] + ipaddress[1] + ipaddress[2] + ".0");
                    }
                }
            }
        }

        if (addrmask[1].length() < 3 && addrmask[1] != "0") {
            int[] subnetmaskip = new int[4];
            int subnetmask = Integer.parseInt(addrmask[1]);
            if (subnetmask < 33 && subnetmask > 0) {
                for (int i = 0; i < subnetmaskip.length; i++) {
                    if (subnetmask >= 8) {
                        subnetmaskip[i] = 0xff;
                        subnetmask -= 8;
                    } else if (subnetmask > 0 && subnetmask < 8) {
                        int letztesquad = 128;
                        int potenz = 128;
                        for (int j = 0; j < (subnetmask - 1); j++) {
                            letztesquad += (potenz / 2);
                            potenz = potenz / 2;
                        }
                        subnetmaskip[i] += letztesquad;
                        subnetmask = 0;
                    }
                }
                mask = new IPAddress(subnetmaskip);
            } /*else {
                throw new IllegalArgumentException("this subnet does not exist, but was:" + subnetmask);
            }*/
        } /*else {{if (addrmask[1].length() > 3)
            mask = new IPAddress(addrmask[1]);
        }*/
    }

    public Subnet (IPAddress ip, int subnetmask) {
        addr = ip;
        int[] subnetmaskip = new int[4];
        if (subnetmask < 33 && subnetmask > 0) {
            for (int i = 0; i < subnetmaskip.length; i++) {
                if (subnetmask >= 8) {
                    subnetmaskip[i] = 0xff;
                    subnetmask -= 8;
                } else if (subnetmask > 0 && subnetmask < 8) {
                    int letztesquad = 128;
                    int potenz = 128;
                    for (int j = 0; j < subnetmask; j++) {
                        letztesquad += (potenz / 2);
                        potenz = potenz / 2;
                    }
                    subnetmaskip[i] = letztesquad;
                }
            }
            mask = new IPAddress(subnetmaskip);
        } else {
            throw new IllegalArgumentException("this subnet does not exist, but was:" + subnetmask);
        }
    }

    public Subnet (IPAddress ip, IPAddress subnet) {
        addr = ip;
        mask = subnet;
    }

    public Subnet (String ip, String subnet) {
        addr = new IPAddress(ip);
        mask = new IPAddress(subnet);
    }

    public IPAddress getNetAddress () {
        return addr;
    }

    public IPAddress getNetMask () {
        return mask;
    }

    public int getPraefix() {
        return praefix;
    }

    public int getNumberOfHosts () {
        /*
        int subnet = 0;
        String subnetmaskv1 = mask.toString();
        String[] subnetmaskv2 = subnetmaskv1.split("\\.");
        for (int i = 0; i < subnetmaskv2.length; i++) {
            if (Integer.parseInt(subnetmaskv2[i]) == 255) {
                subnet += 8;
            } else if (Integer.parseInt(subnetmaskv2[i]) > 0) {
                int nullerwert = 256 - Integer.parseInt(subnetmaskv2[i]);
                int potenzerg = 1;
                int gesamt = 0;
                for (int j = 0; j < 8; j++) {
                    gesamt += (potenzerg * 2);
                    if (nullerwert == gesamt) {
                        int nuller = (int) Math.sqrt(potenzerg * 2);
                        if (nuller == 1) {
                            nuller = 2;
                        }
                        subnet += (8 - nuller);
                    }
                }
            }
        }
        int potenz = 1;
        int anzahlhosts = 0;
        for (int i = 0; i <= (32 - subnet); i++) {
            if (i == 0) {
                anzahlhosts += 1;
            } else {
                anzahlhosts = (potenz * 2);
                potenz = potenz * 2;
            }
        }
        return anzahlhosts;
         */
        return (int) Math.pow(2, 32 - praefix);
    }

    @Override
    public String toString() {
        return addr + "/" + mask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subnet subnet = (Subnet) o;
        return Objects.equals(addr, subnet.addr) && Objects.equals(mask, subnet.mask);
    }

    public IPAddress getNet() {
        return this.addr;
    }

    public static List<IPAddress> getAllIPs(Subnet subnet) {
        List<IPAddress> IPs = new ArrayList<>();
        int startwert = subnet.getNet().getAsInt();
        int numberhosts = subnet.getNumberOfHosts();

        for (int i = 0; i < numberhosts; i++) {
            int currentaddress = startwert + i;
            IPs.add(new IPAddress(currentaddress));
        }
        return IPs;
    }

    public static void testFullSubnetSet(Subnet subnet, Set<IPAddress> validIPs) {
        Subnet randomIPs = new Subnet("192.168.1.0/24");
        if (subnet.getPraefix() == 24) {
            randomIPs = new Subnet("144.46.3.0/24");
        } else if (subnet.getPraefix() == 16) {
            randomIPs = new Subnet("165.89.0.0/16");
        } else if (subnet.getPraefix() == 8) {
            randomIPs = new Subnet("132.0.0.0/8");
        }
        for (IPAddress ip: getAllIPs(subnet)) {
            validIPs.add(ip);
        }
        long eintragZeit = System.currentTimeMillis();

        for (IPAddress ip: getAllIPs(subnet)) {
            validIPs.contains(ip);
        }

        for (IPAddress ip: getAllIPs(randomIPs)) {
            validIPs.contains(ip);
        }

        long suchZeit = System.currentTimeMillis();
        System.out.println("Hashset Dauer: " + (eintragZeit + suchZeit) + " ms");
    }

    public static void testFullSubnetTree(Subnet subnet, Set<IPAddress> validIPs) {
        Subnet randomIPs = new Subnet("192.168.1.0/24");
        if (subnet.getPraefix() == 24) {
            randomIPs = new Subnet("144.46.3.0/24");
        } else if (subnet.getPraefix() == 16) {
            randomIPs = new Subnet("165.89.0.0/16");
        } else if (subnet.getPraefix() == 8) {
            randomIPs = new Subnet("132.0.0.0/8");
        }
        for (IPAddress ip: getAllIPs(subnet)) {
            validIPs.add(ip);
        }
        long eintragZeit = System.currentTimeMillis();

        for (IPAddress ip: getAllIPs(subnet)) {
            validIPs.contains(ip);
        }

        for (IPAddress ip: getAllIPs(randomIPs)) {
            validIPs.contains(ip);
        }

        long suchZeit = System.currentTimeMillis();
        System.out.println("Treeset Dauer: " + (eintragZeit + suchZeit) + " ms");
    }
}
