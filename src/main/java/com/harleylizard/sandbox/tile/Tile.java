package com.harleylizard.sandbox.tile;

import com.harleylizard.sandbox.tile.behaviour.GrassTileBehaviour;
import com.harleylizard.sandbox.tile.behaviour.TallGrassTileBehaviour;
import com.harleylizard.sandbox.tile.behaviour.TileBehaviour;

import java.util.Map;

public class Tile {
    public static final Tile AIR = new Tile();
    public static final Tile DIRT = new Tile();
    public static final Tile ROOTED_DIRT = new Tile();
    public static final Tile GRASS = new Tile();
    public static final Tile TALL_GRASS = new Tile();
    public static final Tile STONE_BRICK = new Tile();
    public static final Tile LOG = new Tile();
    public static final Tile LEAVES = new Tile();

    private static final Map<Tile, TileBehaviour> MAP = Map.of(Tile.TALL_GRASS, new TallGrassTileBehaviour(), Tile.GRASS, new GrassTileBehaviour());

    public TileBehaviour getBehaviour() {
        return MAP.get(this);
    }

    public boolean is(Tag tag) {
        return tag.has(this);
    }
}
