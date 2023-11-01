public class Bus implements Runnable {
    int busIndex;

    Bus(int busIndex) {
        this.busIndex = busIndex;
    }

    @Override
    public void run() {
        try {

            // Allow only the riders who were there when the bus arrived to board the bus
            BusStop.mutex.acquire();
            System.out.println("Bus arrived at the station. " + BusStop.riders + " riders waiting to board the bus");


            if (BusStop.riders > 0) {
                // Awake a rider waiting to board the bus
                BusStop.busArrived.release();

                // Sleep until all waiting riders have boarded the bus
                BusStop.fullyBoarded.acquire();
            }
            else {
                System.out.println("Bus leaving because 0 riders in bus stop");
            }

            // Allow other riders to wait for the next bus and depart
            BusStop.mutex.release();
            depart();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void depart() {
        System.out.println("Bus " + this.busIndex + " departed");
    }

}