package lesson_2018_05_22;

import java.util.concurrent.*;

public class Example1 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<String> callableFuture = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("After sleep");
                return "some value";
            }
        });
        Future<Integer> some_task = service.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Some task");
            }
        }, 100);
        service.shutdown();

        try {
            String result = callableFuture.get();
            System.out.println(result);
            System.out.println("After get in main method");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
