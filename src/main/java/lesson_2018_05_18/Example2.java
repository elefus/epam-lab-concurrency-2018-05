package lesson_2018_05_18;

import java.io.Closeable;
import java.io.IOException;

public class Example2 {



}

class Singleton1 {

    public static final Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {}
}

class Singleton2 {

    private String filed = "123";
    private static volatile Singleton2 instance;

    // T1
    // T2
    // --
    // T3
    public static Singleton2 getInstance() {
        // T1
        // T2
        // --
        // T3

        // Double
        // Check
        // Locking
        Singleton2 localRef = instance;
        if (localRef == null) {
            // T1
            // T2
            synchronized (Singleton2.class) {
                localRef = instance;
                // T2
                if (localRef == null) {
                    // T2
                    localRef = instance = new Singleton2();
                    // T2
                    // local_ptr = malloc(sizeof(Singleton))
                    // T2
                    // instance = local_ptr
                    // Singleton::ctor(instance)
                }
            }
        }
        return localRef;
    }

    private Singleton2() {}
}

enum Singleton3 implements Closeable {
    INSTANCE(10);

    public static void method() {

    }

    private final int field;


    Singleton3(int field) {
        this.field = field;
    }

    @Override
    public void close() throws IOException {

    }
}

class Singleton4 {

    private static class Singleton4Holder {

        private static final Singleton4 INSTANCE = new Singleton4();
    }

    public static void method() {

    }

    public static Singleton4 getInstance() {
        return Singleton4Holder.INSTANCE;
    }
}
