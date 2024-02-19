package com.harleylizard.sandbox.tile;

import com.harleylizard.sandbox.layer.Layer;

public interface TileGetter {

    Tile getTile(Layer layer, int x, int y);
}
