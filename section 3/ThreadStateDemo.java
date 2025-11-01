public class ThreadStateDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            // When the thread starts executing, it is in a RUNNABLE state
            System.out.println("State inside run: " +
                Thread.currentThread().getState());
        });

        // State 1: NEW
        // Thread has been created but not yet started.
        System.out.println("Before start(): " + t1.getState());

        // State 2: RUNNABLE (The thread is ready to run or is running)
        t1.start();
        System.out.println("After start(): " + t1.getState());

        // Wait until the thread finishes. The main thread will block here.
        t1.join();

        // State 3: TERMINATED
        // Thread has completed its execution.
        System.out.println("After completion: " + t1.getState());
    }
}