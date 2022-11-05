package ru.job4j.synch;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer  = new Thread(() -> {
            try {
                queue.offer(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread customer = new Thread(() -> {
            try {
                queue.poll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        producer.start();
        customer.start();
        producer.join();

    }


}