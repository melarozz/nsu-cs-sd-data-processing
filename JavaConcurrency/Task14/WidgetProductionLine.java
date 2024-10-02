package JavaConcurrency.Task14;

import java.util.concurrent.Semaphore;

public class WidgetProductionLine {

    // semaphores for controlling access to details A and B
    private static final Semaphore aSemaphore = new Semaphore(0);
    private static final Semaphore bSemaphore = new Semaphore(0);

    // semaphore for controlling access to detail C
    private static final Semaphore cSemaphore = new Semaphore(0);

    public static void main(String[] args) {
        Thread aThread = new Thread(new A());
        Thread bThread = new Thread(new B());
        Thread cThread = new Thread(new C());

        aThread.start();
        bThread.start();
        cThread.start();
    }

    static class A implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000); // making A detail takes 1 sec
                    System.out.println("Detail A is ready.");
                    aSemaphore.release(); // increasing the counter for A
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class B implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(2000); // making B detail takes 2 sec
                    System.out.println("Detail B is ready.");
                    bSemaphore.release(); // increasing the counter for B
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class C implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    aSemaphore.acquire(); // waiting for A detail
                    bSemaphore.acquire(); // waiting for B detail
                    Thread.sleep(3000); // making C detail takes 3 sec
                    System.out.println("Widget (Detail C) is ready.");
                    cSemaphore.release(); // increasing the counter for C
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

