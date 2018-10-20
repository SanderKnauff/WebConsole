package nl.imine.WebConsole.util;

import java.util.Collection;
import java.util.LinkedList;

public class LimitedSizeQueue<T> extends LinkedList<T> {

    private int maxSize;

    public LimitedSizeQueue(int size){
        this.maxSize = size;
    }

    @Override
    public boolean add(T t){
        boolean r = super.add(t);
        if (size() > maxSize){
            removeRange(0, size() - maxSize - 1);
        }
        return r;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean r = super.addAll(c);
        if (size() > maxSize){
            removeRange(0, size() - maxSize - 1);
        }
        return r;
    }

    public T getYoungest() {
        return get(size() - 1);
    }

    public T getOldest() {
        return get(0);
    }
}
