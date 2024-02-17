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

    private final ColumnGenerator generator = new ColumnGenerator();

    private final Queue<IntObjectPair<Column>> queue = new LinkedList<>();

    @Override
    public Tile getTile(int x, int y) {
        if (y > 255) {
            return Tile.AIR;
        }
        return column(x).getTile(x, y);
    }

    @Override
    public void setTile(int x, int y, Tile tile) {
        if (y > 255) {
            return;
        }
        var column = column(x);
        offerWithNeighbours(x);
        column.setTile(x, y, tile);
    }

    private void offerWithNeighbours(int x) {
        var position = x >> 4;
        var left = position - 1;
        var right = position + 1;

        queue.offer(IntObjectPair.of(left, null));
        queue.offer(IntObjectPair.of(position, column(x)));
        queue.offer(IntObjectPair.of(right, null));
    }

    private Column column(int x) {
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

    public Set<Int2ObjectMap.Entry<Column>> getEntries() {
        return map.int2ObjectEntrySet();
    }

    public Queue<IntObjectPair<Column>> getQueue() {
        return queue;
    }
}
