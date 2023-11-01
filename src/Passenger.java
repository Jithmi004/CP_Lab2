public class Passenger implements Runnable {
    // Index of the current passenger
    public static int totPassengerId = 1;
    int passengerId;

    Passenger(int passengerId) {
        this.passengerId = passengerId;
    }

    @Override
    public void run() {
        try {
            // Enter the bus stop. Only 50 can enter the bus stop at a time
            BusStop.enterBusStopSemaphore.acquire();

            // If bus has not arrived, increment the number of riders waiting to board the
            // bus
            // If a bus has arrived at the bus stop, the thread won't be able to get this
            // mutex as the bus has it
            BusStop.passengerCountMutex.acquire();
            System.out.println("Rider Number: " + this.passengerId + " entered the bus stop");
            BusStop.passengerCount++;
            BusStop.passengerCountMutex.release();

            // Sleep till the bus arrive
            BusStop.boardBusSemaphore.acquire();

            // When the bus arrives one rider will be awakened. He enters the bus and allow
            // one more rider to enter the bus
            BusStop.enterBusStopSemaphore.release();
            board_bus();

            // No need to lock this section as only one thread can go to this area at a time
            BusStop.passengerCount--;

            if (0 == BusStop.passengerCount) {
                System.out.println("All riders got in. Bus departing...");
                // If all riders have boarded, wake the bus thread to depart
                BusStop.busFullSemaphore.release();
            } else {
                // When he boards the bus, allow one more rider to board the bus by releasing
                // this semaphoer once
                BusStop.boardBusSemaphore.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void board_bus() {
        System.out.println("Rider Number: " + this.passengerId + " boarded");
    }

    // Increment rider index
    public static void incrementPassengerId() {
        Passenger.totPassengerId++;
    }

}