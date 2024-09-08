package JavaConcurrency.Task3;

public class Main {

    public static void main(String[] args) {

        System.out.println("Main thread starts");

        String[] sequence1 = {"Thread 1 - Line 1", "Thread 1 - Line 2", "Thread 1 - Line 3"};
        String[] sequence2 = {"Thread 2 - Line 1", "Thread 2 - Line 2", "Thread 2 - Line 3"};
        String[] sequence3 = {"Thread 3 - Line 1", "Thread 3 - Line 2", "Thread 3 - Line 3"};
        String[] sequence4 = {"Thread 4 - Line 1", "Thread 4 - Line 2", "Thread 4 - Line 3"};

        // creating 4 Thread objects, start eaach of them, the run() method executes in each of them
        // all threads will work in parallel
        Thread thread1 = new Thread(new MyRunnable(sequence1));
        Thread thread2 = new Thread(new MyRunnable(sequence2));
        Thread thread3 = new Thread(new MyRunnable(sequence3));
        Thread thread4 = new Thread(new MyRunnable(sequence4));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        System.out.println("Main thread finishes");
    }
}

class MyRunnable implements Runnable {

    private final String[] sequence;

    public MyRunnable(String[] sequence) {
        this.sequence = sequence;
    }

    @Override
    public void run() {
        for (String line : sequence) {
            System.out.println(line);
        }
    }
}
