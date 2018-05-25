package lesson_2018_05_25;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

public class Example1 {

    public static void main(String[] args) {
        int[] data = ThreadLocalRandom.current().ints(40, 0, 100).toArray();

        ForkJoinPool pool = ForkJoinPool.commonPool();
        System.out.println("Before: " + Arrays.toString(data));

        pool.invoke(new QuickSortAction(data, 0, data.length - 1));

        System.out.println("After: " + Arrays.toString(data));
    }

    @AllArgsConstructor
    private static class QuickSortAction extends RecursiveAction {

        private static final int SEQUENTIAL_THRESHOLD = 10;
        private int[] data;
        private int fromInclusive;
        private int toInclusive;

        @Override
        protected void compute() {
            if (toInclusive - fromInclusive < SEQUENTIAL_THRESHOLD) {
                Arrays.sort(data, fromInclusive, toInclusive + 1);
            } else {
                int pivot = partition(data, fromInclusive, toInclusive);
                QuickSortAction left = new QuickSortAction(data, fromInclusive, pivot);
                QuickSortAction right = new QuickSortAction(data, pivot + 1, toInclusive);

//                left.fork();
//                right.fork();
                ForkJoinTask.invokeAll(left, right);
            }
        }
    }

    private static int partition(int[] array, int fromInclusive, int toExclusive) {
        int pivot = array[fromInclusive];
        int i = fromInclusive - 1;
        int j  = toExclusive + 1;
        while (true){
            do {
                i++;
            }
            while (array[i] < pivot);

            do {
                j--;
            }
            while (array[j] > pivot);

            if (i >= j) {
                return j;
            }

            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
