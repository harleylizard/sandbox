package com.harleylizard.sandbox.tile.behaviour;

import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.world.World;

public final class TallGrassTileBehaviour implements TileBehaviour {
    @Override
    public void onNeighbourUpdated(World world, int x, int y) {
        var tile = world.getTile(x, y - 1);
        if (!(tile == Tile.GRASS || tile == Tile.DIRT)) {
            world.setTileUpdating(x, y, Tile.AIR);
        }
    }
}
