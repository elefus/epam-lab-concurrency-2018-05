package lesson_2018_05_18.storage;

import java.util.concurrent.TimeUnit;

public class Storage {

    private volatile String string = "DEFAULT";

    private final Object readLock = new Object();
    private final Object writeLock = new Object();
    private volatile int reading = 0;

    public String get() throws InterruptedException {
        synchronized (writeLock) {
            reading++;
        }

        try {
            TimeUnit.SECONDS.sleep(1);
            return string;
        } finally {
            synchronized (readLock) {
                if (--reading == 0) {
                    readLock.notify();
                }
            }
        }
    }

    public void set(String string) throws InterruptedException {
        synchronized (writeLock) {
            synchronized (readLock) {
                while (reading != 0) {
                    readLock.wait();
                }
            }

            this.string = string;
            TimeUnit.SECONDS.sleep(1);
        }
    }
}