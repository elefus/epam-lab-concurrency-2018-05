package lesson_2018_05_18;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Example3 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from executor " + Thread.currentThread());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
                System.out.println("Leave task " + Thread.currentThread());
            }
        };
        service.execute(task);
        service.execute(task);
        service.execute(task);

        TimeUnit.SECONDS.sleep(3);

        List<Runnable> rejectedTasks = service.shutdownNow();
        System.out.println(rejectedTasks.size());
        System.out.println(service.isShutdown());
        System.out.println(service.isTerminated());

        System.out.println(service.awaitTermination(1, TimeUnit.SECONDS));
        System.out.println("IsTerminated = " + service.isTerminated());

    }
}
