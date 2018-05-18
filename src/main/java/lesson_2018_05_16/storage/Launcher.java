package lesson_2018_05_16.storage;

import lesson_2018_05_16.storage.reader.SimpleReader;
import lesson_2018_05_16.storage.writer.SimpleWriter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class Launcher {
    @SneakyThrows
    public static void main(String[] args) {
        Storage storage = new SimpleStorage();
        SimpleReader readers[] = new SimpleReader[3];
        SimpleWriter writers[] = new SimpleWriter[2];

        for (int i = 0; i < writers.length; i++) {
            writers[i] = new SimpleWriter(storage);
        }
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new SimpleReader(storage);
        }

        for (SimpleWriter writer : writers) {
            TimeUnit.MILLISECONDS.sleep(200);
            writer.start();
        }
        for (SimpleReader reader : readers) {
            TimeUnit.MILLISECONDS.sleep(50);
            reader.start();
        }
    }
}
