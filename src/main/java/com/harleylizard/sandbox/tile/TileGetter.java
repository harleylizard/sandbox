package com.harleylizard.sandbox.tile;

public interface TileGetter {

    Tile getTile(int x, int y);

    void setTile(int x, int y, Tile tile);
}
