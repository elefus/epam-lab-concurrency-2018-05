package lesson_2018_05_24;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Рассмотрим следующий пример. Мы хотим провести автомобильную гонку.
 * В гонке принимают участие пять автомобилей.
 * Для начала гонки нужно, чтобы выполнились следующие условия:
 *    1. Каждый из пяти автомобилей подъехал к стартовой прямой;
 *    2. Была дана команда «На старт!»;
 *    3. Была дана команда «Внимание!»;
 *    4. Была дана команда «Марш!».
 *    5. Важно, чтобы все автомобили стартовали одновременно.
 * @see <a href="https://habrastorage.org/files/46b/3ae/b41/46b3aeb417cf4fb4ba271b4c66b52436.gif">CountDownLatch</a>
 */
public class Example2 {

    private static final CountDownLatch START = new CountDownLatch(8);
    private static final int trackLength = 500000;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            new Thread(new Car(i, (int) (Math.random() * 100 + 50))).start();
            TimeUnit.SECONDS.sleep(1);
        }

        while (START.getCount() > 3) {
            TimeUnit.MILLISECONDS.sleep(100);
        }

        TimeUnit.SECONDS.sleep(1);
        System.out.println("На старт!");
        START.countDown();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Внимание!");
        START.countDown();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Марш!");
        START.countDown();
    }

    public static class Car implements Runnable {
        private int carNumber;
        private int carSpeed;

        Car(int carNumber, int carSpeed) {
            this.carNumber = carNumber;
            this.carSpeed = carSpeed;
        }

        @Override
        @SneakyThrows
        public void run() {
            System.out.println("Автомобиль №" + carNumber + " подъехал к стартовой прямой.");
            START.countDown();
            START.await();
            TimeUnit.MILLISECONDS.sleep(trackLength / carSpeed);
            System.out.println("Автомобиль №" + carNumber + " финишировал!");
        }
    }
}
