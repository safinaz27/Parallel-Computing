import java.util.List;
import java.util.Arrays;

public class MultiExecutor {

    private final List<Runnable> tasks;

    //Constructor: receives a list of Runnable tasks
    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    //Runs all tasks in parallel (one thread per task)
    public void executeAll() {
        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            thread.start();
        }
    }

    public static void main(String[] args) {
        // Define 3 simple tasks
        Runnable task1 = () -> System.out.println("Task 1 running in " + Thread.currentThread().getName());
        Runnable task2 = () -> System.out.println("Task 2 running in " + Thread.currentThread().getName());
        Runnable task3 = () -> System.out.println("Task 3 running in " + Thread.currentThread().getName());

        MultiExecutor executor = new MultiExecutor(Arrays.asList(task1, task2, task3));
        executor.executeAll();
    }
}
