package com.harleylizard.sandbox.tile.behaviour;

import com.harleylizard.sandbox.layer.Layer;
import com.harleylizard.sandbox.tile.Tag;
import com.harleylizard.sandbox.tile.Tile;
import com.harleylizard.sandbox.world.World;

public final class TallGrassTileBehaviour implements TileBehaviour {
    @Override
    public void onNeighbourUpdated(Layer layer, World world, int x, int y) {
        var tile = world.getTile(layer, x, y - 1);
        if (!tile.is(Tag.CAN_PLACE_ON)) {
            world.setTileUpdating(x, y, Tile.AIR);
        }
    }
}
