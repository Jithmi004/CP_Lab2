import java.util.Random;

public class PassengerSpawner implements Runnable {
    static Random random;
    float riderArrivalMean;

    PassengerSpawner(float riderArrivalMean) {
        this.riderArrivalMean = riderArrivalMean;
        random = new Random();
    }

    @Override
    public void run() {
        while (true) {

            // Create passenger
            Passenger passenger = new Passenger(Passenger.totPassengerId);
            Thread passengerThread = new Thread(passenger);

            // Start rider thread
            passengerThread.start();
            try {
                // Sleep to obtain the specific inter arrival time mean
                Thread.sleep(this.calcRiderSleepTime(riderArrivalMean, random));
                Passenger.incrementPassengerId();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Calculate thread sleeping time
    private long calcRiderSleepTime(float riderArrivalMean, Random random) {
        float lambda = 1 / riderArrivalMean;
        return Math.round(-Math.log(1 - random.nextFloat()) / lambda);
    }
}