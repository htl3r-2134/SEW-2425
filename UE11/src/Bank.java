class Konto {
    int kontostand;

    public Konto() {
        this.kontostand = 0;
    }

    public int getKontostand() {
        return kontostand;
    }

    public void setKontostand(int kontostand) {
        this.kontostand = kontostand;
    }

    public synchronized void add(int betrag) {
        int wert = getKontostand();
        wert = wert + betrag;
        setKontostand(wert);
    }
}

class Ueberweiser implements Runnable {
    Konto from;
    Konto to;
    int Anzahl;
    int Betrag;

    public Ueberweiser(Konto from, Konto to, int Anzahl, int Betrag) {
        this.from = from;
        this.to = to;
        this.Anzahl = Anzahl;
        this.Betrag = Betrag;
    }

    public void run(){
        for (int i = 0; i < Anzahl; i++) {
            from.add(-Betrag);
            to.add(Betrag);

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Bank {
    private static final int anzahl = 1000;
    public static void main(String[] args) {
        Konto a = new Konto();
        Konto b = new Konto();
        Konto c = new Konto();

        Runnable r1 = new Ueberweiser(a, b, anzahl, 50);
        Runnable r2 = new Ueberweiser(b, c, anzahl, 50);
        Runnable r3 = new Ueberweiser(c, a, anzahl, 50);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r3);

        Long startTime = System.currentTimeMillis();

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Long endTime = System.currentTimeMillis();
        Long duration = endTime - startTime;

        System.out.println("Konto a: " + a.getKontostand());
        System.out.println("Konto b: " + b.getKontostand());
        System.out.println("Konto c: " + c.getKontostand());
        System.out.println(duration);

        // Dauer der Überweisung
        // Dauer: 113ms
        // Dauer Überweiser Threads: 97ms
        // Dauer synchronized: 5071 ms
        // Dauer Runnable: 4593 ms
        // Dauer mit Thread.sleep und Anzahl = 1000: 1660 ms
    }
}
