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
    volatile String string = "DEFAULT";
    volatile int nowWriting;

    @Override
    @SneakyThrows
    public void setString(final String string) {
        synchronized (writerLock) {
            nowWriting++;
            TimeUnit.SECONDS.sleep(1);
            this.string = string;
            if (nowWriting-- == 0) {
                writerLock.notifyAll();
            }
        }
    }

    @Override
    @SneakyThrows
    public String getString() {
        String result;
        synchronized (writerLock) {
            if (nowWriting != 0) {
                writerLock.wait();
            }
            TimeUnit.SECONDS.sleep(1);
            result = string;
        }
        return result;
    }
}

