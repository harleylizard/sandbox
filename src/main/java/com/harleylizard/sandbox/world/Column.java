package com.harleylizard.sandbox.world;

import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.tile.TileGetter;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

import java.util.ArrayList;
import java.util.List;

public final class Column implements TileGetter {
    private final int[] ints = new int[16 * 16 * 16];

    private final Palette<Tile> palette = new Palette<Tile>().add(Tile.AIR);

    @Override
    public Tile get(int x, int y) {
        return palette.getObject(ints[single(x, y)]);
    }

    @Override
    public void set(int x, int y, Tile tile) {
        ints[single(x, y)] = palette.getInt(tile);
    }

    private static int single(int x, int y) {
        var k = y >> 4;
        if (k > 15) {
            throw new RuntimeException("Over world height");
        }
        return ((k + y) & 0xFF) * 16 + (x & 0xF);
    }

    private static final class Palette<T> {
        private final Object2IntMap<T> map = new Object2IntArrayMap<>();
        private final List<T> list = new ArrayList<>();

        private Palette() {}

        private T getObject(int i) {
            return list.isEmpty() ? null : list.get(i);
        }

        private int getInt(T t) {
            if (!map.containsKey(t)) {
                var i = list.size();
                map.put(t, i);
                list.add(t);
                return i;
            }
            return map.getInt(t);
        }

        private Palette<T> add(T t) {
            var i = list.size();
            map.put(t, i);
            list.add(t);
            return this;
        }
    }
}
