package lesson_2018_05_22.stack;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Блокирующий стек фиксированного размера.
 *
 * @param <T> Тип данных, хранящихся в стеке.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockingStack<T> {
    final Object[] arr;
    Lock lock;
    Condition condition;
    int current = 0;

    /**
     * @param size Размер стека.
     * @param fair Честность доступа к элементам стека (при добавлении и удалении).
     */
    public BlockingStack(int size, boolean fair) {
        arr = new Object[size];
        lock = new ReentrantLock(fair);
        condition = lock.newCondition();
    }

    /**
     * Помещает элемент на вершину стека.
     * Если стек полон - блокирует поток.
     *
     * @param element Добавляемый элемент.
     */
    public void push(T element) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " pushes " + element);
            arr[current] = element;
            current++;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Извлекает элемент из вершины стека.
     * Если стек пуст - блокирует поток.
     *
     * @return Извлеченный элемент.
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public T pop() {
        lock.lock();
        int saveCurrent = current;
        try {
            if (current == 0) {
                System.out.println(Thread.currentThread().getName() + " waiting.");
                condition.await();
                System.out.println(Thread.currentThread().getName() + " resumes.");
            }
            T result = (T) arr[saveCurrent];
            System.out.println("At " + saveCurrent + " " + result);
            current--;
            return result;
        } finally {
            lock.unlock();
        }
    }
}
