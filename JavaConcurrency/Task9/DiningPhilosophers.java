package JavaConcurrency.Task9;

import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    static class Fork {
        private final ReentrantLock mutex = new ReentrantLock();

        // try to pick up the fork, returns true if successful
        public boolean pickUp() {
            return mutex.tryLock();
        }

        // puts down the fork, releasing the lock
        public void putDown() {
            mutex.unlock();
        }
    }

    static class Philosopher extends Thread {
        private final Fork leftFork;
        private final Fork rightFork;

        public Philosopher(String name, Fork leftFork, Fork rightFork) {
            super(name);
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        @Override
        public void run() {
            while (true) {
                think(); // philosopher thinks
                // attempt to pick up left fork
                if (leftFork.pickUp()) {
                    // attempt to pick up right fork
                    if (rightFork.pickUp()) {
                        eat(); // philosopher eats
                        rightFork.putDown(); // release right fork
                    }
                    leftFork.putDown(); // release left fork
                }
            }
        }

        private void think() {
            System.out.println(getName() + " is thinking.");
            try {
                // simulate thinking time
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void eat() {
            System.out.println(getName() + " is eating.");
            try {
                // simulate eating time
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println(getName() + " done eating.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        // create forks for each philosopher
        Fork fork1 = new Fork();
        Fork fork2 = new Fork();
        Fork fork3 = new Fork();
        Fork fork4 = new Fork();
        Fork fork5 = new Fork();

        // create philosophers with their respective forks
        Philosopher philosopher1 = new Philosopher("Aristotle", fork1, fork2);
        Philosopher philosopher2 = new Philosopher("Kant", fork2, fork3);
        Philosopher philosopher3 = new Philosopher("Spinoza", fork3, fork4);
        Philosopher philosopher4 = new Philosopher("Marx", fork4, fork5);
        Philosopher philosopher5 = new Philosopher("Russell", fork5, fork1);

        // start each philosopher's thread
        philosopher1.start();
        philosopher2.start();
        philosopher3.start();
        philosopher4.start();
        philosopher5.start();
    }
}