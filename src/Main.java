public class Main {

    // Set rider arrival mean time to 2 seconds
    static float riderArrivalMean = 2f * 1000;
    // Set bus arrival mean time to 1 minutes
    static float busArrivalMean = 1 * 60f * 1000;

    public static void main(String[] args) {
        // Create rider generator to generate riders
        PassengerGenerator rG = new PassengerGenerator(riderArrivalMean);
        // Create bus generator to generate busses
        BusGenerator bG = new BusGenerator(busArrivalMean);

        // Create the generator threads
        Thread rGT = new Thread(rG);
        Thread bGT = new Thread(bG);

        // Start the generator threads
        rGT.start();
        bGT.start();
    }
}
