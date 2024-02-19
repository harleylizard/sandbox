package com.harleylizard.sandbox.tile.behaviour;

import com.harleylizard.sandbox.layer.Layer;
import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.world.World;

public final class GrassTileBehaviour implements TileBehaviour {
    @Override
    public void onNeighbourUpdated(World world, int x, int y) {
        if (world.getTile(Layer.FOREGROUND, x, y - 1) != Tile.AIR &&
                world.getTile(Layer.FOREGROUND, x, y + 1) != Tile.AIR &&
                world.getTile(Layer.FOREGROUND, x - 1, y) != Tile.AIR &&
                world.getTile(Layer.FOREGROUND, x + 1, y) != Tile.AIR) {
            world.setTile(Layer.FOREGROUND, x, y, Tile.DIRT);
        }
    }
}
