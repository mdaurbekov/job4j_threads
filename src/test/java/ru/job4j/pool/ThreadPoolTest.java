package ru.job4j.pool;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreadPoolTest {

    @Test
    public void testWork() throws InterruptedException {
            int[] count = {0};
            ThreadPool threadPool = new ThreadPool();
            for (int i = 0; i < 10; i++) {
                threadPool.work(() -> count[0]++);
            }
            threadPool.shutdown();
            assertThat(10).isEqualTo(count[0]);

    }
}