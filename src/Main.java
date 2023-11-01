public class Main {

    /*
     * Inter-arrival times for buses and riders are exponentially distributed with a
     * mean of 20 minutes and 30 seconds.
     */

    static float busArrivalMean = 20 * 60f * 1000;
    static float passengerArrivalMean = 30f * 1000;

    public static void main(String[] args) {
        /*
         * BusSpawner and PassengerSpawner runnable instances wil be created to spawn
         * busses and passengers
         */
        PassengerSpawner passengerSpawner = new PassengerSpawner(passengerArrivalMean);
        BusSpawner busSpawner = new BusSpawner(busArrivalMean);

        // Spawner threads are created and started
        Thread busSpawnerThread = new Thread(busSpawner);
        Thread passengerSpawnerThread = new Thread(passengerSpawner);
        busSpawnerThread.start();
        passengerSpawnerThread.start();

    }
}
