package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private  Queue<T> queue = new LinkedList<>();


    public synchronized void offer(T value) {
        synchronized (this) {
            queue.add(value);
            this.notifyAll();
        }

    }

    public synchronized T poll() {
        synchronized (this) {
            T t = queue.poll();
            this.check();
            return t;
        }

    }

    public void check() {
        synchronized (this) {
            while (queue.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
