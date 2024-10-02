package JavaConcurrency.Task7;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PiCalculator {

    // total number of iterations to calculate pi approximation
    private static final int ITERATIONS = 1000000000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of threads: ");

        int numThreads = scanner.nextInt();
        scanner.close();

        double pi = calculatePi(numThreads);

        System.out.println("Approximation of Pi: " + pi);
    }

    public static double calculatePi(int numThreads) {
        // create a fixed thread pool with the specified number of threads
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // array to store the partial sum results from each thread
        final double[] results = new double[numThreads];

        // calculate how many iterations each thread will perform
        int iterationsPerThread = ITERATIONS / numThreads;

        // loop through each thread and submit a task to calculate partial Pi
        for (int threadIndex = 0; threadIndex < numThreads; threadIndex++) {
            final int start = threadIndex * iterationsPerThread;  // starting index for this thread
            final int end = (threadIndex + 1) * iterationsPerThread;  // end index for this thread
            final int index = threadIndex;  // final index used to store the result for the thread

            // submit a task to the executor for Pi calculation in a separate thread
            executor.submit(() -> {
                double localSum = 0.0;

                // calculate the partial sum for this thread's range
                for (int i = start; i < end; i++) {
                    localSum += (Math.pow(-1, i) / (2 * i + 1));  // leibniz formula for Pi
                }

                // store the result in the results array
                results[index] = localSum;
            });
        }

        // shutdown the executor to prevent new tasks from being submitted
        executor.shutdown();

        try {
            // wait for all threads to complete their execution
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // sum up all the partial results from each thread to get the final value of Pi
        double pi = 0.0;
        for (double sum : results) {
            pi += sum;
        }

        // multiply by 4 as per the Leibniz formula for Pi
        return 4 * pi;
    }
}

//3,14159265358979323846

//3.141591653589781 1mil
//3.1415925535897427 10mil
//3.141592643589817 100mil
//3.1415926525892104 1bil