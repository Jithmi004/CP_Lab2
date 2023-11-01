import java.util.concurrent.Semaphore;

public class BusStop {

    // number of passengers waiting to board the bus
    public static int passengerCount = 0;

    // Mutex to lock the passengerCount variable
    public static Semaphore passengerCountMutex = new Semaphore(1);

    /*
     * This semaphore controls the entry of passengers into the bus stop. If there
     * are already 50 passengers inside, the next thread will be blocked from
     * entering.
     */
    public static Semaphore enterBusStopSemaphore = new Semaphore(50);

    /*
     * This semaphore is used to signal whether a passenger can board the bus.
     * They can only board when the bus arrives at the bus stop.
     */

    public static Semaphore boardBusSemaphore = new Semaphore(0);

    /*
     * This semaphore is used to signal whether all waiting passengers have boarded
     * the bus.
     */

    public static Semaphore busFullSemaphore = new Semaphore(0);
}
