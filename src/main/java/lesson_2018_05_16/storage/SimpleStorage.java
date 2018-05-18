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
    static final Object readerLock = new Object();
    volatile String string = "DEFAULT";
    volatile boolean nowWriting;

    @Override
    @SneakyThrows
    public String getString() {
        String result;
        if (!nowWriting) {
            TimeUnit.SECONDS.sleep(1);
            result = string;
        } else {
            synchronized (writerLock) {
                while (nowWriting) {
                    writerLock.wait();
                }
                TimeUnit.SECONDS.sleep(1);
                result = string;
            }
        }
        return result;
    }

    @Override
    @SneakyThrows
    public void setString(final String string) {
        synchronized (writerLock) {
            nowWriting = true;
            TimeUnit.SECONDS.sleep(1);
            this.string = string;
            if (nowWriting) {
                nowWriting = false;
                writerLock.notifyAll();
            }
        }
    }
}

