package com.harleylizard.sandbox.tile.behaviour;

import com.harleylizard.sandbox.layer.Layer;
import com.harleylizard.sandbox.tile.Tag;
import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.world.World;

public final class GrassTileBehaviour implements TileBehaviour {
    @Override
    public void onNeighbourUpdated(Layer layer, World world, int x, int y) {
        if (    world.getTile(layer, x, y - 1).is(Tag.SOLID) &&
                world.getTile(layer, x, y + 1).is(Tag.SOLID) &&
                world.getTile(layer, x - 1, y).is(Tag.SOLID) &&
                world.getTile(layer, x + 1, y).is(Tag.SOLID)) {
            world.setTile(layer, x, y, Tile.DIRT);
        }
    }
}
