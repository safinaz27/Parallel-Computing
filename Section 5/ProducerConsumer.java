import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {

    // Shared queue
    private static final int CAPACITY = 5;
    private final Queue<String> queue = new LinkedList<>();

    // Producer class
    class Producer implements Runnable {
        private final int producerId;

        Producer(int id) {
            this.producerId = id;
        }

        @Override
        public void run() {
            int messageCount = 0;

            while (true) {
                synchronized (queue) {
                    while (queue.size() == CAPACITY) {
                        try {
                            System.out.println("Producer " + producerId + " waiting, queue is full...");
                            queue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    String message = "Message-" + producerId + "-" + messageCount++;
                    queue.add(message);
                    System.out.println("Producer " + producerId + " produced: " + message);

                    queue.notifyAll();  // wake up consumers
                }

                try {
                    Thread.sleep(300); // simulate production time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    // Consumer class
    class Consumer implements Runnable {
        private final int consumerId;

        Consumer(int id) {
            this.consumerId = id;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            System.out.println("Consumer " + consumerId + " waiting, queue is empty...");
                            queue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    String message = queue.poll();
                    System.out.println("Consumer " + consumerId + " consumed: " + message);

                    queue.notifyAll();  // wake up producers
                }

                try {
                    Thread.sleep(500); // simulate consumption time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    // Main method to run multiple producers and consumers
    public static void main(String[] args) {
        ProducerConsumer example = new ProducerConsumer();

        // Start producers
        for (int i = 1; i <= 2; i++) {
            new Thread(example.new Producer(i)).start();
        }

        // Start consumers
        for (int i = 1; i <= 3; i++) {
            new Thread(example.new Consumer(i)).start();
        }
    }
}
