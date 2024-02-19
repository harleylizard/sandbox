package com.harleylizard.sandbox.tile;

import java.util.Map;

public final class TileLayers {
    private static final Map<Tile, TransparencyLayer> MAP = Map.of(
            Tile.AIR,
            TransparencyLayer.TRANSPARENT,
            Tile.DIRT,
            TransparencyLayer.SOLID,
            Tile.ROOTED_DIRT,
            TransparencyLayer.SOLID,
            Tile.GRASS,
            TransparencyLayer.SOLID,
            Tile.TALL_GRASS,
            TransparencyLayer.TRANSPARENT,
            Tile.STONE_BRICK,
            TransparencyLayer.SOLID,
            Tile.LOG,
            TransparencyLayer.SOLID,
            Tile.LEAVES,
            TransparencyLayer.SOLID
    );

    private TileLayers() {}

    public static TransparencyLayer getLayer(Tile tile) {
        return MAP.get(tile);
    }
}
