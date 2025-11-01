public class task1 {
    public static void main(String[] args) throws InterruptedException {
        example1();
        example2();
        example3();
        example4();
    }

    static void example1() throws InterruptedException {
        Thread t1 = new Thread(() -> System.out.println("Running in: " + Thread.currentThread().getName()), "MyFirstThread");
        t1.start();
        t1.join();
        System.out.println("Thread Name: " + t1.getName());
    }

    static void example2() throws InterruptedException {
        Thread t2 = new Thread(() -> System.out.println("Running in: " + Thread.currentThread().getName()));
        t2.setName("WorkerThread-1");
        t2.start();
        t2.join();
        System.out.println("Thread Name: " + t2.getName());
    }

    static void example3() throws InterruptedException {
        Runnable task = () -> System.out.println("Running in: " + Thread.currentThread().getName());
        Thread t3 = new Thread(task, "RunnableThread");
        t3.start();
        t3.join();
    }

    static void example4() throws InterruptedException {
        Thread t1 = new Thread(() -> System.out.println("Thread 1 Priority: " + Thread.currentThread().getPriority()), "T1");
        Thread t2 = new Thread(() -> System.out.println("Thread 2 Priority: " + Thread.currentThread().getPriority()), "T2");
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
    
}

