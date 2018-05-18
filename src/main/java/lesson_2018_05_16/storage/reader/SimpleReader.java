package lesson_2018_05_16.storage.reader;

import lesson_2018_05_16.storage.Storage;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.concurrent.TimeUnit;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SimpleReader extends Thread {
    @NonFinal
    static int id = 0;
    Storage storage;

    public SimpleReader(Storage storage) {
        this.storage = storage;
        setName(String.format("Reader-%d", ++id));
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println(getName() + " read " + storage.getString());
        }
    }
}
