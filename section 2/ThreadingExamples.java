public class ThreadingExamples {

    // Example 1: Extending the Thread Class
    private static class MyThread extends Thread {
        @Override
        public void run() {
            
            System.out.println("Hello from MyThread (Extends Thread)");

        }
    }

    // Example 2: Implementing the Runnable Interface 
    private static class MyTask implements Runnable {
        @Override
        public void run() {
            
            System.out.println("Hello from MyTask (Implements Runnable)");

        }
    }

    public static void main(String[] args) {
        System.out.println("Main Thread is running...");

        // 1. Run Example 1: Extending Thread
        Thread thread1 = new MyThread();
        System.out.println("Starting thread t1 (MyThread)...");
        thread1.start(); 


        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 2. Run Example 2: Implementing Runnable
        Runnable task = new MyTask();
        Thread thread2 = new Thread(task); 
        System.out.println("Starting thread t2 (MyTask)...");
        thread2.start(); 

        System.out.println("Main Thread finished starting both custom threads.");
        // The program will exit once the main thread and all started custom threads finish execution.
    }
}
