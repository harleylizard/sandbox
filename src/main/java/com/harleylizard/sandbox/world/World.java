package com.harleylizard.sandbox.world;

import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.tile.TileGetter;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public final class World implements TileGetter {
    private final Int2ObjectMap<Column> map = new Int2ObjectArrayMap<>();

    @Override
    public Tile get(int x, int y) {
        return column(x).get(x, y);
    }

    @Override
    public void set(int x, int y, Tile tile) {
        column(x).set(x, y, tile);
    }

    private Column column(int x) {
        var l = x >> 4;
        if (!map.containsKey(l)) {
            var column = new Column();
            map.put(l, column);
            return column;
        }
        return map.get(l);
    }
}
