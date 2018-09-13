import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ThreadApproxPi {

    private final static double RADIUS = 1.0;


    private static volatile double total = 0;
    private static volatile double inside = 0;

    private static CountDownLatch latch;

    public static void main(String[] args) throws InterruptedException  {

        if (args.length != 2) {
            System.out.println("Usage:e $ java ApproxPi numb of Tries numb of Threads");
            System.exit(1);
        }


        int numberOfThreads = Integer.parseInt(args[1]);

        int numberOfTriesForEachThread = Integer.parseInt(args[0]) / numberOfThreads;


        //Set<Thread> threads = new HashSet();
        latch = new CountDownLatch(numberOfThreads);

        //Add new threads
        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(new ApproxPiThread(numberOfTriesForEachThread)).start();
        }


        latch.await();

        System.out.println("Approximation of Pi " + (inside / total) * 4);
    }

    private synchronized static void incrementTotal(int num) {
        total = total + num;
    }

    private synchronized static void incrementInside(int num) {
        inside = inside + num;
    }


    @SuppressWarnings("Duplicates")
    static class ApproxPiThread implements Runnable {

        private int numberOfTries;
        private int currentTotal;
        private int currentInside;

        public ApproxPiThread(int numberOfTries) {
            this.numberOfTries = numberOfTries;
            this.currentTotal = 0;
            this.currentInside = 0;
        }

        @Override
        public void run() {
            try {
                approxPi(numberOfTries);
            } finally {
                latch.countDown();
            }
        }

        private void approxPi(int numbOfTries) {

            Random r = new Random();

            for (int i = 0; i < numbOfTries; i++) {

                double xCoord = generateRandom(r);
                double yCoord = generateRandom(r);

                System.out.println("x: " + xCoord + " | " + "y: " + yCoord);
                double distance = calcDtoPoint(xCoord, yCoord);
                currentTotal++;

                if (distance <= 1.0) {
                    inside++;
                }
            }
            incrementTotal(currentTotal);
            incrementInside(currentInside);


        }


        private static double generateRandom(Random r) {
            double min = 0;
            return min + (RADIUS - min) * r.nextDouble();
        }

        private static double calcDtoPoint(double xCoord, double yCoord) {
            return Math.sqrt((xCoord * xCoord) + (yCoord * yCoord));
        }


    }

}

