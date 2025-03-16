import java.util.*;
import java.util.concurrent.*;

// Klasse für ein Auto
class Car {
    private final int id;

    public Car(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

// Klasse für ein Ticket
class Ticket {
    private final int ticketNumber;
    private final int tollboothId;

    public Ticket(int ticketNumber, int tollboothId) {
        this.ticketNumber = ticketNumber;
        this.tollboothId = tollboothId;
    }

    @Override
    public String toString() {
        return "Ticket: " + ticketNumber + " | Tollbooth: " + tollboothId;
    }
}

// Ticket-Generator
class TicketGenerator1 {
    private static int counter = 1;
    public static synchronized Ticket generateTicket(int tollboothId) {
        return new Ticket(counter++, tollboothId);
    }
}

// Mautstelle als Thread
class Tollbooth implements Runnable {
    private final int id;
    private final Queue<Car> queue = new LinkedList<>();
    private final Random random = new Random();
    private final Object lock = new Object();

    public Tollbooth(int id) {
        this.id = id;
    }

    public void addCar(Car car) {
        synchronized (lock) {
            queue.add(car);
        }
    }

    public int getQueueSize() {
        synchronized (lock) {
            return queue.size();
        }
    }

    @Override
    public void run() {
        while (true) {
            Car car;
            synchronized (lock) {
                car = queue.poll();
            }
            if (car != null) {
                int processingTime = 2000 + random.nextInt(18000);
                try {
                    Thread.sleep(processingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Ticket ticket = TicketGenerator.generateTicket(id);
                System.out.println("Car " + car.getId() + " processed at Tollbooth " + id + ", " + ticket);
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

// Klasse zur Überwachung der Mautstellen
class TollboothMonitor implements Runnable {
    private final List<Tollbooth> tollbooths;

    public TollboothMonitor(List<Tollbooth> tollbooths) {
        this.tollbooths = tollbooths;
    }

    @Override
    public void run() {
        Map<Integer, Integer> lastSizes = new HashMap<>();
        while (true) {
            int sum = 0, max = 0, min = Integer.MAX_VALUE;
            for (Tollbooth t : tollbooths) {
                int size = t.getQueueSize();
                sum += size;
                max = Math.max(max, size);
                min = Math.min(min, size);
                int lastSize = lastSizes.getOrDefault(t.hashCode(), 0);
                System.out.println("Tollbooth " + t.hashCode() + " Queue: " + size + " (Last: " + lastSize + ", Change: " + (size - lastSize) + ")");
                lastSizes.put(t.hashCode(), size);
            }
            double avg = sum / (double) tollbooths.size();
            System.out.println("Monitor: Avg: " + avg + ", Max: " + max + ", Min: " + min);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Hauptklasse für die Simulation
public class TollboothSimulation {
    public static void main(String[] args) {
        int numTollbooths = 7;
        List<Tollbooth> tollbooths = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(numTollbooths + 1);
        Random random = new Random();

        for (int i = 0; i < numTollbooths; i++) {
            Tollbooth tollbooth = new Tollbooth(i + 1);
            tollbooths.add(tollbooth);
            executor.execute(tollbooth);
        }

        executor.execute(new TollboothMonitor(tollbooths));

        int carId = 1;
        while (true) {
            try {
                Thread.sleep(500 + random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Car car = new Car(carId++);
            Tollbooth tollbooth = tollbooths.get(random.nextInt(numTollbooths));
            tollbooth.addCar(car);
        }
    }
}
