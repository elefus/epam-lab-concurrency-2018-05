package lesson_2018_05_16.storage;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.TimeUnit;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleStorage implements Storage {
    static final Object writerLock = new Object();
    volatile int writersCount;
    volatile String string = "DEFAULT";

    @Override
    @SneakyThrows
    public String getString() {
        synchronized (writerLock) {
            while (writersCount > 0) {
                writerLock.wait();
            }
        }

        String result;
        result = unsaveGet();
        return result;
    }

    @Override
    @SneakyThrows
    public void setString(final String string) {
        synchronized (writerLock) {
            writersCount++;
            while (writersCount > 1) {
                writerLock.wait();
            }
        }
        unsaveSet(string);
        synchronized (writerLock) {
            writersCount--;
            writerLock.notifyAll();
        }
    }

    @SneakyThrows
    private String unsaveGet() {
        TimeUnit.SECONDS.sleep(1);
        return string;
    }

    @SneakyThrows
    private void unsaveSet(final String string) {
        TimeUnit.SECONDS.sleep(1);
        this.string = string;
    }
}
