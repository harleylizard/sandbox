package com.harleylizard.sandbox.layer;

import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.util.ImmutableIterator;

import java.util.Iterator;
import java.util.LinkedList;

public final class MutableLayeredColumn implements LayeredColumn {
    private static final int SIZE = 16 * 256 * 2;
    private final int[] tiles = new int[SIZE];
    private final MutablePalette<Tile> palette = MutablePalette.of(Tile.AIR);

    @Override
    public Tile getTile(Layer layer, int x, int y) {
        return palette.getObject(tiles[getIndex(layer, x, y)]);
    }

    public void setTile(Layer layer, int x, int y, Tile tile) {
        tiles[getIndex(layer, x, y)] = palette.getOrCreate(tile);
    }

    @Override
    public Palette<Tile> getPalette() {
        return palette.toImmutable();
    }

    @Override
    public Iterator<Entry> iterator() {
        var list = new LinkedList<Entry>();
        for (var i = 0; i < SIZE; i++) {
            var x = i % 16;
            var y = i / 16;
            var layer = i / (16 * 256);

            list.add(new Entry(tiles[i], x, y, layer));
        }
        return ImmutableIterator.of(list);
    }

    private static int getIndex(Layer layer, int x, int y) {
        x &= 0xF;
        y &= 0xFF;
        return (y * 16 + x) + layer.ordinal() * 16 * 256 * 2;
    }
}
