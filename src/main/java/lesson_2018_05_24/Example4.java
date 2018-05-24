package lesson_2018_05_24;

import lombok.SneakyThrows;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * Есть два грузовика: один едет из пункта A в пункт D, другой из пункта B в пункт С.
 * Дороги AD и BC пересекаются в пункте E.
 * Из пунктов A и B нужно доставить посылки в пункты C и D.
 * Для этого грузовики в пункте E должны встретиться и обменяться соответствующими посылками.
 * @see <a href="https://habrastorage.org/files/947/ef3/f47/947ef3f47ff843a099059006b30ea54d.gif">Exchanger</a>
 */
public class Example4 {

    private static final Exchanger<String> EXCHANGER = new Exchanger<>();

    public static void main(String[] args) throws InterruptedException {
        String[] p1 = new String[]{"{посылка A->D}", "{посылка A->C}"};
        String[] p2 = new String[]{"{посылка B->C}", "{посылка B->D}"};
        new Thread(new Truck(1, "A", "D", p1)).start();
        TimeUnit.MILLISECONDS.sleep(100);
        new Thread(new Truck(2, "B", "C", p2)).start();
    }

    public static class Truck implements Runnable {
        private int number;
        private String dep;
        private String dest;
        private String[] parcels;

        Truck(int number, String departure, String destination, String[] parcels) {
            this.number = number;
            this.dep = departure;
            this.dest = destination;
            this.parcels = parcels;
        }

        @Override
        @SneakyThrows
        public void run() {
            System.out.println("В грузовик №" + number + " погрузили: " + parcels[0] + " и " + parcels[1]);
            System.out.println("Грузовик №" + number + " выехал из пункта " + dep + " в пункт " + dest);
            TimeUnit.MILLISECONDS.sleep(1000 + (long) (Math.random() * 5000));
            System.out.println("Грузовик №" + number + " приехал в пункт Е.");
            parcels[1] = EXCHANGER.exchange(parcels[1]);
            System.out.println("В грузовик №" + number + " переместили посылку для пункта " + dest);
            TimeUnit.MILLISECONDS.sleep(1000 + (long) (Math.random() * 5000));
            System.out.println("Грузовик №" + number + " приехал в " + dest + " и доставил: " + parcels[0] + " и " + parcels[1]);
        }
    }
}
