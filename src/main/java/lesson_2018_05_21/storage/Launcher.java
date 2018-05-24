package lesson_2018_05_21.storage;

public class Launcher {

    public static void main(String[] args) {
        Storage storage = new Storage();

        Writer writer = new Writer(storage);
        new Thread(writer).start();

        Thread[] readers = new Thread[4];
        for (int i = 0; i < readers.length; ++i) {
            readers[i] = new Thread(new Reader(storage));
        }

        for (Thread reader : readers) {
            reader.start();
        }
    }
}
