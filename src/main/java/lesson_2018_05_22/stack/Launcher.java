package lesson_2018_05_22.stack;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class Launcher {
    @SneakyThrows
    public static void main(String[] args) {
        BlockingStack<String> stack = new BlockingStack<>(10, true);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> System.out.println(stack.pop()));
        executorService.submit(() -> System.out.println(stack.pop()));
        executorService.submit(() -> System.out.println(stack.pop()));
        TimeUnit.MILLISECONDS.sleep(500);
        executorService.submit(() -> stack.push(Thread.currentThread().getName()));
        executorService.submit(() -> stack.push(Thread.currentThread().getName()));
        executorService.submit(() -> stack.push(Thread.currentThread().getName()));
    }
}
