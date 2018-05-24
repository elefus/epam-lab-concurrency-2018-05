package lesson_2018_05_24;

import lombok.SneakyThrows;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Example9 {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("Barrier down!");
                throw new RuntimeException("In action");
            }
        });

        cyclicBarrier.reset();


        new Thread(new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                System.out.println("I'm another thread");
                cyclicBarrier.await();
                System.out.println("After barrier - 1");
                cyclicBarrier.await();
                System.out.println("After barrier - 2");
            }
        }).start();

        cyclicBarrier.await();
        System.out.println("After barrier in main - 1");

        cyclicBarrier.await();
        System.out.println("After barrier in main - 2");
    }
}
