package com.harleylizard.sandbox.world;

import com.harleylizard.sandbox.column.LayeredColumn;
import com.harleylizard.sandbox.structure.TreeStructure;
import com.harleylizard.sandbox.tile.Layer;
import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.tile.TileLayers;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import kdotjpg.opensimplex2.OpenSimplex2;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class WorldGenerator {
    private final long seed = 0;

    private final Random random = new Random(seed);

    private final IntList list = new IntArrayList();

    public LayeredColumn generate(int position) {
        var column = new LayeredColumn();
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

    private void addGrass(LayeredColumn column, int offset) {
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

    public void placeStructures(World world, int position) {
        var tree = new TreeStructure();

        var offset = position << 4;
        for (var i = 0; i < 16 * 16 * 16; i++) {
            var x = (i % 16) + offset;
            var y = i / 16;

            if (y == 250 && tree.getProbability(random)) {
                var down = y - 1;
                while (TileLayers.getLayer(world.getTile(x, down)) == Layer.TRANSPARENT) {
                    down--;
                }
                if (world.getTile(x, down - 1) == Tile.DIRT) {
                    down++;
                } else {
                    continue;
                }
                tree.place(world, x, down, random);
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
