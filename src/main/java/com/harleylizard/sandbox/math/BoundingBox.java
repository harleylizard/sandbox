package com.harleylizard.sandbox.math;

import org.joml.Vector2f;

public final class BoundingBox {
    private final float minX;
    private final float minY;
    private final float maxX;
    private final float maxY;

    public BoundingBox(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public BoundingBox move(Vector2f vector2f) {
        var x = vector2f.x;
        var y = vector2f.y;
        return new BoundingBox(minX + x, minY + y, maxX + x, maxY + y);
    }

    public boolean intersects(BoundingBox boundingBox) {
        boolean x = (minX <= boundingBox.maxX && maxX >= boundingBox.minX);
        boolean y = (minY <= boundingBox.maxY && maxY >= boundingBox.minY);
        return x && y;
    }

    public boolean intersectsBottom(BoundingBox boundingBox) {
        return maxY >= boundingBox.minY && minY < boundingBox.minY && ((minX >= boundingBox.minX && minX <= boundingBox.maxX) || (maxX >= boundingBox.minX && maxX <= boundingBox.maxX));
    }

    public boolean intersectsTop(BoundingBox boundingBox) {
        return minY <= boundingBox.maxY && maxY > boundingBox.maxY && ((minX >= boundingBox.minX && minX <= boundingBox.maxX) || (maxX >= boundingBox.minX && maxX <= boundingBox.maxX));
    }

    public boolean intersectsRight(BoundingBox boundingBox) {
        return maxX >= boundingBox.minX && minX < boundingBox.minX && ((minY >= boundingBox.minY && minY <= boundingBox.maxY) || (maxY >= boundingBox.minY && maxY <= boundingBox.maxY));
    }

    public boolean intersectsLeft(BoundingBox boundingBox) {
        return minX <= boundingBox.maxX && maxX > boundingBox.maxX && ((minY >= boundingBox.minY && minY <= boundingBox.maxY) || (maxY >= boundingBox.minY && maxY <= boundingBox.maxY));
    }

    public float getMinX() {
        return minX;
    }

    public float getMinY() {
        return minY;
    }

    public float getMaxX() {
        return maxX;
    }

    public float getMaxY() {
        return maxY;
    }
}
