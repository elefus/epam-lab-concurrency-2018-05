package lesson_2018_05_24;

import java.util.concurrent.CountDownLatch;

public class Example8 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before countDown");
                latch.countDown();
                System.out.println("After countDown");
            }
        }).start();

        latch.await();
        System.out.println(latch.getCount());
        System.out.println("Another thread started");
    }
}
