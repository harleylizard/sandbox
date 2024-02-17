package com.harleylizard.sandbox.world;

import com.harleylizard.sandbox.tile.Tile;
import kdotjpg.opensimplex2.OpenSimplex2;

public final class ColumnGenerator {
    private final long seed = 0;

    public Column generate(int position) {
        var column = new Column();
        var offset = position << 4;
        for (var i = 0; i < 16 * 16 * 16; i++) {
            var x = (i % 16) + offset;
            var y = i / 16;

            var gradient = 1.0F - (float) y / 256.0F;

            var noise = gradient + noise(seed, x, y) * 0.075F;
            if (noise > 0.5F) {
                column.set(x, y, Tile.DIRT);
            }
        }
        addGrass(column, offset);
        return column;
    }

    private void addGrass(Column column, int offset) {
        for (var i = 0; i < 16 * 16 * 16; i++) {
            var x = (i % 16) + offset;
            var y = i / 16;

            var gradient = 1.0F - (float) y / 256.0F;

            var noise = gradient + noise(seed, x, y) * 0.075F;
            if (column.get(x, y) == Tile.DIRT && column.get(x, y + 1) == Tile.AIR && noise < 0.6F) {
                column.set(x, y, Tile.GRASS);
            }
        }
    }

    private static double noise(long seed, int x, int y) {
        var PERIOD = 64.0;
        var OFF_X = 512;
        var OFF_Y = 512;
        return OpenSimplex2.noise2(seed, (x + OFF_X) * 1.0 / PERIOD, (y + OFF_Y) * 1.0 / PERIOD);
    }
}
