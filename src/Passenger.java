public class Passenger implements Runnable {
    // Count of total passengerThreads generated
    public static int totPassengerId = 1;
    int passengerId;

    Passenger(int passengerId) {
        this.passengerId = passengerId;
    }

    @Override
    public void run() {
        try {
            /*
             * Passenger attempts to enter the bus stop, with a limit of 50 riders at a
             * time.
             */
            BusStop.enterBusStopSemaphore.acquire();

            /*
             * Entering the boarding area if a bus has not yet arrived and increasing the
             * count of passengers.
             */

            BusStop.passengerCountMutex.acquire();
            System.out.println("Passenger : " + this.passengerId + " entered the bus stop.");
            BusStop.passengerCount++;

            // Releasing the mutex for a bus or for another waiting passenger
            BusStop.passengerCountMutex.release();

            // Acquiring the semaphore to board the bus.
            BusStop.boardBusSemaphore.acquire();
            // Releasing the bus stop semaphore and boarding the bus
            BusStop.enterBusStopSemaphore.release();
            boardBus();
            BusStop.passengerCount--;

            /*
             * If all passengers are boarded, release busFullSemaphore to allow the bus to
             * leave. If there are additional passengers waiting, release boardBussemaphore
             * to allow them to board the bus.
             */
            if (BusStop.passengerCount == 0) {
                System.out.println("All passengers in the boarding area entered the bus. Bus will be leaving.");
                BusStop.busFullSemaphore.release();
            } else {
                BusStop.boardBusSemaphore.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void boardBus() {
        System.out.println("Passenger No: " + this.passengerId + " boarded the bus.");
    }

    // Increment total passenger count
    public static void incrementPassengerId() {
        Passenger.totPassengerId++;
    }

}