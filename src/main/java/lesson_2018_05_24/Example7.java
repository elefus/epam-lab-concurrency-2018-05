package lesson_2018_05_24;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Example7 {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(0);

        new Thread(new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                semaphore.acquire(4);
                System.out.println("Got it!");
                System.out.println(semaphore.availablePermits());
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);
        semaphore.release(10);
    }
}
