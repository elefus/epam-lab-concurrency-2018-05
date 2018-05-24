package lesson_2018_05_24;

import lombok.SneakyThrows;

import java.util.concurrent.Exchanger;

public class Example10 {

    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                String value = exchanger.exchange("123");
                System.out.println("Value in another thread = " + value);
            }
        }).start();


        String value = exchanger.exchange("abc");
        System.out.println("Value in main method = " + value);
    }
}
