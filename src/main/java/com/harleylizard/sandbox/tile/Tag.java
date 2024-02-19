package com.harleylizard.sandbox.tile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Tag {
    public static final Tag SOLID = of(Tile.DIRT, Tile.ROOTED_DIRT, Tile.GRASS, Tile.STONE_BRICK, Tile.LOG);
    public static final Tag CAN_PLACE_ON = of(Tile.GRASS, Tile.DIRT);

    private final List<Tile> list;

    private Tag(List<Tile> list) {
        this.list = list;
    }

    public boolean has(Tile tile) {
        return list.contains(tile);
    }

    public static Tag of(Tile... tiles) {
        return new Tag(Collections.unmodifiableList(Arrays.asList(tiles)));
    }
}
