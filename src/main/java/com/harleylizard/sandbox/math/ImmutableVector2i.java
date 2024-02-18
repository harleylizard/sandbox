package com.harleylizard.sandbox.math;

public final class ImmutableVector2i {
    private int x;
    private int y;

    public ImmutableVector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ImmutableVector2i relative(Direction direction) {
        var normal = direction.getNormal();
        return new ImmutableVector2i(x + normal.x, y + normal.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
