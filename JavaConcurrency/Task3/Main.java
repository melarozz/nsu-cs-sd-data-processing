package JavaConcurrency.Task3;

public class Main {

    public static void main(String[] args) {

        System.out.println("Main thread starts");

        String[] sequence1 = {"Line 1 ", "Line 2 ", "Line 3 "};

        // creating 4 Thread objects, start eaach of them, the run() method executes in each of them
        // all threads will work in parallel
        Thread thread1 = new Thread(new StringPrinterRunnable(sequence1.clone(), 1));
        sequence1[0]="Hello 1";
        Thread thread2 = new Thread(new StringPrinterRunnable(sequence1.clone(), 2));
        sequence1[0]="Hello 2";
        Thread thread3 = new Thread(new StringPrinterRunnable(sequence1.clone(), 3));
        sequence1[0]="Hello 3";
        Thread thread4 = new Thread(new StringPrinterRunnable(sequence1.clone(), 4));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        System.out.println("Main thread finishes");
    }
}

class StringPrinterRunnable implements Runnable {

    private final String[] sequence;
    private final Integer number;

    public StringPrinterRunnable(String[] sequence, Integer number) {
        this.sequence = sequence;
        this.number = number;
    }

    @Override
    public void run() {
        for (String line : sequence) {
            System.out.println(line + " thread " + number.toString());
        }
    }
}
