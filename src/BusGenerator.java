import java.util.Random;

public class BusGenerator implements Runnable {
    static Random random;
    float busArrivalMean;

    BusGenerator(float busArrivalMean) {
        this.busArrivalMean = busArrivalMean;
        random = new Random();
    }

    @Override
    public void run() {
        while (true) {

            // Generate new bus
            Bus driver = new Bus(BusStop.busIndex);
            Thread driverThread = new Thread(driver);

            // Start bus thread
            driverThread.start();

            try {
                // Sleep to obtain the specific inter arrival time mean
                Thread.sleep(this.calcBusSleepTime(busArrivalMean, random));
                BusStop.busIndexIncrement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Calculate thread sleep time
    private long calcBusSleepTime(float busArrivalMean, Random random) {
        float lambda = 1 / busArrivalMean;
        return Math.round(-Math.log(1 - random.nextFloat()) / lambda);
    }

}