package com.harleylizard.sandbox.tile;

import java.util.Map;

public final class TileLayers {
    private static final Map<Tile, Layer> MAP = Map.of(
            Tile.AIR,
            Layer.TRANSPARENT,
            Tile.DIRT,
            Layer.SOLID,
            Tile.ROOTED_DIRT,
            Layer.SOLID,
            Tile.GRASS,
            Layer.SOLID,
            Tile.TALL_GRASS,
            Layer.TRANSPARENT,
            Tile.STONE_BRICK,
            Layer.SOLID,
            Tile.LOG,
            Layer.SOLID,
            Tile.LEAVES,
            Layer.SOLID
    );

    private TileLayers() {}

    public static Layer getLayer(Tile tile) {
        return MAP.get(tile);
    }
}
