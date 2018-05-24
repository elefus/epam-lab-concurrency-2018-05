package lesson_2018_05_23;

import lombok.Value;

import java.util.concurrent.*;

public class Example2 {

    private interface Box {
        String getDestination();
    }

    @Value
    private static class DelayedBox implements Box, Delayed {
        String destination;
        long deliveryTimeMillis;

        public DelayedBox(String destination, long delay, TimeUnit unit) {
            this.destination = destination;
            deliveryTimeMillis = System.currentTimeMillis() + unit.toMillis(delay);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(deliveryTimeMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed other) {
            return Long.compare(deliveryTimeMillis, other.getDelay(TimeUnit.MILLISECONDS));
        }
    }


    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        BlockingQueue<DelayedBox> desk = new DelayQueue<>();
        int numBoxes = 10;

        service.submit(getOperatorBehaviour(desk, numBoxes));
        service.submit(getCourierBehaviour(desk, numBoxes));
        service.shutdown();
    }

    private static Runnable getOperatorBehaviour(BlockingQueue<? super DelayedBox> desk, int numBoxes) {
        return () -> {
            try {
                for (int i = 0; i < numBoxes; ++i) {
                    System.out.println("Prepare box number - " + i);
                    DelayedBox box = new DelayedBox(String.valueOf(i), 10, TimeUnit.SECONDS);

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
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
