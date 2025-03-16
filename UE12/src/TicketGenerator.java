public class TicketGenerator {
    /*
    private static int ticketnumber;
    public static void generateTicket() {
        ticketnumber++;
        if (ticketnumber < 1000000) {
            Ticket1 ticket = new Ticket1(ticketnumber);
        }
    }

     */
    private static int counter = 0;
    private static final Object lock = new Object();

    public static Ticket generateTicket(int tollboothId) {
        synchronized (lock) {
            return new Ticket(++counter, tollboothId);
        }
    }
}
