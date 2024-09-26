package JavaConcurrency.Task4;

public class Main {

    public static void main(String[] args) {
        System.out.println("Main thread starts");

        Thread childThread = new Thread(new StringPrinterRunnable());
        childThread.start();

        // main thread 2 sec sleep before interrupting child
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread interrupts child thread.");
        childThread.interrupt();

        System.out.println("Main thread finishes");
    }
}

class StringPrinterRunnable implements Runnable {

    private static final int NUM_ITERATIONS = 1000;

    @Override
    public void run() {
        System.out.println("Child thread starts");

        for (int i = 0; i < NUM_ITERATIONS; i++) {

            System.out.println("Child thread: Line #" + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Child thread interrupted during sleep");
                break;
            }
        }

        System.out.println("Child thread finishes");
    }
}

