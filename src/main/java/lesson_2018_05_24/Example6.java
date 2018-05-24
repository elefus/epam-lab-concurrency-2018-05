package lesson_2018_05_24;

import lombok.SneakyThrows;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class Example6 {

    private static WeakReference<String> strWeak = new WeakReference<>(new String("123"));
    private static ThreadLocal<Long> local = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        local.set(42L);

        new Thread(new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                local.set(55L);
                TimeUnit.SECONDS.sleep(2);
                System.out.println(local.get());
            }
        }).start();

        TimeUnit.SECONDS.sleep(2);
        System.out.println(local.get());
    }
}
