package lesson_2018_05_23;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Example5 {

    private static final int[] arr = new int[]{1, 2, 3, 4};

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        AtomicIntegerArray atomicArray = new AtomicIntegerArray(arr);




        service.execute(() -> {
            atomicArray.getAndUpdate(0, value -> value + 10);
            do {

            } while (!atomicArray.compareAndSet(0, arr[0], arr[0] + 1));
        });
        service.execute(() -> {
            arr[0] = 2;
        });
        System.out.println(arr[0]);


    }
}
