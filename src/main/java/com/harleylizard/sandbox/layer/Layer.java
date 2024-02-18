package com.harleylizard.sandbox.layer;

import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.tile.TileGetter;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public final class Layer implements TileGetter {
    private final Int2ObjectMap<LayerColumn> map = new Int2ObjectArrayMap<>();

    @Override
    public Tile getTile(int x, int y) {
        if (y > 255) {
            return Tile.AIR;
        }
        return getColumnFromRandomAccess(x).getTile(x, y);
    }

    @Override
    public void setTile(int x, int y, Tile tile) {
        setTileRaw(x, y, tile);
    }

    public void setTileRaw(int x, int y, Tile tile) {
        if (y > 255) {
            return;
        }
        getColumnFromRandomAccess(x).setTile(x, y, tile);
    }

    public LayerColumn getColumnFromRandomAccess(int i) {
        return map.get(i >> 4);
    }

    public LayerColumn getColumn(int i) {
        return map.get(i);
    }

    public void setColumn(int i, LayerColumn column) {
        map.put(i, column);
    }
}
