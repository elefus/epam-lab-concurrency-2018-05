package lesson_2018_05_24;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;

public class Example11 {

    // [2]                 0 - 2 - 0
    // T1 -> arrive        0 - 2 - 1



    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(2);

        System.out.println(phaser.getPhase());
        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getArrivedParties());

        phaser.awaitAdvance(3);

        System.out.println(phaser.getPhase());
        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getArrivedParties());

        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                phaser.register();
                countDownLatch.countDown();
                phaser.arriveAndAwaitAdvance();

                System.out.println(phaser.getPhase());
                System.out.println(phaser.getRegisteredParties());
                System.out.println(phaser.getArrivedParties());


            }
        }).start();
        countDownLatch.await();

        System.out.println(phaser.getPhase());
        System.out.println(phaser.getRegisteredParties());
        System.out.println(phaser.getArrivedParties());


        phaser.arriveAndDeregister();

    }
}
