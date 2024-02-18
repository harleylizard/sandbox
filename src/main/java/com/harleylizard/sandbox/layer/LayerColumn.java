package com.harleylizard.sandbox.layer;

import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.tile.TileGetter;
import com.harleylizard.sandbox.util.ImmutableIterator;

import java.util.Iterator;
import java.util.LinkedList;

public final class LayerColumn implements TileGetter, Iterable<LayerColumn.Entry> {
    private static final int SIZE = 16 * 16 * 16;
    private final int[] tiles = new int[SIZE];
    private final MutablePalette<Tile> palette = MutablePalette.of(Tile.AIR);

    @Override
    public Tile getTile(int x, int y) {
        return palette.getObject(tiles[getIndex(x, y)]);
    }

    @Override
    public void setTile(int x, int y, Tile tile) {
        tiles[getIndex(x, y)] = palette.getOrCreate(tile);
    }

    public Palette<Tile> getPalette() {
        return palette.toImmutable();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Entry> iterator() {
        var list = new LinkedList<Entry>();
        for (var i = 0; i < SIZE; i++) {
            var x = i % 16;
            var y = i / 16;
            list.add(new Entry(tiles[i], x, y));
        }
        return ImmutableIterator.of(list);
    }

    private static int getIndex(int x, int y) {
        return (y & 0xFF) * 16 + (x & 0xF);
    }

    public static final class Entry {
        private final int tile;
        private final int x;
        private final int y;

        private Entry(int tile, int x, int y) {
            this.tile = tile;
            this.x = x;
            this.y = y;
        }

        public int getTile() {
            return tile;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
