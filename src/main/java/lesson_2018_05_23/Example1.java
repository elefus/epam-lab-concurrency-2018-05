package lesson_2018_05_23;

import lombok.Value;

import java.util.concurrent.*;

public class Example1 {

    private interface Box {
        String getDestination();
    }

    @Value
    private static class PrioritizedBox implements Box, Comparable<PrioritizedBox> {
        String destination;
        int priority;

        @Override
        public int compareTo(PrioritizedBox other) {
            return Integer.compare(priority, other.priority);
        }
    }


    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        BlockingQueue<PrioritizedBox> desk = new PriorityBlockingQueue<>();
        int numBoxes = 10;

        service.submit(getOperatorBehaviour(desk, numBoxes));
        service.submit(getCourierBehaviour(desk, numBoxes));
        service.shutdown();
    }

    private static Runnable getOperatorBehaviour(BlockingQueue<? super PrioritizedBox> desk, int numBoxes) {
        return () -> {
            try {
                for (int i = 0; i < numBoxes; ++i) {
                    System.out.println("Prepare box number - " + i);
                    TimeUnit.SECONDS.sleep(1);
                    PrioritizedBox box = new PrioritizedBox(String.valueOf(i), numBoxes - i);

                    System.out.println("Trying put " + i + " on the desk");
                    desk.put(box);
                    System.out.println("Sucessfully put " + i + " on the desk");
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private static Runnable getCourierBehaviour(BlockingQueue<? extends Box> desk, int numBoxes) {
        return () -> {
            try {
                for (int i = 0; i < numBoxes; ++i) {
                    System.err.println("Trying take box from the desk");
                    Box box = desk.take();
                    System.err.println("Took the " + box + ", going to deliver it");
                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
