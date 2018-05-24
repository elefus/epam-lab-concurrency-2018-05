package lesson_2018_05_23;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Example4 {

    private static volatile AtomicLong value = new AtomicLong(100);


    private static String stringVal = "123";
    private static AtomicReference<String> atomicRef = new AtomicReference<>(stringVal);

    public static void main(String[] args) throws InterruptedException {
        // Compare
        // And
        // Set / Swap

        String anotherString = "qwe";
        atomicRef.compareAndSet(stringVal, anotherString);



        Thread inc = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100_000; ++i) {
                    value.getAndIncrement();
                }
            }
        });
        inc.start();

        Thread dec = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100_000; ++i) {
                    value.getAndDecrement();
                }
            }
        });
        dec.start();

        inc.join();
        dec.join();

        System.out.println(value);
    }
}
