import java.lang.Thread.UncaughtExceptionHandler;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ThreadExceptionHandling {

    // --- 1. Define the Global Default Uncaught Exception Handler ---
    private static final UncaughtExceptionHandler GLOBAL_HANDLER = (thread, exception) -> {
        // Use a StringWriter to get the stack trace as a string for clean logging
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();
        
        System.err.println("\n--- GLOBAL HANDLER: Uncaught Exception ---");
        System.err.println("Thread: " + thread.getName());
        System.err.println("Error: " + exception.getMessage());
        System.err.println("Stack Trace:\n" + stackTrace);
        System.err.println("----------------------------------------\n");
    };

    public static void main(String[] args) throws InterruptedException {

        // Set the global default handler for any thread that lacks a specific one
        Thread.setDefaultUncaughtExceptionHandler(GLOBAL_HANDLER);

        // ------------------------------------------------------------------
        // Thread 1: Demonstrates Local Try-Catch (Image: image_ee0c65.png)
        // This thread handles its exception and continues.
        // The Global Handler is NOT invoked.
        // ------------------------------------------------------------------
        Thread t1_local = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " started (Local Catch).");
                int result = 10 / 0; // Throws ArithmeticException
                System.out.println("Result: " + result); // This line is skipped
            } catch (ArithmeticException e) {
                System.out.println("✅ " + Thread.currentThread().getName() + " caught exception locally: " + e.getMessage());
            }
            System.out.println(Thread.currentThread().getName() + " finished.");
        }, "T1-LocalHandler");


        // ------------------------------------------------------------------
        // Thread 2: Demonstrates Thread-Specific Handler (Image: image_ee0c67.png)
        // This thread terminates, but its specific handler is invoked instead of the Global one.
        // ------------------------------------------------------------------
        Thread t2_specific = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started (Specific Handler).");
            // No try-catch block means the exception is uncaught
            throw new RuntimeException("Forced crash for specific handler!");
        }, "T2-SpecificHandler");

        // Set the thread-specific handler
        t2_specific.setUncaughtExceptionHandler((thread, exception) -> {
            System.err.println("\n--- SPECIFIC HANDLER: Uncaught Exception ---");
            System.err.println("Thread: " + thread.getName());
            System.err.println("Message: " + exception.getMessage());
            System.err.println("------------------------------------------\n");
        });


        // ------------------------------------------------------------------
        // Thread 3: Demonstrates Global Default Handler (Image: image_ee0c7e.png)
        // This thread terminates, and since it has no specific handler, 
        // the Global Handler is invoked.
        // ------------------------------------------------------------------
        Thread t3_global = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started (Global Handler).");
            // Uncaught exception - no local or specific handler is defined
            throw new IllegalArgumentException("Invalid argument, caught by global fallback!");
        }, "T3-GlobalHandler");
        
        // Start all threads
        t1_local.start();
        t2_specific.start();
        t3_global.start();
        
        // Wait for all threads to finish before the main program exits
        // Note: join() is not strictly necessary for the handlers to work,
        // but ensures the main thread waits for the output.
        // We'll skip join() here to let the exceptions print out immediately.
        System.out.println("\nMain thread finished starting all workers.");
    }
}