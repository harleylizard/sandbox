package com.harleylizard.sandbox.world;

import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.tile.TileGetter;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntObjectPair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public final class World implements TileGetter {
    private final Int2ObjectMap<Column> map = new Int2ObjectArrayMap<>();

    private final WorldGenerator generator = new WorldGenerator();

    private final Queue<IntObjectPair<Column>> queue = new LinkedList<>();

    @Override
    public Tile getTile(int x, int y) {
        if (y > 255) {
            return Tile.AIR;
        }
        return getOrCreateColumn(x).getTile(x, y);
    }

    @Override
    public void setTile(int x, int y, Tile tile) {
        if (y > 255) {
            return;
        }
        getOrCreateColumn(x).setTile(x, y, tile);
    }

    public void setTileUpdating(int x, int y, Tile tile) {
        setTile(x, y, tile);
        updateTileNeighbour(x, y, 0, 1);
        updateTileNeighbour(x, y, 0, -1);
        updateTileNeighbour(x, y, 1, 0);
        updateTileNeighbour(x, y, -1, 0);
        offer(x);
    }

    private void updateTileNeighbour(int x, int y, int xOffset, int yOffset) {
        var tile = getTile(x + xOffset, y + yOffset);
        var behaviour = Tile.getBehaviour(tile);
        if (behaviour != null) {
            behaviour.onNeighbourUpdated(this, x + xOffset, y + yOffset);
        }
    }

    private void offer(int x) {
        queue.offer(IntObjectPair.of(x >> 4, getColumn(x)));
    }

    private Column getOrCreateColumn(int x) {
        var position = x >> 4;
        if (!map.containsKey(position)) {
            var column = new Column();
            map.put(position, column);
            return column;
        }
        return map.get(position);
    }

    public Column getColumn(int position) {
        return map.get(position);
    }

    public void generate(int position) {
        map.put(position, generator.generate(position));
    }

    public void generateStructures(int position) {
        generator.placeStructures(this, position);
    }

    public Set<Int2ObjectMap.Entry<Column>> getEntries() {
        return map.int2ObjectEntrySet();
    }

    public Queue<IntObjectPair<Column>> getQueue() {
        return queue;
    }
}
