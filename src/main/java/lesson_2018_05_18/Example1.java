package lesson_2018_05_18;

import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class Example1 {

    private static class SingleThreadExecutorSerivce implements Executor {

        private static final Runnable POISION_PILL = new Runnable() {
            @Override
            public void run() {}
        };

        // poison pill
        private final Queue<Runnable> tasks = new LinkedList<>();
        private final Thread worker;
        private volatile boolean isShutdown = false;

        public SingleThreadExecutorSerivce() {
            worker = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Runnable task;
                            synchronized (tasks) {
                                if (!tasks.isEmpty()) {
                                    task = tasks.poll();
                                } else {
                                    tasks.wait();
                                    continue;
                                }
                            }
                            if (task == POISION_PILL) {
                                return;
                            }
                            task.run();
                        }
                    } catch (InterruptedException ex) {
                        System.out.println("Worker going shutdown");
                    }
                }
            }, "Worker");
            worker.start();
        }

        @Override
        public void execute(Runnable runnable) {
            if (!isShutdown) {
                synchronized (tasks) {
                    if (!isShutdown) {
                        tasks.offer(runnable);
                        tasks.notify();
                    } else {
                        throw new IllegalStateException("Service executor was shutdown!");
                    }
                }
            } else {
                throw new IllegalStateException("Service executor was shutdown!");
            }
        }

        void shutdown() {
            if (!isShutdown) {
                synchronized (tasks) {
                    if (!isShutdown) {
                        tasks.offer(POISION_PILL);
                        tasks.notify();
                        isShutdown = true;
                    } else {
                        throw new IllegalStateException("Service executor was shutdown!");
                    }
                }
            } else {
                throw new IllegalStateException("Service executor was shutdown!");
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        SingleThreadExecutorSerivce serivce = new SingleThreadExecutorSerivce();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("Before sleeping " + threadName);
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("After sleeping " + threadName);
                } catch (InterruptedException e) {
                    System.err.println("Handle interrupt in client-code");
                }

            }
        };
        TimeUnit.SECONDS.sleep(1);

        serivce.execute(task);
        serivce.execute(task);
        serivce.execute(task);

        serivce.shutdown();

        serivce.execute(task);

    }
}
