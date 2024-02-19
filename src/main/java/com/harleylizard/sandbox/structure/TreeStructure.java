package com.harleylizard.sandbox.structure;

import com.harleylizard.sandbox.graphics.tile.Flags;
import com.harleylizard.sandbox.layer.Layer;
import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.world.World;

import java.util.Random;

public final class TreeStructure implements Structure {
    @Override
    public void place(World world, int x, int y, Random random) {
        var height = random.nextInt(15) == 0 ? 24 : 20;
        for (var i = 0; i < height; i++) {
            world.setTile(Layer.FOREGROUND, x, y + i, Tile.LOG);

            if (i > height - 10) {
                var f = ((float) height / 24.0F) * 4.0F;
                createBranch(world, x, y + i, (int) f, random);
            }
        }

        world.setTile(Layer.FOREGROUND, x, y - 1, Tile.ROOTED_DIRT);
    }

    private void createBranch(World world, int x, int y, int branchRate, Random random) {
        if (random.nextInt(branchRate) == 0) {
            return;
        }
        var length = 4;

        var direction = random.nextBoolean() ? 1 : -1;
        var xOffset = direction;
        for (var i = 0; i < length; i++) {
            world.setTile(Layer.FOREGROUND, x + xOffset, y + i, Tile.LOG);
            if (random.nextInt(2) == 0) {
                xOffset += direction;
            }
        }

        createLeaves(world, x + xOffset, y, random, length);
    }

    private void createLeaves(World world, int x, int y, Random random, int height) {
        var size = random.nextInt(25) == 0 ? 5 : 4;
        for (var j = -size - 1; j < size; j++)
            for (var k = -size - 1; k < size; k++) {
                var distance = Math.sqrt(j * j + k * k);
                if (distance < 4) {
                    var m = x + j;
                    var n = y + height + k;
                    if (Flags.is(world.getTile(Layer.FOREGROUND, m, n), Flags.TRANSPARENT)) {
                        world.setTile(Layer.FOREGROUND, m, n, Tile.LEAVES);
                        if (random.nextInt(4) == 0) {
                            world.setTile(Layer.FOREGROUND, m, n, Tile.AIR);
                        }
                    }
                }
            }
    }

    @Override
    public boolean getProbability(Random random) {
        return random.nextInt(16) == 0;
    }
}
