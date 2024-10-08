package Collections_II;

import org.junit.jupiter.api.Test;
import java.util.*;
import Collections_II.MySubnetComparator_1_2.MySubnetComparator;
import Collections_II.MySubnetComparator_1_2.MySubnetComparator2;

import static org.junit.jupiter.api.Assertions.*;

class IpCollectionTestv2 {

    @Test
    void ipAddressTest1() {
        String __msg = "wurde equals() definiert?";
        assertEquals(new IPAddress("10.0.0.1"), new IPAddress("10.0.0.1"), __msg);
    }

    @Test
    void ipAddressTest2() {
        String __msg = "wurde hashCode() definiert?";
        assertEquals(new IPAddress("10.0.0.1").hashCode(), new IPAddress("10.0.0.1").hashCode(),
                __msg);
    }

    @Test
    void ipAddressTest3() {
        String __msg = "sIP.size() bei HashSet";
        Set<IPAddress> sIP = new HashSet<IPAddress>();
        sIP.add(new IPAddress("10.0.0.1"));
        sIP.add(new IPAddress("10.0.0.2"));
        sIP.add(new IPAddress("10.0.0.1"));
        assertEquals(2, sIP.size(), __msg);
    }

    @Test
    void ipAddressTest4() {
        String __msg = "wurde compareTo() definiert?";
        assertEquals(0,
                new IPAddress("10.0.0.1").compareTo(new IPAddress("10.0.0.1"))
                , __msg);
    }

    @Test
    void ipAddressTest5() {
        String __msg = "wurde compareTo() richtig definiert?";
        assertEquals(-1,
                new IPAddress("10.0.0.1").compareTo(new IPAddress("10.0.0.3"))
                , __msg);
        assertEquals(1,
                new IPAddress("10.0.0.3").compareTo(new IPAddress("10.0.0.1"))
                , __msg);
        assertEquals(-1,
                new IPAddress("10.0.0.1").compareTo(new IPAddress("192.168.1.1"))
                , __msg);
    }

    @Test
    void ipAddressTest6() {
        String __msg = "sIP.size() bei TreeSet";
        Set<IPAddress> sIP = new TreeSet<IPAddress>();
        sIP.add(new IPAddress("10.0.0.1"));
        sIP.add(new IPAddress("10.0.0.2"));
        sIP.add(new IPAddress("10.0.0.1"));
        assertEquals(2,
                sIP.size()
                , __msg);
    }

    @Test
    void ipAddressTest7() {
        String __msg = "st.toArray()[1]";
        Set<IPAddress> st = new TreeSet<>();
        st.add(new IPAddress("10.0.0.1"));
        st.add(new IPAddress("172.16.0.1"));
        st.add(new IPAddress("192.168.1.1"));
        assertEquals(new IPAddress("172.16.0.1"),
                st.toArray()[1]
                , __msg);
    }

    @Test
    void snMySnComparatorTest1() {
        Subnet sn1 = new Subnet("172.16.0.0/24");
        Subnet sn2 = new Subnet("192.168.0.0/16");
        Subnet sn3 = new Subnet("127.0.0.0/8");
        MySubnetComparator mc = new MySubnetComparator();
        String __msg = "mc.compare(sn1, sn1)";
        assertEquals(0,
                mc.compare(sn1, sn1)
                , __msg);
        __msg = "mc.compare(sn2, sn2)";
        assertEquals(0,
                mc.compare(sn2, sn2)
                , __msg);
        __msg = "mc.compare(sn1, sn2) < 0";
        assertTrue(mc.compare(sn1, sn2) < 0, __msg);
        __msg = "mc.compare(sn2, sn1) > 0";
        assertTrue(mc.compare(sn2, sn1) > 0, __msg);
        __msg = "mc.compare(sn1, sn3) > 0";
        assertTrue(mc.compare(sn1, sn3) > 0, __msg);
        __msg = "mc.compare(sn3, sn1) < 0";
        assertTrue(mc.compare(sn3, sn1) < 0, __msg);
    }

    @Test
    void snMySnComparatorTest2() {
        Subnet sn1 = new Subnet("224.0.0.0/8");
        Subnet sn2 = new Subnet("0.0.0.0/8");
        MySubnetComparator mc = new MySubnetComparator();
        String __msg = "Problem mit grossen Integers";
        assertTrue(mc.compare(sn1, sn2) > 0, __msg);
        assertTrue(mc.compare(sn2, sn1) < 0, __msg);
    }

    @Test
    void snMySnComparatorTest3() {
        TreeMap<Subnet, IPAddress> next = new TreeMap<>(new MySubnetComparator());
        IPAddress ip = new IPAddress("10.0.0.1");
        next.put(new Subnet("172.16.0.0/24"), ip);
        next.put(new Subnet("192.168.0.0/16"), ip);
        next.put(new Subnet("127.0.0.0/8"), ip);
        String __msg = "Treemap 1. Element";
        assertEquals(0x7f000000,
                ((Subnet)(next.keySet().toArray()[0])).getNet().getIP()
                , __msg);
        __msg = "Treemap 2. Element";
        assertEquals(0xac100000,
                ((Subnet)(next.keySet().toArray()[1])).getNet().getIP()
                , __msg);
    }

    @Test
    void snMySnComparator2Test1() {
        Subnet sn1 = new Subnet("172.16.0.0/24");
        Subnet sn2 = new Subnet("192.168.0.0/16");
        Subnet sn3 = new Subnet("127.0.0.0/8");
        Subnet sn4 = new Subnet("10.0.0.0/8");
        MySubnetComparator2 mc = new MySubnetComparator2();
        String __msg = "mc.compare(sn1, sn1)";
        assertEquals(0,
                mc.compare(sn1, sn1)
                , __msg);
        __msg = "mc.compare(sn1, sn2) < 0";
        assertTrue(mc.compare(sn1, sn2) < 0, __msg);
        __msg = "mc.compare(sn1, sn3) < 0";
        assertTrue(mc.compare(sn1, sn3) < 0, __msg);
        __msg = "mc.compare(sn4, sn3) < 0";
        assertTrue(mc.compare(sn4, sn3) < 0, __msg);
    }

    @Test
    void snMySnComparator2Test2() {
        TreeMap<Subnet, IPAddress> next = new TreeMap<>(new MySubnetComparator2());
        IPAddress ip = new IPAddress("10.0.0.1");
        next.put(new Subnet("172.16.0.0/24"), ip);
        next.put(new Subnet("192.168.0.0/16"), ip);
        next.put(new Subnet("127.0.0.0/8"), ip);
        String __msg = "Treemap 1. Element";
        assertEquals(0xac100000,
                ((Subnet)(next.keySet().toArray()[0])).getNet().getIP()
                , __msg);
        __msg = "Treemap 3. Element";
        assertEquals(0x7f000000,
                ((Subnet)(next.keySet().toArray()[2])).getNet().getIP()
                , __msg);
    }

    @Test
    void getNextHopTest1() {
        String __msg = "getNextHop1";
        TreeMap<Subnet, IPAddress> next = new TreeMap<>(new MySubnetComparator2());
        IPAddress ip = new IPAddress("193.36.189.1");
        next.put(new Subnet("172.16.0.0/16"), new IPAddress("192.168.1.253"));
        next.put(new Subnet("10.0.0.0/8"), new IPAddress("192.168.1.252"));
        next.put(new Subnet("10.16.0.0/16"), new IPAddress("192.168.1.251"));
        next.put(new Subnet("192.168.0.0/23"), new IPAddress("192.168.1.250"));
        next.put(new Subnet("127.0.0.0/8"), new IPAddress("192.168.1.249"));
        assertNull(ip.getNextHop(next));
    }

    @Test
    void getNextHopTest2() {
        String __msg = "getNextHop2";
        TreeMap<Subnet, IPAddress> next = new TreeMap<>(new MySubnetComparator2());
        IPAddress ip = new IPAddress("10.0.0.1");
        next.put(new Subnet("0.0.0.0/0"), new IPAddress("192.168.1.254"));
        next.put(new Subnet("172.16.0.0/16"), new IPAddress("192.168.1.253"));
        next.put(new Subnet("10.0.0.0/8"), new IPAddress("192.168.1.252"));
        next.put(new Subnet("10.16.0.0/16"), new IPAddress("192.168.1.251"));
        next.put(new Subnet("192.168.0.0/23"), new IPAddress("192.168.1.250"));
        next.put(new Subnet("127.0.0.0/8"), new IPAddress("192.168.1.249"));
        assertEquals(new IPAddress("192.168.1.252") , ip.getNextHop(next));
    }

    @Test
    void getNextHopTest3() {
        String __msg = "getNextHop3";
        TreeMap<Subnet, IPAddress> next = new TreeMap<>(new MySubnetComparator2());
        IPAddress ip = new IPAddress("10.16.0.1");
        next.put(new Subnet("0.0.0.0/0"), new IPAddress("192.168.1.254"));
        next.put(new Subnet("172.16.0.0/16"), new IPAddress("192.168.1.253"));
        next.put(new Subnet("10.0.0.0/8"), new IPAddress("192.168.1.252"));
        next.put(new Subnet("10.16.0.0/16"), new IPAddress("192.168.1.251"));
        next.put(new Subnet("192.168.0.0/23"), new IPAddress("192.168.1.250"));
        next.put(new Subnet("127.0.0.0/8"), new IPAddress("192.168.1.249"));
        assertEquals(new IPAddress("192.168.1.251") , ip.getNextHop(next));
    }


    @Test
    void getNextHopTest4() {
        String __msg = "getNextHop4";
        TreeMap<Subnet, IPAddress> next = new TreeMap<>(new MySubnetComparator2());
        IPAddress ip = new IPAddress("127.0.0.2");
        next.put(new Subnet("0.0.0.0/0"), new IPAddress("192.168.1.254"));
        next.put(new Subnet("172.16.0.0/16"), new IPAddress("192.168.1.253"));
        next.put(new Subnet("10.0.0.0/8"), new IPAddress("192.168.1.252"));
        next.put(new Subnet("10.16.0.0/16"), new IPAddress("192.168.1.251"));
        next.put(new Subnet("192.168.0.0/23"), new IPAddress("192.168.1.250"));
        next.put(new Subnet("127.0.0.0/8"), new IPAddress("192.168.1.249"));
        assertEquals(new IPAddress("192.168.1.249") , ip.getNextHop(next));
    }

    @Test
    void getNextHopTest5() {
        String __msg = "getNextHop5";
        TreeMap<Subnet, IPAddress> next = new TreeMap<>(new MySubnetComparator2());
        IPAddress ip = new IPAddress("192.168.45.33");
        next.put(new Subnet("0.0.0.0/0"), new IPAddress("192.168.1.254"));
        next.put(new Subnet("172.16.0.0/16"), new IPAddress("192.168.1.253"));
        next.put(new Subnet("10.0.0.0/8"), new IPAddress("192.168.1.252"));
        next.put(new Subnet("10.16.0.0/16"), new IPAddress("192.168.1.251"));
        next.put(new Subnet("192.168.0.0/23"), new IPAddress("192.168.1.250"));
        next.put(new Subnet("127.0.0.0/8"), new IPAddress("192.168.1.249"));
        assertEquals(new IPAddress("192.168.1.254") , ip.getNextHop(next));
    }

    @Test
    void getNextHopTest6() {
        String __msg = "getNextHop6";
        TreeMap<Subnet, IPAddress> next = new TreeMap<>(new MySubnetComparator2());
        IPAddress ip = new IPAddress("192.168.1.36");
        next.put(new Subnet("0.0.0.0/0"), new IPAddress("192.168.1.254"));
        next.put(new Subnet("172.16.0.0/16"), new IPAddress("192.168.1.253"));
        next.put(new Subnet("10.0.0.0/8"), new IPAddress("192.168.1.252"));
        next.put(new Subnet("10.16.0.0/16"), new IPAddress("192.168.1.251"));
        next.put(new Subnet("192.168.0.0/23"), new IPAddress("192.168.1.250"));
        next.put(new Subnet("127.0.0.0/8"), new IPAddress("192.168.1.249"));
        assertEquals(new IPAddress("192.168.1.250") , ip.getNextHop(next));
    }

    @Test
    void getNextHopTest7() {
        String __msg = "getNextHop7";
        TreeMap<Subnet, IPAddress> next = new TreeMap<>(new MySubnetComparator2());
        IPAddress ip = new IPAddress("192.168.0.9");
        next.put(new Subnet("0.0.0.0/0"), new IPAddress("192.168.1.254"));
        next.put(new Subnet("172.16.0.0/16"), new IPAddress("192.168.1.253"));
        next.put(new Subnet("10.0.0.0/8"), new IPAddress("192.168.1.252"));
        next.put(new Subnet("10.16.0.0/16"), new IPAddress("192.168.1.251"));
        next.put(new Subnet("192.168.0.0/23"), new IPAddress("192.168.1.250"));
        next.put(new Subnet("127.0.0.0/8"), new IPAddress("192.168.1.249"));
        assertEquals(new IPAddress("192.168.1.250") , ip.getNextHop(next));
    }

    @Test
    void getNextHopTest8() {
        String __msg = "getNextHop8";
        TreeMap<Subnet, IPAddress> next = new TreeMap<>(new MySubnetComparator2());
        IPAddress ip = new IPAddress("172.16.0.33");
        next.put(new Subnet("0.0.0.0/0"), new IPAddress("192.168.1.254"));
        next.put(new Subnet("172.16.0.0/16"), new IPAddress("192.168.1.253"));
        next.put(new Subnet("10.0.0.0/8"), new IPAddress("192.168.1.252"));
        next.put(new Subnet("10.16.0.0/16"), new IPAddress("192.168.1.251"));
        next.put(new Subnet("192.168.0.0/23"), new IPAddress("192.168.1.250"));
        next.put(new Subnet("127.0.0.0/8"), new IPAddress("192.168.1.249"));
        assertEquals(new IPAddress("192.168.1.253") , ip.getNextHop(next));
    }
}