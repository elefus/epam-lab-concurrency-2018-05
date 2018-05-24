package lesson_2018_05_23;

import lombok.SneakyThrows;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Example6 {

    private volatile int field = 42;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();

        // ABA problem
        String value = "1";
        AtomicReference<String> atomic = new AtomicReference<>(value);
        AtomicMarkableReference<String> markableReference = new AtomicMarkableReference<>(value, true);
        AtomicStampedReference<String> stampedReference = new AtomicStampedReference<>(value, 0);

        AtomicIntegerFieldUpdater intFieldUptdater = AtomicIntegerFieldUpdater.newUpdater(Example6.class, "field");

        service.execute(new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                String value1 = value;
                TimeUnit.SECONDS.sleep(2);
                atomic.compareAndSet(value1, value + "2");
            }
        });
        service.execute(new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                String newValue = value + "3";
                atomic.compareAndSet(value, newValue);
                TimeUnit.SECONDS.sleep(1);
                atomic.compareAndSet(newValue, value);
            }
        });
        service.shutdown();
        service.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(atomic.get());
    }
}
