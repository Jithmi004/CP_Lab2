public class Bus implements Runnable {
    // Count of total bus threads generated
    public static int totBusId = 1;
    // Id of the bus
    int busId;

    Bus(int busId) {
        this.busId = busId;
    }

    @Override
    public void run() {
        try {
            /*
             * Passenger count mutex is acquired to ensure that only the passengers who were
             * present when the bus arrived are allowed to board the bus.
             */
            BusStop.passengerCountMutex.acquire();
            announseArrival();
            /*
             * Handles the bus boarding process, ensuring that waiting passengers are
             * allowed to board if there are any, and the bus leaves if there are no
             * passengers
             * at the bus stop.
             */
            if (BusStop.passengerCount > 0) {
                BusStop.boardBusSemaphore.release();
                BusStop.busFullSemaphore.acquire();
            } else {
                System.out.println("Bus will leave since 0 passengers are waiting");
            }

            /*
             * The bus releases the mutex to allow other passengers to enter the waiting
             * area or to accommodate another bus.
             */

            BusStop.passengerCountMutex.release();
            announceDeparture();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void announceDeparture() {
        System.out.println("Bus No: " + this.busId + " departed.");
    }

    private void announseArrival() {
        System.out.println("Bus No: " + this.busId + " arrived at the bus stop and " + BusStop.passengerCount
                + " passengers are waiting to board.");
    }

    // Increment the total bus count
    public static void incrementBusId() {
        Bus.totBusId++;
    }

}