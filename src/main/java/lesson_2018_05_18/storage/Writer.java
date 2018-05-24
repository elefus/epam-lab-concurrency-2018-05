package lesson_2018_05_18.storage;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Writer implements Runnable {

    private final Storage storage;

    public Writer(Storage storage) {
        this.storage = storage;
    }

    @Override
    @SneakyThrows
    public void run() {
        for (int i = 0; i < 5; ++i) {
            storage.set(String.valueOf(i));
            TimeUnit.SECONDS.sleep(3);
        }
    }
}

