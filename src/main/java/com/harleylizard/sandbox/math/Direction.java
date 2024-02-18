package com.harleylizard.sandbox.math;

public enum Direction {
    LEFT(new ImmutableVector2i(-1, 0)),
    RIGHT(new ImmutableVector2i(1, 0)),
    UP(new ImmutableVector2i(0, 1)),
    DOWN(new ImmutableVector2i(0, -1));

    private final ImmutableVector2i normal;

    Direction(ImmutableVector2i normal) {
        this.normal = normal;
    }

    public ImmutableVector2i getNormal() {
        return normal;
    }
}
