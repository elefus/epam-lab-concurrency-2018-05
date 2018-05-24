package lesson_2018_05_21;

import java.util.concurrent.*;

public class Example4 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("From execute method");
            }
        });

        Future<?> runnableFuture = service.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("From submit(Runnable)");
                throw new RuntimeException("142323");
            }
        });
        service.shutdown();
        System.out.println("Before get in main method");
        try {
            runnableFuture.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("Timeout");
        }
        System.out.println(runnableFuture.isDone());
        System.out.println(runnableFuture.cancel(true));
        System.out.println(runnableFuture.isCancelled());

        try {
            runnableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("After get in main method");



    }
}
