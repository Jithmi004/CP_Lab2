import java.util.Random;

public class BusSpawner implements Runnable {
    static Random rand;
    float meanArrivalTime;

    BusSpawner(float meanArrivalTime) {
        this.meanArrivalTime = meanArrivalTime;
        rand = new Random();
    }

    @Override
    public void run() {
        while (true) {

            // Spawn a new bus
            Bus newBus = new Bus(Bus.totBusId);
            Thread newBusThread = new Thread(newBus);
            newBusThread.start();

            try {
                // Sleeping to achieve the desired mean inter-arrival time.
                Thread.sleep(this.calculateBusSleepTime(rand, meanArrivalTime));
                Bus.incrementBusId();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Calculates the sleep time for the bus generation thread.

    private long calculateBusSleepTime(Random randVal, float meanArrivalTime) {
        // Calculate the arrival rate from the mean arrival time
        float lambda = 1 / meanArrivalTime;

        // Generate a random sleep time using exponential distribution
        float randomFloat = randVal.nextFloat();
        double negativeLog = -Math.log(1 - randomFloat);
        return Math.round(negativeLog / lambda);
    }

}