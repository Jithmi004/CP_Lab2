public class Passenger implements Runnable {
    int riderIndex;

    Passenger(int riderIndex) {
        this.riderIndex = riderIndex;
    }

    @Override
    public void run() {
        try {
            // Enter the bus stop. Only 50 can enter the bus stop at a time
            BusStop.inWaiting.acquire();
            BusStop.mutex.acquire();
            System.out.println("Rider Number: " + this.riderIndex + " entered the bus stop");

            // If bus has not arrived, increment the number of riders waiting to board the bus
            // If a bus has arrived at the bus stop, the thread won't be able to get this mutex as the bus has it

            BusStop.riders++;
            BusStop.mutex.release();

            // Sleep till the bus arrive
            BusStop.busArrived.acquire();

            // When the bus arrives one rider will be awakened. He enters the bus and allow one more rider to enter the bus
            BusStop.inWaiting.release();
            board_bus();

            // No need to lock this section as only one thread can go to this area at a time
            BusStop.riders--;

            if (0 == BusStop.riders) {
                System.out.println("All riders got in. Bus departing...");
                // If all riders have boarded, wake the bus thread to depart
                BusStop.fullyBoarded.release();
            }
            else {
                // When he boards the bus, allow one more rider to board the bus by releasing this semaphoer once
                BusStop.busArrived.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void board_bus() {
        System.out.println("Rider Number: " + this.riderIndex + " boarded");
    }

}