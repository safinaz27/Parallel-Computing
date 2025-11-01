public class ThreadExceptionHandling {

    public static void main(String[] args) {

        //"Global Handler" 
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            System.err.println("\n GLOBAL HANDLER:");
            System.err.println("Thread: " + thread.getName());
            System.err.println("Error: " + ex.getMessage());
        });

        //Thread 1:(try-catch)
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("T1: Running...");
                int x = 10 / 0;
            } catch (Exception e) {
                System.out.println("T1 caught exception locally: " + e.getMessage());
            }
            System.out.println("T1: Done.\n");
        }, "T1");

        //Thread 2: specifc Handler 
        Thread t2 = new Thread(() -> {
            System.out.println("T2: Running...");
            throw new RuntimeException("Crash in specific handler!");
        }, "T2");

        t2.setUncaughtExceptionHandler((thread, ex) -> {
            System.err.println("\n SPECIFIC HANDLER:");
            System.err.println("Thread: " + thread.getName());
            System.err.println("Error: " + ex.getMessage());
        });

        //Thread 3: Global
        Thread t3 = new Thread(() -> {
            System.out.println("T3: Running...");
            throw new IllegalArgumentException("Goes to global handler!");
        }, "T3");

        // Run all threads
        t1.start();
        t2.start();
        t3.start();

        System.out.println("\nMain thread finished starting all threads.");
    }
}
