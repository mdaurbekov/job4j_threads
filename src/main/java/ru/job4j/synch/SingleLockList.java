package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) throws CloneNotSupportedException {
        this.list = (List) ((SingleLockList) list).clone();
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }


    private List<T> copy(List<T> list) {
        return List.copyOf(list);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }


}