package lesson_2018_05_16.storage.writer;

import lesson_2018_05_16.storage.Storage;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleWriter extends Thread {
    @NonFinal
    static int id = 0;
    Storage storage;

    public SimpleWriter(Storage storage) {
        this.storage = storage;
        setName(String.format("Writer-%d", ++id));
    }

    @Override
    @SneakyThrows
    public void run() {
        int counter = 0;
        while (true) {
            TimeUnit.SECONDS.sleep(3);
            storage.setString(getName() + " " + ++counter);
            System.out.println("Now storage is " + getName() + " " + counter);
        }
    }
}
