package lesson_2018_05_24;

import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Существует паромная переправа.
 * Паром может переправлять одновременно по три автомобиля.
 * Чтобы не гонять паром лишний раз, нужно отправлять его, когда у переправы соберется минимум три автомобиля.
 * @see <a href="https://habrastorage.org/files/89a/f0c/b71/89af0cb71aad4465bb9c934b8be91a67.gif">CyclicBarrier</a>
 */
public class Example3 {

    private static final CyclicBarrier BARRIER = new CyclicBarrier(3, new FerryBoat());

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 9; i++) {
            new Thread(new Car(i)).start();
            TimeUnit.MILLISECONDS.sleep(400);
        }
    }

    public static class FerryBoat implements Runnable {
        @Override
        @SneakyThrows
        public void run() {
            System.out.println("Паром отправился в путь!");
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println("Паром переправил автомобили!");
        }
    }

    public static class Car implements Runnable {
        private int carNumber;

        Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        @SneakyThrows
        public void run() {
                System.out.println("Автомобиль №" + carNumber + " подъехал к паромной переправе.");
                BARRIER.await();
                System.out.println("Автомобиль №" + carNumber + " продолжил движение.");
        }
    }
}
