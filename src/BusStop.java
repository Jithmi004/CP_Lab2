import java.util.concurrent.Semaphore;

public class BusStop {
    // Index of the current rider
    public static int riderIndex = 1;

    // Index of the current bus
    public static int busIndex = 1;

    // Shared variable to hold the number of riders waiting to board the bus
    public static int riders = 0;

    // Mutex used to protect riders variable
    public static Semaphore mutex = new Semaphore(1);

    // Semaphore to allow riders to enter the bus stop. If more than 50 are in the bus stop, next thread can't enter
    public static Semaphore inWaiting = new Semaphore(50);

    // Semaphore to signal whether a person can board the bus(Can only board when the bus arrives at the bus stop)
    public static Semaphore busArrived = new Semaphore(0);

    // Semaphore to signal whether all waiting riders has boarded the bus
    public static Semaphore fullyBoarded = new Semaphore(0);

    // Increment rider index
    public static void riderIndexIncrement() {
        BusStop.riderIndex++;
    }

    // Increment bus index
    public static void busIndexIncrement() {
        BusStop.busIndex++;
    }
}
