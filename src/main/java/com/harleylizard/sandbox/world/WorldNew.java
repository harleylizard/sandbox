package com.harleylizard.sandbox.world;

import com.harleylizard.sandbox.layer.Layer;
import com.harleylizard.sandbox.layer.LayeredColumn;
import com.harleylizard.sandbox.layer.MutableLayeredColumn;
import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.tile.TileGetter;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntArrayFIFOQueue;
import it.unimi.dsi.fastutil.ints.IntPriorityQueue;

import java.util.concurrent.locks.ReentrantLock;

public final class WorldNew implements TileGetter {
    private final ReentrantLock lock = new ReentrantLock();
    private final Int2ObjectMap<MutableLayeredColumn> map = new Int2ObjectArrayMap<>();
    private final IntPriorityQueue queue = new IntArrayFIFOQueue();

    private final WorldGenerator generator = new WorldGenerator();

    @Override
    public Tile getTile(Layer layer, int x, int y) {
        if (aboveHeight(y)) {
            return Tile.AIR;
        }
        return getMutableLayerColumn(x >> 4).getTile(layer, x, y);
    }

    public void setTile(Layer layer, int x, int y, Tile tile) {
        if (aboveHeight(y)) {
            return;
        }
        var i = x >> 4;
        getMutableLayerColumn(i).setTile(layer, x, y, tile);
        queue.enqueue(i);
    }

    private MutableLayeredColumn getMutableLayerColumn(int i) {
        return map.get(i);
    }

    public LayeredColumn getLayeredColumn(int i) {
        return map.get(i);
    }

    public boolean aboveHeight(int y) {
        return y >= 256;
    }
}
