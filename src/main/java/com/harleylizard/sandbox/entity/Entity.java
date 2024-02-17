package com.harleylizard.sandbox.entity;

import com.harleylizard.sandbox.math.BoundingBox;
import com.harleylizard.sandbox.world.World;
import org.joml.Vector2f;

public sealed interface Entity permits Player {

    void step(World world);

    Vector2f getPosition();

    BoundingBox getBoundingBox();
}
