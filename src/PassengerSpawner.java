import java.util.Random;

public class PassengerSpawner implements Runnable {
    static Random rand;
    float meanArrivalTime;

    PassengerSpawner(float meanArrivalTime) {
        this.meanArrivalTime = meanArrivalTime;
        rand = new Random();
    }

    @Override
    public void run() {
        while (true) {

            // Spawn a passenger
            Passenger newPassenger = new Passenger(Passenger.totPassengerId);
            Thread passengerThread = new Thread(newPassenger);
            passengerThread.start();

            try {
                // Sleeping to achieve the desired mean inter-arrival time.
                Thread.sleep(this.calculatePassengerSleepTime(rand, meanArrivalTime));
                Passenger.incrementPassengerId();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Calculates the sleep time for the passenger generation thread.

    private long calculatePassengerSleepTime(Random randVal, float meanArrivalTime) {
        // Calculate the arrival rate from the mean arrival time
        float lambda = 1 / meanArrivalTime;

        // Generate a random sleep time using exponential distribution
        float randomFloat = randVal.nextFloat();
        double negativeLog = -Math.log(1 - randomFloat);
        return Math.round(negativeLog / lambda);
    }
}