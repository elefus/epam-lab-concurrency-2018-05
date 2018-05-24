package lesson_2018_05_21;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Example2 {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("Lock held be thread " + Thread.currentThread());
                    condition.awaitUninterruptibly();
                    System.out.println("After condition");
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(5);

        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
        System.out.println("After signal");

    }
}
