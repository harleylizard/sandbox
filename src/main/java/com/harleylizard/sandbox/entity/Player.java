package com.harleylizard.sandbox.entity;

import org.joml.Vector3f;

public final class Player implements Entity {
    private final Vector3f position = new Vector3f();

    @Override
    public Vector3f getPosition() {
        return position;
    }
}
