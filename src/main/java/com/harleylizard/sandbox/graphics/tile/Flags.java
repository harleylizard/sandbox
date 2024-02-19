package com.harleylizard.sandbox.graphics.tile;

import com.harleylizard.sandbox.tile.Tile;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;

public final class Flags {
    public static final int SOLID = 1;
    public static final int TRANSPARENT = 2;
    public static final int TINTED = 3;
    public static final int WAVY = 4;

    private static final Object2IntMap<Tile> MAP = createImmutable();

    private Flags() {}

    public static boolean is(Tile tile, int flag) {
        return (MAP.getInt(tile) & flag) == flag;
    }

    private static Object2IntMap<Tile> createImmutable() {
        var map = new Object2IntArrayMap<Tile>();
        map.put(Tile.AIR, TRANSPARENT);
        map.put(Tile.DIRT, SOLID);
        map.put(Tile.ROOTED_DIRT, SOLID);
        map.put(Tile.GRASS, SOLID | TINTED);
        map.put(Tile.TALL_GRASS, TRANSPARENT | TINTED | WAVY);
        map.put(Tile.STONE_BRICK, SOLID);
        map.put(Tile.LOG, SOLID);
        map.put(Tile.LEAVES, SOLID | TINTED | WAVY);

        return Object2IntMaps.unmodifiable(map);
    }
}
