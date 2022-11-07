package ru.job4j.cache;

import org.testng.annotations.Test;
import ru.job4j.synch.SimpleBlockingQueue;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class CacheTest {
    @Test
    public void test() {
        Map<Integer, Base> map = new HashMap<>();
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("one");
        cache.add(base);
        Base base1 = new Base(1, 1);
        base1.setName("two");
        cache.update(base1);
        cache.delete(base);






    }


}