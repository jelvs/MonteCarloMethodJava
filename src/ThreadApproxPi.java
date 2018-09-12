import java.util.Random;

public class ThreadApproxPi {

    private final static double RADIUS = 1.0;

    public static void main(String[] args) {

        if(args.length != 1){
            System.out.println("Usage:e $ java ApproxPi numb of Tries");
            System.exit(1);
        }

        System.out.println("PI " + approxPi(Integer.parseInt(args[0])));
    }

    private static double approxPi(int numbOfTries) {
        double total = 0;
        double   inside = 0;
        Random r = new Random();

        for(int i = 0; i < numbOfTries; i++){

            double xCoord = generateRandom(r);
            double yCoord = generateRandom(r);

            System.out.println("x: " + xCoord + " | " + "y: " + yCoord);
            double distance = calcDtoPoint(xCoord, yCoord);
            total++;

            if (distance <= 1.0) {
                inside++;
            }
        }
        return (inside/total) * 4;

    }





    private static double generateRandom(Random r){
        double min = 0;
        return min + (RADIUS - min) * r.nextDouble();
    }

    private static double calcDtoPoint(double xCoord, double yCoord){
        return Math.sqrt((xCoord * xCoord) + (yCoord * yCoord));
    }
}

    class ApproxPiThread implements Runnable {

        public void run(){

        }
    }
