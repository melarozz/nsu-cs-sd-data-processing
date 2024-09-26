package JavaConcurrency.Task1;

public class MyThread {

    private static final int NUM_ITERATIONS = 10;
    public static void main(String[] args) {

        System.out.println("Main thread starts");

        Thread thread = new Thread(new NumberPrinterRunnable());
        thread.start(); // starting new thread, run() method executes

        // main thread loop continues executing, so 2 threads are working in parallel,
        // their messages can be displayed in any order, depending on how
        // the Java thread scheduler distributes the processor time
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            System.out.println("Main thread: Line #" + i);
        }

        System.out.println("Main thread finishes");
    }
}

class NumberPrinterRunnable implements Runnable {

    private static final int NUM_ITERATIONS = 10;
    @Override
    public void run() {
        System.out.println("New thread starts");

        for (int i = 0; i < NUM_ITERATIONS; i++) {
            System.out.println("New thread: Line #" + i);
        }

        System.out.println("New thread finishes");
    }
}
