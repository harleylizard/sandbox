package com.harleylizard.sandbox.layer;

import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.tile.TileGetter;

public sealed interface LayeredColumn extends TileGetter, Iterable<LayeredColumn.Entry> permits MutableLayeredColumn {

    Palette<Tile> getPalette();

    final class Entry {
        private final int tile;
        private final int x;
        private final int y;
        private final int layer;

        Entry(int tile, int x, int y, int layer) {
            this.tile = tile;
            this.x = x;
            this.y = y;
            this.layer = layer;
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

        public int getLayer() {
            return layer;
        }
    }
}
