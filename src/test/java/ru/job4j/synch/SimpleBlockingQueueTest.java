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
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer  = new Thread(() -> queue.offer(1));
        Thread customer = new Thread(() -> queue.poll());
        producer.start();
        customer.start();
        producer.join();

    }


}