package com.harleylizard.sandbox.structure;

import com.harleylizard.sandbox.world.World;

import java.util.Random;

public sealed interface Structure permits TreeStructure {

    void place(World world, int x, int y, Random random);

    boolean getProbability(Random random);
}
