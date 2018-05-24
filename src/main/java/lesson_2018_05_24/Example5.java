package lesson_2018_05_24;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.concurrent.Phaser;

/**
 * Есть пять остановок.
 * На первых четырех из них могут стоять пассажиры и ждать автобуса.
 * Автобус выезжает из парка и останавливается на каждой остановке на некоторое время.
 * После конечной остановки автобус едет в парк.
 * Необходимо забрать пассажиров и высадить их на нужных остановках.
 * @see <a href="https://habrastorage.org/files/086/6a4/b7a/0866a4b7acdf416384d4e7372b49a34b.gif">Phaser</a>
 */
public class Example5 {

    private static final Phaser PHASER = new Phaser(1);

    public static void main(String[] args) {
        ArrayList<Passenger> passengers = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            if ((int) (Math.random() * 2) > 0) {
                passengers.add(new Passenger(i, i + 1));
            }

            if ((int) (Math.random() * 2) > 0) {
                passengers.add(new Passenger(i, 5));
            }
        }

        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    System.out.println("Автобус выехал из парка.");
                    PHASER.arrive();
                    break;
                case 6:
                    System.out.println("Автобус уехал в парк.");
                    PHASER.arriveAndDeregister();
                    break;
                default:
                    int currentBusStop = PHASER.getPhase();
                    System.out.println("Остановка № " + currentBusStop);

                    for (Passenger p : passengers)
                        if (p.departure == currentBusStop) {
                            PHASER.register();
                            p.start();
                        }

                    PHASER.arriveAndAwaitAdvance();
            }
        }
    }

    public static class Passenger extends Thread {
        private int departure;
        private int destination;

        Passenger(int departure, int destination) {
            this.departure = departure;
            this.destination = destination;
            System.out.println(this + " ждёт на остановке № " + departure);
        }

        @Override
        @SneakyThrows
        public void run() {
            System.out.println(this + " сел в автобус.");

            while (PHASER.getPhase() < destination) {
                PHASER.arriveAndAwaitAdvance();
            }

            System.out.println(this + " покинул автобус.");
            PHASER.arriveAndDeregister();
        }

        @Override
        public String toString() {
            return "Пассажир{" + departure + " -> " + destination + '}';
        }
    }
}
