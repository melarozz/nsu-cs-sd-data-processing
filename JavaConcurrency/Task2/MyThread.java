package JavaConcurrency.Task2;

public class MyThread {

    public static void main(String[] args) {

        System.out.println("Main thread starts");

        Thread thread = new Thread(new MyRunnable());
        thread.start(); // we start child thread, run() method executes

        // waiting for child termination, main thread stops in awaiting of child's run() termination
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int NUM_ITERATIONS = 10;
        // main thread loop after child's termination
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            System.out.println("Main thread: Line #" + i);
        }

        System.out.println("Main thread finishes");
    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("New thread starts");

        int NUM_ITERATIONS = 10;
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            System.out.println("New thread: Line #" + i);
        }

        System.out.println("New thread finishes");
    }
}
