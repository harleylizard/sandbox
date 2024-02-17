package com.harleylizard.sandbox.graphics.mesh;

import com.harleylizard.sandbox.tile.Tile;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;

public final class TileTextureGetter {
    private static final Object2IntMap<Tile> MAP = create();

    private TileTextureGetter() {}

    public static int get(Tile tile) {
        return MAP.getInt(tile);
    }

    private static Object2IntMap<Tile> create() {
        var map = new Object2IntArrayMap<Tile>();
        map.put(Tile.DIRT, 0);
        map.put(Tile.GRASS, 1);
        return Object2IntMaps.unmodifiable(map);
    }
}
