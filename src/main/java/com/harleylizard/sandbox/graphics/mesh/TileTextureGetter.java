package com.harleylizard.sandbox.graphics.mesh;

import com.harleylizard.sandbox.tile.Tile;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;

import java.util.List;

public final class TileTextureGetter {
    private static final List<String> TEXTURES = List.of(
            "textures/tile/dirt.png",
            "textures/tile/grass.png",
            "textures/tile/tall_grass.png"
    );

    private static final Object2IntMap<Tile> MAP = create();

    private TileTextureGetter() {}

    public static int get(Tile tile) {
        return MAP.getInt(tile);
    }

    private static Object2IntMap<Tile> create() {
        var map = new Object2IntArrayMap<Tile>();
        map.put(Tile.DIRT, TEXTURES.indexOf("textures/tile/dirt.png"));
        map.put(Tile.GRASS, TEXTURES.indexOf("textures/tile/grass.png"));
        map.put(Tile.TALL_GRASS, TEXTURES.indexOf("textures/tile/tall_grass.png"));
        return Object2IntMaps.unmodifiable(map);
    }

    public static List<String> getTextures() {
        return TEXTURES;
    }
}
