package com.harleylizard.sandbox.tile;

public interface TileGetter {

    Tile get(int x, int y);

    void set(int x, int y, Tile tile);
}
