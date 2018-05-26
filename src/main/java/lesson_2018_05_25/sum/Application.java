package lesson_2018_05_25.sum;

import lesson_2018_05_25.Example1;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class Application {
    @Value
    @EqualsAndHashCode(callSuper = true)
    private static class SumTask extends RecursiveTask<Integer> {
        Integer[] numbers;
        int fromInclusive;
        int toExclusive;

        @Override
        protected Integer compute() {
            if (toExclusive - fromInclusive <= 1) {
                return numbers[fromInclusive];
            }
            int pivot = (toExclusive + fromInclusive) / 2;
            SumTask leftTask = new SumTask(numbers, fromInclusive, pivot);
            SumTask rightTask = new SumTask(numbers, pivot, toExclusive);

            return leftTask.invoke() + rightTask.invoke();
        }
    }

    public static void main(String[] args) {
        Integer[] data = ThreadLocalRandom.current().ints(40, 0, 100).boxed().toArray(Integer[]::new);

        ForkJoinPool pool = ForkJoinPool.commonPool();
        System.out.println("Before: " + Arrays.toString(data));

        Integer result = pool.invoke(new SumTask(data, 0, data.length));

        System.out.println("After: " + result);
        System.out.println("Correct: " + Arrays.stream(data).reduce(0, (left, right) -> left + right));
    }
}
