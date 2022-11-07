package ru.job4j;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CASCountTest {

    @Test
    public void incrementAndGet() throws InterruptedException {

        CASCount casCount = new CASCount();
        Thread first = new Thread(casCount::increment);
        Thread second = new Thread(() -> {
            casCount.increment();
            casCount.increment();
            casCount.increment();
        });

        first.start();
        second.start();
        Thread.sleep(500);
        assertThat(casCount.get()).isEqualTo(4);

    }


}