package lesson_2018_05_21;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Example1 {

    private static volatile Long value = 0L;
    private static final Lock LOCK = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread inc = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (int i = 0; i < 100_000; ++i) {
                    if (LOCK.tryLock(1, TimeUnit.MILLISECONDS)) {
                        try {
                            ++value;
                        } finally {
                            LOCK.unlock();
                        }
                    } else {
                        System.out.println("Can't lock this monitor");
                        --i;
                    }
                }
            }
        });
        inc.start();


        Thread dec = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100_000; ++i) {
                    LOCK.lock();
                    try {
                        --value;
                    } finally {
                        LOCK.unlock();
                    }
                }
            }
        });
        dec.start();

        inc.join();
        dec.join();

        System.out.println(value);
    }
}
