package com.harleylizard.sandbox.tile.behaviour;

import com.harleylizard.sandbox.world.World;

public sealed interface TileBehaviour permits GrassTileBehaviour, TallGrassTileBehaviour {

    void onNeighbourUpdated(World world, int x, int y);
}
