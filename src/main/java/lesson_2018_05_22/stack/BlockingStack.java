package lesson_2018_05_22.stack;

/**
 * Блокирующий стек фиксированного размера.
 * @param <T> Тип данных, хранящихся в стеке.
 */
public class BlockingStack<T> {

    private final Object[] arr;
    private int current = 0;
    // TODO использовать Lock + Condition

    /**
     * @param size Размер стека.
     * @param fair Честность доступа к элементам стека (при добавлении и удалении).
     */
    public BlockingStack(int size, boolean fair) {
        arr = new Object[size];
    }

    /**
     * Помещает элемент на вершину стека.
     * Если стек полон - блокирует поток.
     * @param element Добавляемый элемент.
     */
    public void push(T element) {
        // TODO
    }

    /**
     * Извлекает элемент из вершины стека.
     * Если стек пуст - блокирует поток.
     * @return Извлеченный элемент.
     */
    @SuppressWarnings("unchecked")
    public T pop() {
        // TODO
        return (T) arr[current];
    }
}
