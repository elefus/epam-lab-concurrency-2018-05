package lesson_2018_05_21.storage;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Storage {

    private final Lock readLock;
    private final Lock writeLock;
    private volatile String string = "DEFAULT";

    public Storage() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    public String get() throws InterruptedException {
        readLock.lock();
        try {
            TimeUnit.SECONDS.sleep(1);
            return string;
        } finally {
            readLock.unlock();
        }
    }

    public void set(String string) throws InterruptedException {
        writeLock.lock();
        try {
            this.string = string;
            TimeUnit.SECONDS.sleep(1);
        } finally {
            writeLock.unlock();
        }
    }
}