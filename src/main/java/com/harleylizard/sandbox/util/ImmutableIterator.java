package com.harleylizard.sandbox.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class ImmutableIterator<T> implements Iterator<T> {
    private final List<T> list;
    private int index;

    private ImmutableIterator(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public T next() {
        var t = list.get(index);
        index++;
        return t;
    }

    public static <T> Iterator<T> of(List<T> list) {
        return new ImmutableIterator<>(Collections.unmodifiableList(list));
    }
}
