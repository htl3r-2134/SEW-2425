public class Truck extends Vehicle{
    public Truck(int seats, int capacity, boolean hasTailLift) {
        this.seats = seats;
        this.capacity = capacity;
        super.hasTailLift = hasTailLift;
    }

    @Override
    public String toString() {
        String tailLift = "";
        if (hasTailLift) {
            tailLift += " kg, has tail lift";
        } else {
            tailLift += " kg, no tail lift";
        }
        return getClass().getSimpleName() + ": seats: " + seats + ", capacity: " + capacity + tailLift;
    }


}
