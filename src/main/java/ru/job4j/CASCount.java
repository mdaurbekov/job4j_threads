package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);
    volatile int rez;

    public void increment() {
        int newValue;
        do {
            newValue = count.get() + 1;
        } while (!count.compareAndSet(count.get(), newValue));

    }

    public int get() {
        return count.get();
    }
}