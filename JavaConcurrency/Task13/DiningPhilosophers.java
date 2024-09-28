package JavaConcurrency.Task13;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers {

    static class Fork {
        private final Lock lock = new ReentrantLock(); // lock for mutual exclusion
        private final Condition condition = lock.newCondition(); // condition for signaling that fork iss available
        private boolean isAvailable = true; // indicates if the fork is currently available

        public boolean pickUp() {
            lock.lock(); // acquire the lock
            try {
                // wait till the fork is available
                while (!isAvailable) {
                    condition.await(); // releases the lock and waits until signaled
                }
                isAvailable = false; // mark the fork as unavailable
                return true;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            } finally {
                lock.unlock(); // release the lock after fork is picked up
            }
        }

        public void putDown() {
            lock.lock(); // acquire the lock
            try {
                isAvailable = true; // mark the fork as available
                condition.signal(); // signal any waiting philosophers that the fork is now available
            } finally {
                lock.unlock(); // release the lock
            }
        }
    }

    static class Philosopher extends Thread {
        private final Fork leftFork;
        private final Fork rightFork;
        private final Lock forks = new ReentrantLock(); // lock to handle access to both forks
        private final Condition condition = forks.newCondition(); // condition to signal when forks are available

        public Philosopher(String name, Fork leftFork, Fork rightFork) {
            super(name);
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        @Override
        public void run() {
            while (true) {
                think(); // philosopher is thinking
                try {
                    forks.lock(); // lock the access to two forks

                    // try to pick up both forks
                    while (!leftFork.pickUp() || !rightFork.pickUp()) {
                        // if one fork is picked up, put it down if the other isn't available
                        if (leftFork.pickUp()) {
                            leftFork.putDown();
                        }
                        if (rightFork.pickUp()) {
                            rightFork.putDown();
                        }
                        condition.await(); // wait for both forks to be available
                    }
                    eat(); // philosopher is eating after picking up both forks
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    leftFork.putDown(); // put down left fork after eating
                    rightFork.putDown(); // put down right fork after eating
                    condition.signalAll(); // signal other philosophers that forks are available
                    forks.unlock(); // release the lock on forks
                }
            }
        }

        private void think() {
            System.out.println(getName() + " is thinking.");
            try {
                Thread.sleep((long) (Math.random() * 1000)); // random sleep time to simulate thinking
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void eat() {
            System.out.println(getName() + " is eating.");
            try {
                Thread.sleep((long) (Math.random() * 1000)); // random sleep time to simulate eating
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        // create 5 forks, one for each philosopher
        Fork fork1 = new Fork();
        Fork fork2 = new Fork();
        Fork fork3 = new Fork();
        Fork fork4 = new Fork();
        Fork fork5 = new Fork();

        // create 5 philosophers, each with access to two forks
        Philosopher philosopher1 = new Philosopher("Aristotle", fork1, fork2);
        Philosopher philosopher2 = new Philosopher("Kant", fork2, fork3);
        Philosopher philosopher3 = new Philosopher("Spinoza", fork3, fork4);
        Philosopher philosopher4 = new Philosopher("Marx", fork4, fork5);
        Philosopher philosopher5 = new Philosopher("Russell", fork5, fork1);

        // start the philosopher threads
        philosopher1.start();
        philosopher2.start();
        philosopher3.start();
        philosopher4.start();
        philosopher5.start();
    }
}
