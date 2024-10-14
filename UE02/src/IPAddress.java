import java.util.*;

public class IPAddress implements Comparable<IPAddress> {
    public static void main(String[] args) {

    }
    private int ipaddress;
    public static final IPAddress LOCALHOST = new IPAddress();
    public static final IPAddress MODEM = new IPAddress("10.0.0.138");
    public IPAddress() {
        ipaddress = 0x7f_00_00_01;
    }

    public IPAddress(int ipaddress){
        set(ipaddress);
    }

    public IPAddress(int ipa, int ipb, int ipc, int ipd) {
        set(ipa, ipb, ipc, ipd);
    }

    public IPAddress(int[] ip) {
        set(ip);
    }

    public IPAddress(String ip) {
        set(ip);
    }
    public void set (int ipaddress){
        this.ipaddress = ipaddress;
    }

    public void set (int ipa, int ipb, int ipc, int ipd) {
        if (ipa < 0 | ipa > 255 | ipb < 0 | ipb > 255 | ipc < 0 | ipc > 255 | ipd < 0 | ipd > 255) {
            throw new IllegalArgumentException(String.format(
                    "octects must be 0..255 but was: %d.%d.%d.%d", ipa, ipb, ipc, ipd));
        }

        ipaddress = (ipa << 24) | (ipb << 16) | (ipc << 8) | ipd;
    }

    public void set (int[] ip) {
        if (ip[0] < 0 | ip[0] > 255 | ip[1] < 0 | ip[1] > 255 | ip[2] < 0 | ip[2] > 255 | ip[3] < 0 | ip[3] > 255) {
            throw new IllegalArgumentException(String.format(
                    "octects must be 0..255 but was: %d.%d.%d.%d", ip[0], ip[1], ip[2], ip[3]));
        }
        ipaddress =  (ip[0] << 24) | (ip[1] << 16) | (ip[2] << 8) | ip[3];
    }

    public void set (String ip) {
        String[] ipString = ip.split("\\.");
        if (ipString.length < 4) {
            throw new IllegalArgumentException("octects must have length 4");
        }

        for (int i = 0; i < ipString.length; i++) {
            for (int j = 0; j < ipString[i].length(); j++) {
                if (Character.isLetter(ipString[i].charAt(j))) {
                    throw new IllegalArgumentException("octects cannot contain any letters");
                }
            }
        }

        for (int i = 0; i < ipString.length; i++) {
            if (Integer.parseInt(ipString[i]) > 255 || Integer.parseInt(ipString[i]) < 0) {
                throw new IllegalArgumentException("must be within the range of 255 and 0");
            }
        }

        int[] ipInteger = new int[4];
        for (int i = 0; i < ipString.length; i++) {
            ipInteger[i] += Integer.parseInt(ipString[i]);
        }
        set(ipInteger);
    }

    public int getAsInt () {
        return ipaddress;
    }

    public int getOctet (int num) {
        num = ipaddress >>> 24;
        return num;
    }

    public int[] getAsArray () {
        int[] ip = new int[] {ipaddress >>> 24 & 0xff, ipaddress >>> 16 & 0xff, ipaddress >>> 8 & 0xff, ipaddress & 0xff};
        return ip;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPAddress ipAddress = (IPAddress) o;
        return ipaddress == ipAddress.ipaddress;
    }

    @Override
    public String toString() {
        int[] ip = new int[4];
        ip[0] = ipaddress >>> 24 & 0xff;
        ip[1] = ipaddress >>> 16 & 0xff;
        ip[2] = ipaddress >>> 8 & 0xff;
        ip[3] = ipaddress & 0xff;
        return ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipaddress);
    }

    @Override
    public int compareTo(IPAddress o) {
        return Integer.compareUnsigned(ipaddress, o.ipaddress);
    }

    public int getIP() {
        return this.ipaddress;
    }

    public boolean containsIP (Subnet o) {
        if ((this.ipaddress & o.getNetMask().getAsInt()) == o.getNet().getAsInt()) {
            return true;
        }
        return false;
    }

    public IPAddress getNextHop(TreeMap<Subnet, IPAddress> next) {
        for (Map.Entry<Subnet, IPAddress> Subnet : next.entrySet()) {
            if (containsIP(Subnet.getKey())) {
                return Subnet.getValue();
            }
        }
        return null;
    }
}