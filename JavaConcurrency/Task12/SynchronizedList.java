package JavaConcurrency.Task12;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SynchronizedList {

    // lock object for synchronizing access to the list
    private static final Object LIST_LOCK = new Object();
    private static final LinkedList<String> sharedList = new LinkedList<>();

    public static void main(String[] args) {
        // starting the thread that will periodically sort the list
        Thread sorterThread = new Thread(new ListSorter());
        sorterThread.start();

        Scanner scanner = new Scanner(System.in);

        // main loop for user input
        while (true) {
            System.out.println("Enter a string (or press Enter to print the list):");
            String userInput = scanner.nextLine();

            // if user just presses Enter, print the current list
            if (userInput.isEmpty()) {
                synchronized (LIST_LOCK) {
                    // print the current state of the shared list
                    System.out.println("Current list:");
                    for (String item : sharedList) {
                        System.out.println(item);
                    }
                }
            } else {
                // if the user enters a string, split it if it's longer than 80 chars
                synchronized (LIST_LOCK) {
                    addInputToList(userInput);
                }
            }
        }
    }

    // method to split and add input to the shared list
    private static void addInputToList(String input) {
        final int MAX_LENGTH = 80;

        // if the input length is greater than MAX_LENGTH, split it
        if (input.length() > MAX_LENGTH) {
            for (int i = 0; i < input.length(); i += MAX_LENGTH) {
                // get substring of up to MAX_LENGTH characters
                String substring = input.substring(i, Math.min(i + MAX_LENGTH, input.length()));
                sharedList.addFirst(substring);
            }
        } else {
            // if input is short enough, add it directly
            sharedList.addFirst(input);
        }
    }

    // runnable class for sorting the list
    static class ListSorter implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000); // sleep for 5 seconds before sorting
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                synchronized (LIST_LOCK) {
                    // sort the list when awake
                    bubbleSort(sharedList);
                }
            }
        }

        // bubble sort
        private void bubbleSort(List<String> list) {
            int size = list.size();
            for (int i = 0; i < size - 1; i++) {
                for (int j = 0; j < size - i - 1; j++) {
                    if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                        String temp = list.get(j);
                        list.set(j, list.get(j + 1));
                        list.set(j + 1, temp);
                    }
                }
            }
        }
    }
}