package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private int size;

    private volatile boolean isRunning = true;

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(new TaskWorker()));
        }
        for (Thread thread : threads) {
            thread.start();

        }
    }

    public void work(Runnable job) {
        if (threads.isEmpty()) {
            throw new IllegalStateException("Нет потоков");
        }
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }


    }


    private final class TaskWorker implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}