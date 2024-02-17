package com.harleylizard.sandbox.world;

import com.harleylizard.sandbox.tile.Tile;
import kdotjpg.opensimplex2.OpenSimplex2;

import java.util.concurrent.ThreadLocalRandom;

public final class ColumnGenerator {
    private final long seed = ThreadLocalRandom.current().nextLong();

    public Column generate(int position) {
        var column = new Column();
        var offset = position << 4;
        for (var i = 0; i < 16 * 16 * 16; i++) {
            var x = (i % 16) + offset;
            var y = i / 16;

            var gradient = 1.0F - (float) y / 256.0F;

            var random = ((hills(seed, x, y) * 0.075F) + (rocky(seed, x, y) * 0.015F));

            var noise = gradient + random;
            if (noise > 0.5F) {
                column.setTile(x, y, Tile.DIRT);
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

            var noise = gradient + hills(seed, x, y) * 0.075F;
            if (column.getTile(x, y) == Tile.DIRT && column.getTile(x, y + 1) == Tile.AIR && noise < 0.6F) {
                column.setTile(x, y, Tile.GRASS);

                if (ThreadLocalRandom.current().nextBoolean()) {
                    column.setTile(x, y + 1, Tile.TALL_GRASS);
                }
            }
        }
    }

    private static double hills(long seed, int x, int y) {
        var PERIOD = 64.0F;
        var OFF_X = 8;
        var OFF_Y = 8;
        return OpenSimplex2.noise2(seed, (x + OFF_X) * 1.0 / PERIOD, (y + OFF_Y) * 1.0 / PERIOD);
    }

    private static double rocky(long seed, int x, int y) {
        var PERIOD = 16.0F;
        var OFF_X = 1;
        var OFF_Y = 1;
        return OpenSimplex2.noise2(seed, (x + OFF_X) * 1.0 / PERIOD, (y + OFF_Y) * 1.0 / PERIOD);
    }
}
