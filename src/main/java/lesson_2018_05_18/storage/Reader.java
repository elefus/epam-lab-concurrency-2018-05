package lesson_2018_05_18.storage;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Reader implements Runnable {

    private final Storage storage;

    public Reader(Storage storage) {
        this.storage = storage;
    }

    @Override
    @SneakyThrows
    public void run() {
        String threadName = Thread.currentThread().getName();
        for (int i = 0; i < 10; ++i) {
            System.out.println(threadName + " - " + i + " - " + storage.get());
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
