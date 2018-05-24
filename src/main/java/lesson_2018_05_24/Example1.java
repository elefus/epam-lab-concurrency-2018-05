package lesson_2018_05_24;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Существует парковка, которая одновременно может вмещать не более 5 автомобилей.
 * Если парковка заполнена полностью, то прибывший автомобиль должен подождать пока не освободится хотя бы одно место.
 * После этого он сможет припарковаться.
 * @see <a href="https://habrastorage.org/files/9da/48f/85b/9da48f85b5874362bc2279f181613c0e.gif">Semaphore</a>
 */
public class Example1 {

    private static final boolean[] PARKING_PLACES = new boolean[5];
    private static final Semaphore SEMAPHORE = new Semaphore(5, true);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 7; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(400);
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
            System.out.println("Автомобиль № " + carNumber + " подъехал к парковке.");
            SEMAPHORE.acquire();

            int parkingNumber = -1;

            synchronized (PARKING_PLACES){
                for (int i = 0; i < 5; i++)
                    if (!PARKING_PLACES[i]) {
                        PARKING_PLACES[i] = true;
                        parkingNumber = i;
                        System.out.println("Автомобиль №" + carNumber + " припарковался на месте " + i);
                        break;
                    }
            }

            TimeUnit.SECONDS.sleep(5);

            synchronized (PARKING_PLACES) {
                PARKING_PLACES[parkingNumber] = false;
            }

            SEMAPHORE.release();
            System.out.println("Автомобиль №" + carNumber + " покинул парковку.");
        }
    }
}
