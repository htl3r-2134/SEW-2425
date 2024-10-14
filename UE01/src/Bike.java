public class Bike extends Vehicle{

    public Bike (int seats) {
        this.seats = seats;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + ": seats: " + seats;
    }
}
