package JavaConcurrency.Task5;

public class Main {

    public static void main(String[] args) {
        System.out.println("Main thread starts");

        Thread childThread = new Thread(new MyRunnable());
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

class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("Child thread starts");

        try {
            for (int i = 0; i < 10; i++) {
                // check interruption
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Child thread is interrupted. Preparing to finish...");
                    break;
                }

                System.out.println("Child thread: Line #" + i);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Child thread interrupted during sleep. Preparing to finish...");
        }

        System.out.println("Child thread finishes");
    }
}
