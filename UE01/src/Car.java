public class Car extends Vehicle{

    public Car (int seats, int capacity) {
        this.seats = seats;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": seats: " + seats + ", capacity: " + capacity + " kg";
    }
}
