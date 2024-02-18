package com.harleylizard.sandbox.layer;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MutablePalette<T> implements Palette<T> {
    private final List<T> list;
    private final Object2IntMap<T> map;
    private final T t;

    private MutablePalette(List<T> list, Object2IntMap<T> map, T t) {
        this.list = list;
        this.map = map;
        this.t = t;
    }

    @Override
    public T getObject(int i) {
        return list.isEmpty() ? t : list.get(i);
    }

    @Override
    public int getInt(T t) {
        return map.getInt(t);
    }

    public int getOrCreate(T t) {
        if (!map.containsKey(t)) {
            var i = list.size();
            map.put(t, i);
            list.add(t);
            return i;
        }
        return map.getInt(t);
    }

    public boolean hasObject(T t) {
        return map.containsKey(t);
    }

    public Palette<T> toImmutable() {
        return new ImmutablePalette<>(new MutablePalette<>(Collections.unmodifiableList(list), Object2IntMaps.unmodifiable(map), t));
    }

    public static <T> MutablePalette<T> of(T t) {
        var list = new ArrayList<T>();
        var map = new Object2IntArrayMap<T>();
        list.add(t);
        map.put(t, 0);
        return new MutablePalette<>(list, map, t);
    }
}
