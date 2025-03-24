import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Bridge {
    private static final int MAX_CARS_ON_BRIDGE = 10;
    private int carsOnBridge = 0;
    private final Queue<Car> waitingQueue = new LinkedList<>();

    public synchronized void enterBridge(Car car) throws InterruptedException {
        waitingQueue.add(car);
        long startTime = System.currentTimeMillis();
        while (waitingQueue.peek() != car || carsOnBridge >= MAX_CARS_ON_BRIDGE) {
            wait();
        }
        waitingQueue.poll();
        carsOnBridge++;
        car.setWaitTime(System.currentTimeMillis() - startTime);
        System.out.println("Car " + car.getId() + " is crossing the bridge. Cars on bridge: " + carsOnBridge);
    }

    public synchronized void exitBridge(Car car) {
        carsOnBridge--;
        System.out.println("Car " + car.getId() + " has left the bridge. Cars on bridge: " + carsOnBridge);
        notifyAll();
    }

    public synchronized int getCarsOnBridge() {
        return carsOnBridge;
    }

    public synchronized int getWaitingQueueSize() {
        return waitingQueue.size();
    }
}

class Car extends Thread {
    private final Bridge bridge;
    private long waitTime;

    public Car(Bridge bridge, int id) {
        super("Car-" + id);
        this.bridge = bridge;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public long getWaitTime() {
        return waitTime;
    }

    @Override
    public void run() {
        try {
            bridge.enterBridge(this);
            Thread.sleep(50000); // Simulate crossing time
            bridge.exitBridge(this);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class BridgeMonitor extends Thread {
    private final Bridge bridge;
    private final Queue<Long> waitTimes = new LinkedList<>();

    public BridgeMonitor(Bridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
                System.out.println("[Monitor] Cars on bridge: " + bridge.getCarsOnBridge());
                System.out.println("[Monitor] Queue length: " + bridge.getWaitingQueueSize());

                synchronized (this) {
                    long sum = 0;
                    for (long time : waitTimes) sum += time;
                    long avgWaitTime = waitTimes.isEmpty() ? 0 : sum / waitTimes.size();
                    System.out.println("[Monitor] Avg wait time (last 10 min): " + avgWaitTime + "ms");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

class CarGenerator extends Thread {
    private final Bridge bridge;
    private final Random random = new Random();
    private int carId = 0;

    public CarGenerator(Bridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((random.nextInt(5) + 1) * 1000);
                Car car = new Car(bridge, ++carId);
                car.start();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

public class Brückensimulation {
    public static void main(String[] args) {
        Bridge bridge = new Bridge();
        BridgeMonitor monitor = new BridgeMonitor(bridge);
        CarGenerator generator = new CarGenerator(bridge);

        monitor.start();
        generator.start();
    }
}
