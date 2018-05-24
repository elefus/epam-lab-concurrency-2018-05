package lesson_2018_05_22;

import lombok.SneakyThrows;

import java.util.concurrent.*;

public class Example2 {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        perform(Executors.unconfigurableExecutorService(service));

        ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(4);
        scheduledService.scheduleAtFixedRate(new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                System.out.println("Scheduled task");
                TimeUnit.SECONDS.sleep(25);
            }
        }, 10, 20, TimeUnit.SECONDS);
    }

    // client-side code
    public static void perform(ExecutorService service) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) service;
        executor.setMaximumPoolSize(Integer.MAX_VALUE);


        service.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Some task");
            }
        });
    }
}
