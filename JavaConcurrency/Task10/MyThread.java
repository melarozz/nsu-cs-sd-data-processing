package JavaConcurrency.Task10;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread {

    // mutex for synchronization
    static final Lock mutex = new ReentrantLock();
    // flag for determining the turn
    static boolean isParentTurn = true;

    public static void main(String[] args) {

        System.out.println("Main thread starts");

        Thread thread = new Thread(new MyRunnable());
        thread.start();

        int NUM_ITERATIONS = 10;
        // main thread loop
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            synchronized (mutex) {
                // check if it's the child's turn
                while (!isParentTurn) {
                    try {
                        mutex.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                System.out.println("Main thread: Line #" + i);
                isParentTurn = false;
                mutex.notify();
            }
        }

        // wait till end of child thread
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
            synchronized (MyThread.mutex) {
                // check if it's parent's turn
                while (MyThread.isParentTurn) {
                    try {
                        MyThread.mutex.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                System.out.println("New thread: Line #" + i);
                MyThread.isParentTurn = true;
                MyThread.mutex.notify();
            }
        }

        System.out.println("New thread finishes");
    }
}

